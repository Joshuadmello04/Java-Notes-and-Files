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

abstract class Employee {
    private String employeeID;
    private String name;
    private Gender gender;
    private int age;
    private int salary;
    private Designation designation;



    Employee(int salary, String designation, Scanner sc) {

        File dir = new File("assignment");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, "employees.csv");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

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
        System.out.println("1. Append employee details to file  2. Overwrite employee details in file");
        int ch = sc.nextInt();
        switch(ch) {
            case 1:
                appendToFile();
                break;
            case 2:
                overwriteToFile();
                break;
            default:
                System.out.println("Invalid choice. Skipping file operation.");
        }

        
    }


    public String getEmployeeID() {
    return employeeID;
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

    public void appendToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("assignment/employees.csv", true));
            pw.println(employeeID + "," + name + "," + age + "," + gender + "," + salary + "," + designation);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
}
    public void overwriteToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("assignment/employees.csv"));
            pw.println(employeeID + "," + name + "," + age + "," + gender + "," + salary + "," + designation);
            pw.flush(); 
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
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
        System.out.println("3. Exit");


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

public class EmpMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        int ch;


        do {
            menu.displayMenu();
            ch = menu.getChoice(sc);

            switch (ch) {
                case 1:
                    menu.displayEmployeeTypes();
                    int empChoice = menu.getChoice(sc);
                    if (empChoice == 1) {
                        Employee e = new Clerk(sc);
                        
                    } else if (empChoice == 2) {
                        Employee e = new Programmer(sc);
                    } else if (empChoice == 3) {
                        Employee e = new Manager(sc);
                    } else {
                        System.out.println("Invalid choice");
                    }
                    System.out.println("Employees created successfully");
                    break;

                case 2:
                    try{
                        System.out.println("Displaying Employee Details");
                        BufferedReader fr = new BufferedReader(new FileReader(new File("assignment/employees.csv")));
                        String line = null;
                        while((line = fr.readLine()) != null){
                            System.out.println(line);
                        }
                        fr.close();
                    }catch(Exception e){
                        System.out.println(e);
                    }


                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (ch != 6);
    }
}