package com.hxocr.idfpserver._02_threadpool;

import com.hxocr.idfpserver._40_message.RequestInfo;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Logger;

@Component
public class ServerThreadPoolManager {

    private static Logger logger = Logger.getLogger("MyLogger");

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 2;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 5;

    /**
     * 当线程池的容量满了，执行下面代码，将请求存入到缓冲队列
     */
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //请求加入到缓冲队列
            try {
                msgQueue.offer(((ServerThread) r).getAcceptRequest());
            } catch (ClassCastException e) {
                logger.info("处理队列已满，请稍后重发");
                //http发送稍后重发消息
            }
            logger.info("系统任务太忙了,把此请求交给消息队列逐一处理，请求号：" + ((ServerThread) r).getAcceptRequest());
            logger.info("消息队列当前长度：" + msgQueue.size());
        }
    };

    /**创建线程池*/
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);

    /**
     * 用于储存在队列中的请求,防止重复提交,在真实场景中，可用redis代替验证重复
     */
    Map<String, RequestInfo> cacheMap = new ConcurrentHashMap<String, RequestInfo>();

    /**
     * 请求的缓冲队列,当线程池满了，则将请求存入到此缓冲队列
     */
    Queue<RequestInfo> msgQueue = new LinkedBlockingQueue< RequestInfo>();

    /**将任务加入请求线程池*/
    public String addRequest(String UUID, String fileName, String filePath){
        logger.info("此请求准备添加到线程池，请求号：" + UUID);
        //验证当前进入的请求是否已经存在
        if (cacheMap.get(UUID) == null) {
            RequestInfo requestInfo = new RequestInfo(UUID, fileName, filePath);
            cacheMap.put(UUID, requestInfo);
            ServerThread serverThread = new ServerThread(requestInfo);
            threadPool.execute(serverThread);
        }
        return "success";
    }

    /**
     * 线程池的定时任务----> 称为(调度线程池)。此线程池支持 定时以及周期性执行任务的需求。
     */
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    /**
     * 检查(调度线程池)，每秒执行一次，查看请求的缓冲队列是否有请求记录，则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            //判断缓冲队列是否存在记录
            if(!msgQueue.isEmpty()){
                //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的请求加入到线程池
                if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                    RequestInfo msgItem= (RequestInfo) msgQueue.poll();
                    ServerThread serverThread = new ServerThread(msgItem);
                    threadPool.execute(serverThread);
                    logger.info("(调度线程池)缓冲队列出现请求业务，重新添加到线程池, 消息ID：" + msgItem.getRequestID());
                    logger.info("消息队列当前长度：" + msgQueue.size());
                }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);


    /**获取消息缓冲队列*/
    public Queue<RequestInfo> getMsgQueue() {
        return msgQueue;
    }

    /**终止请求线程池+调度线程池*/
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        logger.info("终止请求线程池+调度线程池："+scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();

    }

    public static void main(String[] args) {

    }
}