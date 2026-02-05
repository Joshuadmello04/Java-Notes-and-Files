class A{
    static int x;
}
public class Demo {
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
        A a3 = new A();
        a1.x=50;//50 for all coz of static..makes it a global variable
        A.x=100;//now itll become 100 for all
        System.out.println(a1.x);
        System.out.println(a2.x);
        System.out.println(a3.x);
        System.out.println(A.x);
    }
}
