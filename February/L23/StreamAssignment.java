import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamAssignment {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", 28, "Programmer", "IT", 55000, "Male"));
        employees.add(new Employee("Sara", 32, "Manager", "HR", 75000, "Female"));
        employees.add(new Employee("Michael", 25, "Clerk", "Finance", 48000, "Male"));
        employees.add(new Employee("Priya", 30, "Manager", "IT", 68000, "Female"));
        employees.add(new Employee("Arjun", 29, "Clerk", "Tech Support", 42000, "Male"));
        employees.add(new Employee("Neha", 27, "Programmer", "UI/UX", 50000, "Female"));
        // IT — simplified
        employees.add(new Employee("Aarav", 26, "Programmer", "IT", 42000, "Male"));
        employees.add(new Employee("Priya", 30, "Programmer", "IT", 72000, "Female"));
        employees.add(new Employee("Rahul", 34, "Manager", "IT", 90000, "Male"));
        employees.add(new Employee("Neha", 28, "Clerk", "IT", 52000, "Female"));
        employees.add(new Employee("Vikram", 31, "Programmer", "IT", 78000, "Male"));
        // HR — simplified
        employees.add(new Employee("Sara", 32, "Manager", "HR", 75000, "Female"));
        employees.add(new Employee("Rohan", 27, "Clerk", "HR", 48000, "Male"));
        employees.add(new Employee("Ananya", 29, "Manager", "HR", 68000, "Female"));
        // Finance — simplified
        employees.add(new Employee("Michael", 25, "Clerk", "Finance", 50000, "Male"));
        employees.add(new Employee("Isha", 33, "Clerk", "Finance", 73000, "Female"));
        employees.add(new Employee("Karan", 37, "Manager", "Finance", 88000, "Male"));
        // Sales — simplified
        employees.add(new Employee("Reema", 26, "Clerk", "Sales", 46000, "Female"));
        employees.add(new Employee("Arjun", 31, "Manager", "Sales", 82000, "Male"));
        employees.add(new Employee("Meera", 29, "Clerk", "Sales", 67000, "Female"));
        // Operations — simplified
        employees.add(new Employee("Deepak", 35, "Manager", "Operations", 84000, "Male"));
        employees.add(new Employee("Kriti", 27, "Clerk", "Operations", 52000, "Female"));

        //1. to get highst salary paid employee
        Employee highest = employees.stream().max(Comparator.comparing(Employee::getSalary)).get();
        System.out.println("Highest Paid Employee : \n"+highest);
        System.out.println("---------------------");
        //2. to find count of Male and Female employees
        Map<String,Long> m1 = employees.stream().collect(Collectors.groupingBy(e->e.gender,Collectors.counting()));
        System.out.print("Gender Count : ");
        System.out.println(m1);
        System.out.println("---------------------");
        //3. total expenses per department
        Map<String,Integer> m2 = employees.stream().collect(Collectors.groupingBy(e->e.dept,Collectors.summingInt(e->e.salary)));
        System.out.println("Expense Per Department");
        System.out.println(m2);
        System.out.println("---------------------");
        //4. top 5 senior employees
        List<Employee> top5 = employees.stream().sorted(Comparator.comparingInt((Employee e)->e.age).reversed()).limit(5).collect(Collectors.toList());
        //System.out.println(top5);
        System.out.println("Top 5 senior employee");
        top5.forEach(e->System.out.println(e));
        System.out.println("---------------------");
        //5. names of all the managers
        List<String> names = employees.stream().filter(e->e.designation.equalsIgnoreCase("Manager")).map(e->e.name).collect(Collectors.toList());
        System.out.println("Manager Names");
        names.forEach(e->System.out.println(e));
        //employees.stream().filter(e->e.designation.equalsIgnoreCase("Manager")).map(e->e.name).forEach(System.out::println);
        //System.out.println(names);
        System.out.println("------------------");
        //6. Hike salary for all except manager
        Predicate<Employee> p = (x)->!x.designation.equalsIgnoreCase("Manager");
        
        List<Double> updated = employees.stream().filter(p).map(e->e.salary*1.2).collect(Collectors.toList());
        updated.forEach(System.out :: println);

        //7. find total no of employees
        long count = employees.stream().count();
        System.out.println("Num employees : "+count);
    }
}

class Employee{
    String name;
    int age;
    String designation;
    String dept;
    int salary;
    String gender;

    public Employee(String n,int a,String d,String dep,int sal,String g)
    {
        name =n;
        age = a;
        designation =d;
        dept =dep;
        salary=sal;
        gender=g;
    }

    public int getSalary() {
        return salary;
    }
    
    @Override
    public String toString()
    {
        return "("+name+","+age+","+designation+","+dept+","+salary+","+gender+")";

    }
}
