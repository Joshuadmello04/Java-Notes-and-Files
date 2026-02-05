class Calculator{
    public void add(int ...a)
    {
        int res = 0;
        for(int i=0;i<a.length;i++)
        {
            res += a[i];
        }
        System.out.println("Result : "+res);
    }
}

class Geometry{
    public void area(int r)
    {
        System.out.println("Circle area : "+ (3.142*r*r));
    }
    public void area(int a,int b)
    {
        if(a==b)
    {
        System.out.println("Square area : "+(a*a));
    }
    else 
    System.out.println("Rectangle area "+(a*b));
}
}

public class Overloading{
   public static void main(String[] args) {
    Calculator c1 = new Calculator();
    c1.add(10,20);
    c1.add(10,20,30);
    c1.add();
    Geometry g1 = new Geometry();
    g1.area(10);
    g1.area(10,10);
    g1.area(10,20);
   }
}