import java.util.concurrent.*;

public class CountdownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //System.out.println("Starting.....");
        //new MyThread();
        //System.out.println("Finished....");//but this indicates that it continues it doesnt wait for thread to end

        //so countodwnlatch
        CountDownLatch cdl = new CountDownLatch(5);//5s counter
        System.out.println("Starting....");
        new MyThread(cdl);
        cdl.await();//now thread will wait for await to finish  after countdown completes...so 1-t then finished then resume
        System.out.println("Finished......");
    }   
}

class MyThread implements Runnable{
    CountDownLatch cdl;
    public MyThread(CountDownLatch c) {
        cdl = c;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        for(int i=1;i<=10;i++)
        {
            System.out.println(i);
            try {
                cdl.countDown();//now itll wait for countdown
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
}
