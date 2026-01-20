class A extends Thread // u have to override run method to make it a thread
{
	public void run()
	{
		for(int i=1; i<=20; i++)
		{
			System.out.println("A : "+i);
			try
			{
				if(i%3==0)
					Thread.sleep(2000);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
class B extends Thread
{
	public void run()
	{
		for(int i=1; i<=20; i++)
		{
			System.out.println("B : "+i);
			try
			{
				if(i%4==0)
					Thread.sleep(4000);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
class C extends Thread
{
	public void run()
	{
		for(int i=1; i<=20; i++)
		{
			System.out.println("C : "+i);
			try
			{
					Thread.sleep(1500);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
public class ThreadDemo2
{
	public static void main(String args[]) throws Exception
	{
		A a1 = new A();
		B b1 = new B();
		C c1 = new C();
        //adding threads to thread group for bulk operations on threads
        ThreadGroup tg = new ThreadGroup("Demo");
        Thread t1 = new Thread(tg,a1);
        Thread t2 = new Thread(tg,b1);
        Thread t3 = new Thread(tg,c1);

        t1.start();
        t2.start();
        t3.start();
        
        for(int i=1;i<=90;i++)
        {
            System.out.println("Main "+i);
            if(i==10)
                tg.suspend();
            if(i==40)
                tg.resume();
            if(i==50)
                tg.stop();
            if(i==70)
                tg.resume();
            Thread.sleep(500);
        }

        System.out.println("Main exit");
	}
}