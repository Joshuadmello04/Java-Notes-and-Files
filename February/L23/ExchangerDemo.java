import java.util.concurrent.*;
import java.util.stream.Stream;

public class ExchangerDemo {
    public static void main(String[] args) {
      Exchanger<String> ex = new Exchanger<String>();
      new MakeString(ex);
      new UseString(ex);
    }
}

class Shared{
    static int count=0; //shared variable across threads
}

class MakeString implements Runnable{
 
    String name;
    Exchanger<String> ex;
   
    public MakeString(Exchanger<String> c) {
        ex =c;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try{
            // System.out.println(name+" is waiting for permission");
            String names[] = {"Rakesh","Pakesh","Ganesh","Ramesh","Mukesh"};
            // sem.acquire();
            // System.out.println(name+" got permission");
            for(int i=0; i<5; i++){
                String str = ex.exchange(names[i]);
                System.out.println("From UseString: " + str);
                Thread.sleep(1000);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
 
}

class UseString implements Runnable{
    Exchanger<String> ex;
    UseString(Exchanger<String> c) {
        ex = c;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
         try {
                for(int i=0;i<5;i++)
                {
                    String str = ex.exchange("");
                    System.out.println("Received : "+str);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        //}
    }    
}
