import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewDemo {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Rakesh", 22, 25000, "Tester"));
        list.add(new Employee("Ramesh", 32, 32000, "Programmer"));
        list.add(new Employee("Suresh", 42, 28000, "Tester"));
        list.add(new Employee("Dinesh", 52, 45000, "Manager"));
        list.add(new Employee("Mukesh", 62, 36000, "Tester"));
        list.add(new Employee("Jignesh", 48, 48000, "Tester"));
        list.add(new Employee("Mangesh", 24, 27000, "Programmer"));
        list.add(new Employee("Ganesh", 21, 25000, "Tester"));
        //get age above 40 and below 60
        //so for such case we need partitioningBy()
        //so lets make map object of boolean key
        Map<Boolean,List<Employee>> m1 =list.stream().collect(Collectors.partitioningBy(e->e.age>30));
        System.out.println(m1);
        System.out.println("-----------------");
        //now we want partition and then count as well >30 and <30
        Map<Boolean,Long> m2 =list.stream().collect(Collectors.partitioningBy(e->e.age>30,Collectors.counting()));
        System.out.println(m2);
        System.out.println("-----------------");
        //now we want designation wise
        Map<String,List<Employee>> m3 = list.stream().collect(Collectors.groupingBy(e->e.designation));
        System.out.println(m3);
        System.out.println("-----------------");
        //now to count
        Map<String,Long> m4 = list.stream().collect(Collectors.groupingBy(e->e.designation,Collectors.counting()));
        System.out.println(m4);
        System.out.println("-----------------");
        Map<String,List<String>> m5 = list.stream().collect(Collectors.groupingBy(e->e.name,Collectors.mapping(e->e.name.toUpperCase(),Collectors.toList())));
        System.out.println(m5);
        //Sum of designation grp salaries
        Map<String,Integer> m6 = list.stream().collect(Collectors.groupingBy(e->e.designation,Collectors.summingInt(e->e.salary)));
        System.out.println(m6);
    }
}

class Employee{
     String name;
     int age;
     int salary;
     String designation;
    
    public Employee(String n,int a,int s,String d)
    {
        name = n;
        age =a;
        salary=s;
        designation=d;
    }

    public String toString()
    {
        return "("+name+", "+age+", "+salary+", "+designation+")";
    }
}
