interface X{
    public default void abc(){
        System.out.println("from X.abc() method");
    }
}

interface Y{
    public default void abc()
    {
        System.out.println("from Y.abc() method");
    }
    //this would show error in version < 8  for private methods
    private void xyz(){
        System.out.println("Extra Logic");
    }
}

class A implements X,Y{
    public void abc(){
        //specify which interface to use
        X.super.abc();//now itll resolve
        Y.super.abc();//
        //we can add our own logic too
        System.out.println("Hello from A Class");
    }
}


public interface InterfaceNewFeature{
    public static void main(String[] args) {
        System.out.println("Interface Demo");
        A a1 = new A();
        a1.abc();
    }
}