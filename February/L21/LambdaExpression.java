interface I{
    void abc();
    public abstract int hashCode();
    public abstract boolean equals(Object obj);
}

interface J{
    int add(int a,int b);
    //int subtract(int a,int b);...wont be allowed as lambda fn doesnt know whether to add or subract then
    //public abstract int sub(int a,int b);//then its allowed
}
// class A implements I{
//     public void abc(){
//         System.out.println("IMplementing to a class");
//     }
// }

public class LambdaExpression{
    public static void main(String[] args) {
        // I i1 = new A();
        // i1.abc();

        // I i2 = new I() //anonymous class
        // {
        //     public void abc(){
        //         System.out.println("Using anonymous class");
        //     }
        // };
        // i2.abc();

        //Lambda Expression..if multiple line code block -> use '{}'..else no need
        I i3 = () -> {
            System.out.println("From lambda expression");
        };
        i3.abc();

        J j1 =(x,y)->x+y; //return is not mandatory
        System.out.println("Sum is "+j1.add(10, 20));
    }
}