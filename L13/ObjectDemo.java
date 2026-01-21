class A implements Cloneable{
    int x;
    public boolean equals(Object obj){
        A a2 = (A)obj;
        if(this.x == a2.x)
                return true;
        return false;
    }
    public String toString(){
        return "A class object with x value: "+x;
    }
    public void finalize(){
        System.out.println("Object with value x "+x+" is getting removed");
    }
    public A clone(){
        try {
            return (A)super.clone();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
public class ObjectDemo  {
    public static void main(String[] args) throws Exception{
        A a1 = new A();
        a1.x = 10;
        A a2 = new A();
        a2.x = 20;
        System.out.println(a1.equals(a2));
        System.out.println(a1.toString());
        System.out.println(a2.toString());
        A a3 = a1.clone();
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());
        System.out.println(a3.hashCode());
        a1 = null;
        a2 = null;
        // a1.finalize();
        System.gc();
        // a1.finalize();
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        Thread.sleep(1000);
    }
}
 