import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class test {

// 总的请求个数

    public static final int requestTotal = 1;

// 同一时刻最大的并发线程的个数

    public static final int concurrentThreadNum = 40;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //CountDownLatch countDownLatch = new CountDownLatch(requestTotal);

        Semaphore semaphore = new Semaphore(concurrentThreadNum);

        for (int i = 0; i < 100000; i++) {


            int finalI = i;
            executorService.execute(() -> {

                try {

                    semaphore.acquire();

                    System.out.println("i = " + finalI+"   "+semaphore.availablePermits());
                    //String result = testRequestUri();


                    semaphore.release();

                } catch (InterruptedException e) {


                }

                //countDownLatch.countDown();

            });

        }

        //countDownLatch.await();

        executorService.shutdown();


    }

    private static String testRequestUri() {
        System.out.println(111);

        return "qqq";



}

}
