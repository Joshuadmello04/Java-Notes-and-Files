import java.util.function.*;

public class PredicateDemo {
    public static void main(String[] args) {
        int arr[] = {11,22,33,44,55,66,77,88,99};
        // evenNumbers(arr);
        // oddNumbers(arr);
        //|| BUT THIS IS OLD||
        //instead we use Predicate
        Predicate<Integer> p1 = (x)->x%2 ==0;
        process(arr,p1);//for even no's

        //Predicate<Integer> p2 = (x)->x%2!=0;
        //process(arr, p2);///same function for odd now

        //even better for odd number
        process(arr, p1.negate());

        //now what if above 50 and odd
        Predicate<Integer> p2 = (x)->x<50;
        process(arr, p2);
        //and above 50
        process(arr,p2.negate());
        
        //now wb below 50 for even no
        process(arr,p1.and(p2)); //even no below 50
        process(arr,p1.and(p2.negate()));//even and above 50

        //odd above 50
        process(arr, p1.negate().and(p2.negate()));

        //either even or above 50
        process(arr, p1.or(p2.negate()));

        //either odd or above 50
        process(arr, p1.negate().or(p2.negate()));
    }
    public static void process(int[] arr,Predicate<Integer> p)
    {
        for(int i=0;i<arr.length;i++)
        {
            if(p.test(arr[i]))
            {
                System.out.println(arr[i]);
            }
        }
        System.out.println("--------------------");
    }

}
