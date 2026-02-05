import java.util.Scanner;

class Employee {
    private String name;
    private String designation;
    private int salary;
    private static final Scanner sc = new Scanner(System.in);

    public Employee(String des, int sal) {
        System.out.println("Enter your name ");
        this.name = sc.nextLine();
        this.salary = sal;
        this.designation = des;
    }
    public void raiseSalary() {}

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getSalary() {
        return this.salary;
    }

    public void display() {
        System.out.println("----------------------------");
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
        System.out.println("----------------------------");
    }
}

class Manager extends Employee{
 public Manager() {
        super("MANAGER", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 20000);
    }
}
class Clerk extends Employee{
    public Clerk() {
        super("CLERK", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 3000);
    }
}
class Programmer extends Employee{
    public Programmer() {
        super("PROGRAMMER", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 10000);
    }
}
public class EmployeeMenu {
    public static void main(String[] args) {
        System.out.println("Enter employee count");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Employee[] emps = new Employee[n];
        int ch;
        int cnt =0;
        do {
            System.out.println("---------");
            System.out.println("1. Create");
            System.out.println("2. Display");
            System.out.println("3. Raise Salary");
            System.out.println("4. Exit");
            System.out.println("---------");
            ch = sc.nextInt();
            switch(ch)
            {
                case 1 : System.out.println("--------------------------");
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
                case 4 : System.out.println("Exit");
            }
        } while (ch!=4);

    }
}
