class A{
    int x=10;
    public void abc()
    {
        System.out.println("HI");
    }
}
class B extends A{
    int x =50;
    public void abc()
    {
        System.out.println("Hello");
        super.abc();
    }
    public void display()
    {
        System.out.println(x);
        System.out.println(super.x);
        abc();
        super.abc();
    }
}
public class Inheritance2 {
    public static void main(String[] args) {
        B b1 = new B();
        b1.display();
    }
}
