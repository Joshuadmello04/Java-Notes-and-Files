/*Should add recrod in fie when we create with details
have all options like create,display,raise,delete
read all record and display in proper format with label
all records should be added in employee.ser in assignment folder */


import java.io.*;
import java.util.*;
enum Designation {
    CLERK,
    PROGRAMMER,
    MANAGER
}

enum Gender {
    MALE,
    FEMALE
}

abstract class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    private String employeeID;
    private String name;
    private Gender gender;
    private int age;
    private int salary;
    private final Designation designation;

    Employee(int salary, String designation, Scanner sc) {
        System.out.print("Enter Name: ");
        this.name = sc.next();
        System.out.print("Enter Age: ");
        this.age = sc.nextInt();
        System.out.print("Enter Employee ID (Format: E[C/P]XXXX): ");
        String empID = sc.next();

        while (true) {
            if (!empID.matches("^E[C/P][0-9]{4}$")) {
                System.out.print("Invalid format. Re-enter (E[C/P]XXXX): ");
                empID = sc.next();
            }
            else {
                this.employeeID = empID;
                break;
            }
        }

        System.out.print("Enter Gender (MALE/FEMALE): ");
        String gender = sc.next().toUpperCase();
        if(gender.equals("MALE") || gender.equals("FEMALE")) {
            this.gender = Gender.valueOf(gender);
        }
        this.salary = salary;
        this.designation = Designation.valueOf(designation);
    }

    public String getEmployeeID() 
    {
        return employeeID;
    }

    public void raiseSalary(int amount) {
        salary += amount;
    }
    public final void display() {
        System.out.println("---------------------------");
        System.out.println("Name        : " + name);
        System.out.println("Employee ID : " + employeeID);
        System.out.println("Age         : " + age);
        System.out.println("Gender      : " + gender);
        System.out.println("Salary      : " + salary);
        System.out.println("Designation : " + designation);
        System.out.println("---------------------------");
    }
}

final class Clerk extends Employee {
    Clerk(Scanner sc) {
        super(20000, "CLERK", sc); 
    }
}

final class Programmer extends Employee {
    Programmer(Scanner sc) {
        super(50000, "PROGRAMMER", sc);
    }
}

final class Manager extends Employee {
    public Manager(Scanner sc) {
        super(100000, "MANAGER", sc);
    }
}

class Menu {
    public void displayMenu() {
        System.out.println("1. Create Employee");
        System.out.println("2. Display Employee Details");
        System.out.println("3. Raise Salary");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
    }

    public void displayEmployeeTypes() {
        System.out.println("Select Employee Type to Create:");
        System.out.println("1. Clerk");
        System.out.println("2. Programmer");
        System.out.println("3. Manager");
    }

    public int getChoice(Scanner sc) {
        System.out.print("Enter choice: ");
        return sc.nextInt();
    }
}

public class Assignment {
    static ArrayList<Employee> empList = new ArrayList<>(); //global in class not belonging to Employee object
    static final String FILE = "assignment/employee.ser"; // for file
    // in start we have to load
    //read employee from file and load to ram
    static void loadEmployees() { //for deserialization
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
            empList = (ArrayList<Employee>) ois.readObject(); //append content to arraylist of emp
            ois.close();
        } catch (Exception e) {
            empList = new ArrayList<>();//if its first time then start with new/fresh empty emp arraylist
        }
    }
    //in the end we have to serialize
    //save all content in persistent ie ram
    static void saveEmployees() { //this is for serialization
        try {
            new File("assignment").mkdir();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));//once read and saved now write to file..if exists overwrite else create
            oos.writeObject(empList);//convert emplist to bytes and write to file the ser file
            oos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadEmployees();//coz afterwards employees exist already right coz persistemt storage 
        Menu menu = new Menu();
        int ch;
        do {
            menu.displayMenu();
            ch = menu.getChoice(sc);
            switch (ch) {
                case 1:
                    menu.displayEmployeeTypes();
                    int empChoice = menu.getChoice(sc);
                    Employee e = null;
                    if(empChoice == 1) {
                         e = new Clerk(sc);
                    } else if (empChoice == 2) {
                         e = new Programmer(sc);
                    } else if (empChoice == 3) {
                         e = new Manager(sc);
                    } else {
                        System.out.println("Invalid choice");
                    }
                    empList.add(e);
                    saveEmployees();
                    System.out.println("Employee created successfully");
                    break;

                case 2:
                    if (empList.isEmpty()) {
                        System.out.println("No records found");
                    } else {
                        for (Employee emp : empList) {
                            emp.display();//read from ram
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter Employee ID: ");
                    String id = sc.next();
                    System.out.print("Enter raise amount: ");
                    int amt = sc.nextInt();
                    for (Employee emp : empList) {
                        if (emp.getEmployeeID().equals(id)) {
                            emp.raiseSalary(amt);
                        }
                    }
                    saveEmployees();//serialized again
                    System.out.println("Salary updated");
                    break;
                
                case 4:
                    System.out.print("Enter Employee ID: ");
                    String delId = sc.next();
                    empList.removeIf(emp -> emp.getEmployeeID().equals(delId));
                    saveEmployees();
                    System.out.println("Employee deleted");
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (ch != 6);
    }
}