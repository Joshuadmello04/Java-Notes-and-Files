import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        Map<Integer,String> map = new HashMap<>();
        map.put(11, "Ramesh");
        map.put(22, "Suresh");
        map.put(33, "Dinesh");
        map.put(44, "Rajesh");
        map.put(55, "Mahesh");

        //map doesnt extend util collection
        //thus iterator wont work map like list,Queue and Set

        //thus to traverse,wwe use a set of unique keys
        //Set s = map.entrySet();
        // Iterator i = s.iterator();

        // while(i.hasNext())
        // {
        //     //System.out.println(i.next()); --> returns a map object..so we typecast
        //     Map.Entry me = (Map.Entry) i.next();
        //     System.out.println("ID : "+me.getKey());
        //     System.out.println("Name : "+me.getValue());
        // }

        //but if we have set...just make a stream in set
        //s.stream(); --> ie map.entrySet().stream()
        //map.entrySet().stream().map(Map.Entry::getKey);// this will get keys
        map.entrySet().stream().map(Map.Entry::getKey).forEach(System.out::println);

        map.entrySet().stream().map(Map.Entry::getValue).forEach(System.out::println);

        System.out.println("--------");
        map.entrySet().stream().filter(me->me.getKey()>20).map(Map.Entry :: getValue).forEach(System.out::println);
    }
}
