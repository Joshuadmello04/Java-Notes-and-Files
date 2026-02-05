enum Currency{
    JPY("Japanese Yen"),
    INR("Indian Ruppees"),
    SAR("Saudi Riyal");
    String type;
    //or int amt;
    private Currency(String s)
    {
        type =s;
    }
    public String display(){
        return type;
    }

}
enum EmployeeType{
    CONTRACT,
    PERMANENT;
}
class Employee{
    String name;
    int age;
    float salary;
    Currency currency;
    EmployeeType type;

    public String toString(){
        System.out.println("Name" + name);
        System.out.println("Age "+age);
        System.out.println("Salary "+salary);
        System.out.println("Currency "+currency);
        System.out.println("Employee Type "+type);
        return "";
    }
}
public class EnumDemo {
    public static void main(String[] args) {
        Employee e1 = new Employee();
        e1.name="Suraj";
        e1.age=25;
        e1.salary=34234;
        e1.currency=Currency.INR;
        e1.type=EmployeeType.CONTRACT;
        System.out.println(e1);
        System.out.println(e1.currency.display());
    }
}
