class A implements Runnable{
    //object level lock
    synchronized public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+": "+ i);
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
//but below its already extending..so we have to implement Runnable
class B extends Exception implements Runnable{
    @Override
    public void run(){
        synchronized (B.class) { //class level lock
            for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+": "+ i);
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        }
    }
}

public class ThreadDemo3 {
    public static void main(String[] args) {
        // A a1 = new A();
        // A a2 = new A();
        // A a3 = new A();

        // Thread t1 = new Thread(a1);//but single thread of single obj
        // Thread t2 = new Thread(a2);
        // Thread t3 = new Thread(a3);
        // t1.setName("Badal");
        // t2.setName("Joshua");
        // t3.setName("Aryan");
        // t1.start();
        // t2.start();
        // t3.start();

        //class lvl lock
        B b1 = new B();
        B b2 = new B();
        B b3 = new B();
        Thread t4 = new Thread(b1);//but single thread of single obj
        Thread t5 = new Thread(b2);
        Thread t6 = new Thread(b3);
        t4.setName("Badal");
        t5.setName("Joshua");
        t6.setName("Aryan");
        t4.start();
        t5.start();
        t6.start();
    }
}
