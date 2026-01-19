
class A extends Thread //now we can make thread from this class
{
    //but threadneeds to be created..we need to put logic in run method()
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("A : "+i);
        }
    }
}
class B extends Thread //now we can make thread from this class
{
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("B : "+i);
        }
    }
}

class C extends Thread //now we can make thread from this class
{
    public void run(){
        for(int i=1;i<=20;i++){
            System.out.println("C : "+i);
        }
    }
}
public class ThreadDemo {
    public static void main(String[] args) {
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
        //now random order
    }
}
