import java.util.concurrent.*;

public class PhaserDemo {
    public static void main(String[] args)
    {
        //to run threads in batches ex first 30,then next 30..next 40 ... -> phaser
        Phaser phsr = new Phaser(1);//1 thread registered by default
        new MyThread(phsr, "A");
        new MyThread(phsr, "B");
        new MyThread(phsr, "C");
        new MyThread(phsr, "D");
        new MyThread(phsr, "E");

        int curPhase = phsr.getPhase();//phase number 0 indexed...1st phase here
        phsr.arriveAndAwaitAdvance();//now all threads after completing reach here
        System.out.println("Phase "+(curPhase+1)+" complete");

        curPhase = phsr.getPhase();// now arrive at next phase 
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase "+(curPhase+1)+" complete");

        curPhase = phsr.getPhase();// now arrive at next phase 
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase "+(curPhase+1)+" complete");

        phsr.arriveAndDeregister();//but this should be applied for all phases else wont deregister
        //to find out if phase is terminated or not?
        if(phsr.isTerminated())
            System.out.println("Finished....");
        //but we observed phaser was not active so we have to de register the threads...thus we added on line 24.. phaser.arriveAndDeregister()
    }   
}

class MyThread implements Runnable{
    Phaser p;String name;
    public MyThread(Phaser ps,String s) {
        p=ps;
        name = s;
        //for how mnay threads should phaser wait?
        p.register();//now it gets registered..main thread and 3 threads
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        //assume ur batches/phases ex 30/30/40 are here
       System.out.println("Thread "+name+" beginning phase one ");
       p.arriveAndAwaitAdvance();
        //method to say only execute till here dont advance to next phase till all threads have arrived here
       try {
           Thread.sleep(1000);
       } catch (Exception e) {
        System.out.println(e);
       }


       System.out.println("Thread "+name+" beginning phase two ");
       p.arriveAndAwaitAdvance();//now phase 2
        try {
           Thread.sleep(1000);
       } catch (Exception e) {
        System.out.println(e);
       }

       System.out.println("Thread "+name+" beginning phase three ");
       //last phase so deregister here now it works
       p.arriveAndDeregister();
    }
    
}
