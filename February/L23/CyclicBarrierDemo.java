
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        try {
            CyclicBarrier cb = new CyclicBarrier(3,new MyAction());
            //it means 3 threads complete then call Myaction
            System.out.println("Starting ....");
            for(int i=1;i<11;i++)
            {
                new MyThread(cb,"Thread"+i);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
        }
    }
}
class MyThread implements Runnable{
    String name;
    CyclicBarrier cb;
    public MyThread(CyclicBarrier c,String name) {
        this.name = name;
        cb = c;
        new Thread(this).start();
    }
    @Override
    public void run()
    {
        try {
            System.out.println(name);//thread name
            cb.await();//wait
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class  MyAction implements  Runnable
{
    @Override
    public void run()
    {
        System.out.println("Finished...");
    }
}
