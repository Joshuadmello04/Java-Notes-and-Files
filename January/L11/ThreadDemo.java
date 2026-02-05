
class A extends Thread //now we can make thread from this class
{
    //but threadneeds to be created..we need to put logic in run method()
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("A : "+i);
            try {
                if(i%4==0)
                {
                    Thread.sleep(4000);
                }
            } catch (Exception e) {
            }
        }
    }
}
class B extends Thread //now we can make thread from this class
{
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("B : "+i);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
    }
}

class C extends Thread //now we can make thread from this class
{
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("C : "+i);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
    }
}
public class ThreadDemo {
    public static void main(String[] args) throws Exception {
        A a1 = new A();
        B b1 = new B();
        C c1 = new C();
        //single thread ..
        // a1.abc();
        // b1.abc();
        // c1.abc();

        //now in real world scenario the threads will run simultaneously so we extend thread and use obj.start method
        a1.start();
        b1.start();
        c1.start();
        a1.setName("Badal");
        b1.setName("Joshua");
        //we have created 4 threads ie these 3 and Main thread..
        b1.setPriority(7);
        System.out.println(a1 + " and Priority "+a1.getPriority());
        System.out.println(b1 + " and Priority "+b1.getPriority());
        System.out.println(c1 + " and Priority "+c1.getPriority());
        Thread m1 = Thread.currentThread();
        System.out.println(m1);
        //to show 4th loop
        for (int i = 0; i <= 90; i++) {
            System.out.println("Main : "+i);
            if(i==100){
                a1.join(); //Main thread should wait until a1 finishes its work
                b1.join(); //Main thread should wait until b1 finishes its work
                c1.join(); //Main thread should wait until c1 finishes its work
            }
            if(i==20){
                //which thread to suspend
                b1.suspend();
            }
            if(i==70)
            {
                b1.resume();
            }
            Thread.sleep(500);
        }
        System.out.println("Main exit");
        //main occurs randomly coz the priority is assigned randomly
        //now random order
    }
}
