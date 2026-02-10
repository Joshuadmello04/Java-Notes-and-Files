
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CallableDemo {
    public static void main(String[] args) throws Throwable, ExecutionException {
        
        //Good practice is to find out how many threads needed in pool first
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("No of Processors "+ processors);
        
        //now how to create this thread
        ExecutorService es=  Executors.newFixedThreadPool(processors);//upto 3 threads in this thred pool
        
        //now pass the task to the treads ie sum
        Future<?> f1 = es.submit(new Sum(11));//now it can accept double,int,val etc
        Future f2 = es.submit(new Square(12));
        Future f3 = es.submit(new Sum(3));
        Future f4 = es.submit(new Square(11));
       
        
        
        //now whenever result comes we do f1.get()
        System.out.println("Sum result : "+f1.get());
        System.out.println("Square result : "+f2.get());
        System.out.println("Sum result : "+f3.get());
        System.out.println("Square result : "+f4.get());
        //notice the order comes in diff order ie least val to max ie 3 then 11 then 12
        //so all are done parallely..when a result comes its complete 
        
        //for the runnable submit ..
        es.submit(new Abc());
        //for runnable execute
        es.execute(new Abc());
        //thus submit works for both and execute only for runnable
       
        //now we need to shutdown
        es.shutdown();
    }
}

class Shared{
    static AtomicInteger count = new AtomicInteger(0);
}
class Abc implements Runnable{
    public void run()
    {
        System.out.println("From Abc thread");
    }
}
class Sum implements Callable<Integer>
{
    int val;
    public Sum(int val) {
        this.val=val;
        //new Thread(this).start(); --> this thread class doesnt take arg of type callable..only runnable

    }

    @Override
    public Integer call()
    {
        int sum=0;
        for(int i=1;i<=val;i++)
        {
            sum+=i;
        }
        //System.out.println("Result "+sum);//and its still waiitng as its not yet terminated
        return sum;
    }
}

class Square implements Callable<Double>
{
    double val;
    public Square(double val) {
        this.val=val;
    }

    @Override
    public Double call()
    {
        double square=val*val;
        return square;
    }
}