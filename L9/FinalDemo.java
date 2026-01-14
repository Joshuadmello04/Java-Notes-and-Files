class A{
    final int x =10;
    public final void abc()
    {
        System.out.println("HI");
    }
}

final class B extends A{

}

public class FinalDemo {
    public static void main(String[] args) {
        A a1 = new A();
        a1.abc();
        System.out.println(a1.x);
        B b1 = new B();
        b1.abc();
    }
}
