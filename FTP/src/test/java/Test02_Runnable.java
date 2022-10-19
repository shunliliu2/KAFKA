//测试 Runnable
public class Test02_Runnable {
    public static void main(String[] args) {
        //创建对象
        MyRunnable m = new MyRunnable();
        //绑定Thread对象和接口实现类target的关系
        Thread t = new Thread(m);
        Thread t2 = new Thread(m);
        //想要调用Thread.里的start方法来启动线程
        // 启动线程
        t.setName("1号线程");
        t2.setName("2号线程");
        t.start();
        t2.start();
    }
}
//模拟多线程编程 -- implements Runnable
class MyRunnable implements Runnable{
    //实现了了Runnable接口,就要把接口里的所有抽象方法都重写，否则就包含着抽象方法是一个抽象类
    @Override
    public void run() {
        //输出100次线程名称
        //2,把想要完成的业务,写人重写的run()里
        for (int i = 0; i < 100; i++) {
            //Thread.currentThread()代表正在执行任务的线程对象的引用
            System.out.println(Thread.currentThread().getName()+"*****"+i);
        }
    }
}