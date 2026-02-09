
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        Integer source1 = 10;
        Integer source2 = null; //will give error

        //therefore we need optional
        Optional<Integer> o1 = Optional.ofNullable(source1);
        Optional<Integer> o2 = Optional.ofNullable(source2);

        Calculator c1 = new Calculator();
        c1.add(o1, o2);
    }
}

class Calculator{
    public void add(Optional<Integer> o1,Optional<Integer> o2)
    {
        Integer res = o1.orElse(0)+o2.orElse(0);
        System.out.println("Total : "+res);
    }
}
