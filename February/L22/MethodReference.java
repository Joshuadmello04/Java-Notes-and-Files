interface I{
    public void abc();
}
class Demo {
    Demo()
    {
        System.out.println("From demo() constructor");
    }   
    Demo(String name){
        System.out.println(name+" object created");
    }
    public static void staticMethod()
    {
        System.out.println("From static method");
    }
    public void instanceMethod()
    {
        System.out.println("From instance method");
    }
}

interface J{
    public Demo getObject(String name);
}

public class MethodReference{
    public static void main(String[] args) {
        I i1 = () ->{}; //empty lambda function just to call wthout object
        i1.abc();

        I i2 = Demo::staticMethod;//we refer to the abc method of interface to staticmethod
        i2.abc();//means whenever abc is called run demo.staticmethod

        //now we wanna refer non static method
        //I i3 = Demo::instanceMethod; //error coz its an instance method we need obj to refer it
        I i3 = new Demo()::instanceMethod;
        i3.abc();

        //to call a constructor..new keyword
        I i4 = Demo::new;//to call the demo class
        i4.abc();

        //J j1 = Demo::new;//but no parameter given so it maps to I
        //RULE : Method reference is only possible between two methods when their parameters match

        J j1 = Demo::new;
        Demo d1 = j1.getObject("Rahul");
        Demo d2 = j1.getObject("Mehul");
        Demo d3 = j1.getObject("Jeet");

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }
}