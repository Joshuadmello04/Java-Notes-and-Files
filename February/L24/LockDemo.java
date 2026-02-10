import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class LockDemo {
    public static void main(String[] args)
    {
        ReentrantLock lock = new ReentrantLock();
        // new MyThread(lock,"A");
        // new MyThread(lock,"B");
        // new MyThread(lock,"C");
        // new MyThread(lock,"D");

        new DemoThread("E");
        new DemoThread("F");
        new DemoThread("G");

    }   
}

class Shared{
    static int count=0; //...bad programming use atomic classes
    //static AtomicInteger count = new AtomicInteger(0);
}

class ImprovedShared{
    static AtomicInteger count = new AtomicInteger(0);
}
class MyThread implements Runnable{
    ReentrantLock l;String name;
    public MyThread(ReentrantLock l,String s) {
        this.l=l;
        name = s;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        System.out.println("Starting..."+name);
            l.lock();
            System.out.println(name+" is waiting to lock count variable");
            l.lock();//even more secure
            System.out.println(name+" has locked the count");
            Shared.count++;
            System.out.println(name+" : "+Shared.count);
            l.unlock();//like a door u enter and close from inside..u have to open frominside
            System.out.println(name+" is releasing the lock on count");
            //for now all start to together
            l.unlock();
        
        System.out.println("Finished...."+name);
    }
    
}

class DemoThread implements Runnable{
    String name;
    public DemoThread(String s) {
        name = s;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        for(int i=0;i<=5;i++)
        {
            System.out.println(Thread.currentThread().getId()+" "+name+" : "+ImprovedShared.count.getAndIncrement());
        }
    }
}
