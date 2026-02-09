import java.util.*;
import java.util.function.Consumer;
class A{
    public static void abc(Integer i){
            System.out.println("Custom logic "+i);
    }
}
public class ForEach {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(11,22,33,44);
        //iterator method
        Iterator i = list.iterator();
        while(i.hasNext())
        {
            System.out.print(i.next()+" ");
        }
        System.out.println();
        //for each loop not method
        for(int x:list)
        {
            System.out.print(x+" ");
        }
        System.out.println();
        System.out.println("Using forEach() method");
        
        list.forEach((x)->{
            System.out.println("Consumed "+x);
        });

        list.forEach(System.out::println);//print on new line every member of list

        list.forEach(A::abc);

        Consumer<Integer> c1 = (a)->System.out.println("Writing to file: "+a);
        Consumer<Integer> c2 = (a)->System.out.println("Writing to db "+a);
        Consumer<Integer> c3 = (a)->System.out.println("Calling REST API "+a);

        list.forEach(c1.andThen(c2).andThen(c3));
    }
}
