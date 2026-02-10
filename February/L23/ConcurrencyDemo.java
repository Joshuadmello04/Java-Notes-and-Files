import java.util.concurrent.*;

public class ConcurrencyDemo {
    public static void main(String[] args) {
        
        // A a1 = new A();
        // A a2 = new A();
        // Thread t1 = new Thread(a1);
        // Thread t2 = new Thread(a2);
        // t1.start();
        // t2.start();

        // new IncThread("A");
        // new IncThread("B");
        // new IncThread("C");

        //but now diff class so we see 2 2 classes at a time...itll still allow multiple
        //so now synchronize completely fails
        //so we need semaphore api
        Semaphore sem = new Semaphore(2);//this lock needs to be created for how many threads are permitted...here 1 at a time -->this flexibility is not available in synchronized keyword
        new IncThread("A",sem);
        new DecThread("B",sem);
        new IncThread("C",sem);
        new IncThread("D", sem);
    }
}

class Shared{
    static int count=0; //shared variable across threads
}

class IncThread implements Runnable{
    String name;
    Semaphore sem;
    public IncThread(String n,Semaphore s) {
        name = n;
        sem = s;
        new Thread(this).start();
    }

    //synchronixed wont work here coz diff class objects
    public void run()
    {
        //this also wont work coz its object level locking//so we need IncThread.class
        //synchronized (this) {
        //synchronized (IncThread.class) { //now it works
        //now no need of synchronixed it was needed till semaphores
            try {
                System.out.println(name +" is waiting for permission");
                sem.acquire();
                System.out.println(name+" got permission");
                for(int i=0;i<5;i++)
                {
                    Shared.count++;
                    System.out.println(name+" : " +Shared.count);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(name+" released permission");
            sem.release();
        //}
    }    
}

//but now diff class so we see 2 2 classes at a time...itll still allow multiple
//so now synchronize completely fails
class DecThread implements Runnable{
    String name;
    Semaphore sem;

    public DecThread(String n,Semaphore s) {
        name = n;
        sem =s;
        new Thread(this).start();
    }

    //synchronixed wont work here coz diff class objects
    public void run()
    {
        //this also wont work coz its object level locking//so we need IncThread.class
        //synchronized (this) {
        //synchronized (DecThread.class) { //now it works
        //now no need of synchronixed it was needed till semaphores
        try {
                System.out.println(name +" is waiting for permission");
                sem.acquire();
                System.out.println(name+" got permission");
                for(int i=0;i<5;i++)
                {
                    Shared.count--;
                    System.out.println(name+" : " +Shared.count);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(name+" released permission");
            sem.release();
        //}
    }    
}


// class A extends Thread{
//     //limitations of synchronized
//     synchronized  public void run(){
//         // synchronized(A.class) //now myultiple objects of same class wont enter...
//         // //this is class level locking
//         // {

//         // }
        

//         //now if we have 2/3 classes and all tryna access..then synchronized alone is not enough

//     }
// }

