import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

public class StreamApiDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(22);
        list.add(33);
        list.add(44);
        list.add(65);
        list.add(54);
        list.add(34);
        list.add(84);
        list.add(66);
        System.out.println(list);
        //to filter through massive lists and all we need collections stream api

        //step 1..initialize 
        //list.stream()
        //step 2 .. processing stage to filter etc
        //list.stream().filter();
        //step 3 ... predicate in filter
        Predicate<Integer> p1 = x->x%2==0;
        //list.stream().filter(x->x%2==0);
        //step 4 ... collect the filtered expression as List
        List l1 = list.stream().filter(p1).collect(Collectors.toList());
        System.out.println(l1);

        //when we use filter -> matching

        //what if we wanna perform smth once on every single element
        List l2 = list.stream().map(x-> x*2).collect(Collectors.toList());
        System.out.println(l2);

        Random random = new Random();
        //System.out.println();
        //random.ints().limit(10).sorted().forEach(System.out :: println);

        IntStream is = IntStream.range(1,11);
        is.forEach(System.out::print);
        System.out.println();
        List l3 = Arrays.asList(1,2,3,4,5,6,7);
        l3.stream().limit(5).skip(2).forEach(System.out :: println);

        //filter -> return <=no of elements
        //map -> return all
        //what is reduce -> to condense all to 1 final output

        //ex : avg age of all employees reduce to 1 value amd return
        System.out.println();
        OptionalInt r1 = IntStream.range(1,6).reduce((a,b)->a+b); //sum of all elements
        System.out.println(r1.getAsInt());

        System.out.println();
        OptionalInt r2 = IntStream.range(1,6).reduce((a,b)->a*b); //sum of all elements
        System.out.println(r2.getAsInt());

        System.out.println();
        int reduced2Param = IntStream.range(1,6).reduce(10,(a,b)->a+b);
        //this means it starts initially as 10..then 10+1=11,11+2=13,13+3=16....
        System.out.println(reduced2Param);

        //to do things parallely
        System.out.println();
        int reduced = Stream.of(1,4).reduce(10, (a,b)->a+b, (a,b)->a+b);
        System.out.println(reduced);

        System.out.println();
        int r4 = Arrays.asList(1,4).parallelStream().reduce(10, (a,b)->a+b, (a,b)->a+b);
        System.out.println(r4);

        System.out.println();
        IntSummaryStatistics stats = IntStream.rangeClosed(1,10).summaryStatistics();
        System.out.println(stats);
    }
}
