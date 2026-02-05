import java.lang.reflect.*;
import java.util.Scanner;

class A{
   public String toString(){
    return "From A class Object";
   }
}
class B{
    public String toString(){
    return "From A class Object";
   }
}
class C{
    public String toString(){
    return "From A class Object";
   }
}
public class ReflectionAPIDemo {
    public static void main(String[] args) {
        A a1 = new A();
        try {
            System.out.println("Enter class Name");
            String cname = new Scanner(System.in).next();
            cname.toUpperCase();
            Class c1 = Class.forName(cname);
            Object obj = c1.newInstance();
            
            System.out.println(obj);
            Method m[] = c1.getMethods();
            Constructor c[] =c1.getConstructors();
            Field f[] =c1.getFields();
            for(Method m1 : m){
                System.out.println(m1.getName());
            }
            Class c2 = "HELLO".getClass();
            System.out.println("hello is an object of class "+c2.getName());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
