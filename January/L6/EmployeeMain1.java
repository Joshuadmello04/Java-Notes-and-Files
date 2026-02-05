import java.util.Scanner;

class Employee {
    private String name;
    private int age;
    private String designation;
    private int salary;

    // Use a single shared Scanner to avoid multiple input streams
    private static final Scanner sc = new Scanner(System.in);

    public Employee(String des, int sal) {
        System.out.println("Enter your following details: ");
        System.out.print("Name: ");
        this.name = sc.nextLine().trim();

        System.out.print("Age: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid integer for Age: ");
            sc.next(); // consume invalid token
        }
        this.age = sc.nextInt();
        sc.nextLine(); // consume the leftover newline
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
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("Age: " + age);
        System.out.println("Designation: " + designation);
        System.out.println("----------------------------");
    }
}

class Clerk extends Employee {
    public Clerk() {
        super("CLERK", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 3000);
    }
}

class Programmer extends Employee {
    public Programmer() {
        super("PROGRAMMER", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 10000);
    }
}

class Manager extends Employee {
    public Manager() {
        super("MANAGER", 20000);
    }
    public void raiseSalary() {
        setSalary(getSalary() + 20000);
    }
}

public class EmployeeMain1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int ch = 0;
        Employee empManager = null;
        Employee empClerk = null;
        Employee empProgrammer = null;

        do {
            System.out.println("----------------------------");
            System.out.println("1. Create (1 Manager, 1 Clerk, 1 Programmer)");
            System.out.println("2. Display All");
            System.out.println("3. Raise Salary (All)");
            System.out.println("4. Exit");
            System.out.println("----------------------------");
            System.out.print("Enter Choice: ");

            ch = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (ch) {
                case 1:
                    
                        System.out.println("Creating Manager:");
                        empManager = new Manager();
                        System.out.println("Creating Clerk:");
                        empClerk = new Clerk();
                        System.out.println("Creating Programmer:");
                        empProgrammer = new Programmer();
                        System.out.println("All employees created successfully.");
                        break;

                case 2:
                    
                        System.out.println("=== Employee Details ===");
                        empManager.display();
                        empClerk.display();
                        empProgrammer.display();
                        break;

                case 3:
                        empManager.raiseSalary();
                        empClerk.raiseSalary();
                        empProgrammer.raiseSalary();
                        System.out.println("Salary raised for all employees.");
                    break;
            }

        } while (ch != 4);
    }
}
