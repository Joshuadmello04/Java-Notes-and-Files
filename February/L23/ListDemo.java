import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListDemo {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1,2,3);
        List<Integer> l2 = Arrays.asList(4,5,6,7);
        List<Integer> l3 = Arrays.asList(8,9,10);

        List<List<Integer>> list = Arrays.asList(l1,l2,l3);
        System.out.println(list);

        list.stream().forEach(System.out::println);//prints list on each line
        //List<Integer> result = list.stream().map(x-> x+x).collect(Collectors.toList()); --> wont work as List of List..so + wont work
        List<Integer> result = list.stream().flatMap(lst -> lst.stream()).collect(Collectors.toList());
        //System.out.println(result);

        //now we can perform map and all
        List<Integer> res2 = list.stream().flatMap(lst -> lst.stream()).map(x->x*2).collect(Collectors.toList());
        System.out.println(res2);
    }
}
