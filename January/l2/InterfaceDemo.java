interface  X{
    int c=30;
     void add();
     void sub();
}

interface  Y{
    int b=10;
    void mul();
    void div();
}

interface Z extends X,Y
{
    void mod();
}

 class Calculator implements Z{
    public void sub()
    {
        System.out.println(X.c-Y.b);
    }
    public void add()
    {
        System.out.println(X.c+Y.b);
    }
    public void mul()
    {
        System.out.println(Z.c*Z.b);
    }
    public void div()
    {
        System.out.println(X.c/Y.b);
    }

    public void mod()
    {
        System.out.println(X.c%Y.b);
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Calculator c1 = new Calculator();//for reusability
        //client with  X interface
        X x1 = c1;
        x1.add();
        x1.sub();
        //Client with Y interface
        Y y1 = c1;
        y1.mul();
        y1.div();
        //client with all 4 requirements needs Z interface
        Z z1 = c1;
        z1.add();
        z1.sub();
        z1.mul();
        z1.div();
        z1.mod();
    }
}