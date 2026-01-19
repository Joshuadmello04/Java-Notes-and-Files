import java.util.Scanner;
enum Designation{
    CLERK,
    PROGRAMMER,
    MANAGER;
}
class InvalidUserIdException extends Exception {
    public InvalidUserIdException(){}
    public InvalidUserIdException(String msg) {
        super(msg);
    }
}

class Employee {
    private String name;
    //private String designation;
    private Designation designation;
    private int salary;
    private String userId;
    private static final Scanner sc = new Scanner(System.in);

     private void validateUserId(String id) throws InvalidUserIdException {
        String regex = "^(EP|EC)\\d{4}$"; 
        if (!id.matches(regex)) {
            throw new InvalidUserIdException("Invalid User ID! Must be like EP1234 or EC5678");
        }
    }


    public Employee(Designation des, int sal) {
        try {
            System.out.println("Enter your User ID (EP1234 / EC5678): ");
            String id = sc.nextLine();

            validateUserId(id);
            this.userId = id;

            System.out.println("Enter your name: ");
            this.name = sc.nextLine();

            this.salary = sal;
            this.designation = des;

        } catch (InvalidUserIdException e) {
            System.out.println("ERROR: " + e.getMessage());
            
        } catch (Exception e) {
            System.out.println("Some unexpected error: " + e);
        }
    }
    public void raiseSalary() {}

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public int getSalary() {
        return this.salary;
    }

    public void display() {
        System.out.println("----------------------------");
        System.out.println("Name: " + name);
        System.out.println("UserID: " + userId);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);
        System.out.println("----------------------------");
    }

    
}

class Manager extends Employee{
 public Manager() {
        super(Designation.MANAGER, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 20000);
    }
}
class Clerk extends Employee{
    public Clerk() {
        super(Designation.CLERK, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 3000);
    }
}
class Programmer extends Employee{
    public Programmer() {
        super(Designation.PROGRAMMER, 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 10000);
    }
}
public class Assignment {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        try
        {
        System.out.println("Enter employee count");
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
                         sc.nextLine(); //this is so that i clear buffer
                         if(cnt<n)
                         {
                            switch (type) {
                             case 1: emps[cnt++] = new Clerk();
                                     break;
                             case 2: emps[cnt++] = new Programmer();
                                     break;
                             case 3: emps[cnt++] = new Manager();
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
                                if (emps[i] == null) {
                                    throw new NullPointerException("Employee slot " + i + " is empty!");
                                }
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
                                if (emps[i] == null) {
                                    throw new NullPointerException("Employee slot " + i + " is empty!");
                                }
                                emps[i].raiseSalary();
                            }
                            System.out.println("Raised all employee salaries");
                         }
                         break;
                case 4 : System.out.println("Exit");
            }
        } while (ch!=4);
     }
     catch (NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
     } 
     finally{
        System.out.println("Program ends now");
     }
    }
}
