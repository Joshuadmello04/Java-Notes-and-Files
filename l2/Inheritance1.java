class A{
    int x =10;
    public void abc()
    {
        System.out.println("HI");
    }
}

class B extends A{
    int y =20;
    public void xyz()
    {
        System.out.println("Hello");
    }
}

class C extends B{
    public void mow()
    {
        System.out.println("Thanks");
    }
}

class D extends B{
    
}

//error : u cant extend more than 1 "class"
// class E extends B,E{}
public class Inheritance1 {
    public static void main(String[] args) {
        A a1 = new A();
        System.out.println(a1.x);
        a1.abc();

        B b1 = new B();
        System.out.println(b1.x);
        b1.abc();
        b1.xyz();

        C c1 = new C();
        System.out.println(c1.x);
        c1.abc();
        c1.xyz();
        c1.mow();
    }
}
