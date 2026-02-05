import java.util.Scanner;
enum Designation{
    MANAGER,
    CLERK,
    PROGRAMMER;
}

enum EmployeeType{
    CONTRACT("EC"),
    PERMANENT("EP");

    private final String type;
 
    EmployeeType(String id) {
        this.type = id;
    }

    public String getType()
    {
        return type;
    }
    
}
abstract class Employee{
    String name;
    float salary;
    String empId; 
    String gender;
    Designation designation;
    EmployeeType empType;
    static int empCount=0;
    private static final Scanner sc = new Scanner(System.in);
    
    public Employee(Designation des, float  sal) {
        this.salary = sal;
        this.designation = des;
        System.out.println("Enter your name ");
        this.name = sc.nextLine();
        System.out.println("Enter your gender 1-M,2-F");
        int ch = sc.nextInt();
        this.gender = (ch==1)? "Male":"Female";
        System.out.println("Enter Employee Type (Permanent or Contract)");
        sc.nextLine();
        String type = sc.nextLine();
        this.empType = EmployeeType.valueOf(type);
        System.out.println("Enter your id ");
        this.empId = sc.nextLine();
        if(!validateId(empId))
        System.out.println("invalid id");
        empCount++;
    }
    public abstract void raiseSalary();

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public float getSalary() {
        return this.salary;
    }

    public boolean validateId(String s)
    {
        String regex = "^[E][CP][0-9]{4}$";
        if(s.matches(regex))
        {
            System.out.println("Valid Id");
            return true;
        }
        else{
            return false;
        }
    }
    public int getCount()
    {
        return empCount;
    }
    public final void display() {
        System.out.println("----------------------------");
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
        System.out.println("Gender : "+ gender);
        System.out.println("ID : "+ empId);
        System.out.println("Type : "+empType);
        System.out.println("----------------------------");
    }
}

final class Manager extends Employee{
 public Manager() {
        super(Designation.MANAGER, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 20000);
    }
}
final class Clerk extends Employee{
    public Clerk() {
        super(Designation.CLERK, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 3000);
    }
}

final class Programmer extends Employee{
    public Programmer() {
        super(Designation.PROGRAMMER, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 10000);
    }
}
public class EmployeeWithEnum {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee limit");
        int n = sc.nextInt();
        Employee[] emps = new Employee[n];
        int cnt =0;
        int ch=0;
        do{
            System.out.println("---------");
            System.out.println("1. Create");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Exit");
            System.out.println("---------");
            ch = sc.nextInt();
            switch (ch) {
                case 1:  System.out.println("--------------------------");
                         System.out.println("1. Clerk");
                         System.out.println("2. Programmer");
                         System.out.println("3. Manager");
                         System.out.println("4. Exit");
                         System.out.println("--------------------------");
                         System.out.print("Enter choice: ");
                         int type = sc.nextInt();
                         if(cnt<n)
                         {
                            switch (type) {
                             case 1: emps[cnt++] = new Manager();
                                     break;
                             case 2: emps[cnt++] = new Clerk();
                                     break;
                             case 3: emps[cnt++] = new Programmer();
                                     break;
                             case 4 : break;
                            }
                        }
                         else{
                            System.out.println("Employee count full");
                         }
                         break;
                case 2 : if (cnt == 0)
                         {
                          System.out.println("No employees to display");
                         }
                         else{
                            for(int i=0;i<cnt;i++)
                            {
                                emps[i].display();
                            }
                         }
                         break;
                case 3 : if(cnt==0)
                         {
                             System.out.println("no employee to raise");    
                         }
                         else{
                            for(int i=0;i<cnt;i++)
                            {
                                emps[i].raiseSalary();
                            }
                            System.out.println("Raised all employee salaries");
                         }
                case 4 : System.out.println("No of employees created : "+cnt);
                System.out.println("Exit");
            }

        }while(ch!=4);
                         
    }
}
