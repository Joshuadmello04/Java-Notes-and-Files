
class A{
 void abc()
 {
    System.out.println("HI");
 }
}
class B extends A{
  void abc()
  {
    System.out.println("Hello");
  }
  void abc(int x)
  {
    System.out.println("Meow");
  }
}
public class RuntimePolymorphism {
    public static void main(String[] args) {
        A a1 = new B();
        a1.abc();//overriding..it executes B's abc()
        //a1.abc(11); //wont work coz its overloading...it will look at the methods of A u will have to cast type to B
        //B b1 = new A(); ...wont work
    }
}
