
import java.sql.*;
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
    protected String employeeID;
    protected String name;
    protected final Gender gender;
    protected int age;
    protected int salary;
    protected final Designation designation;

    Employee(int baseSalary, Designation designation, Scanner sc) {
        this.designation = designation;
        this.salary = baseSalary;
        System.out.print("Enter Name: ");
        this.name = sc.next(); // (same as your old style)
        System.out.print("Enter Age: ");
        this.age = sc.nextInt();
        // Employee ID validation
        System.out.print("Enter Employee ID (Format: E[C/P]XXXX): ");
        String empID = sc.next();
        while (!empID.matches("^E[C/P][0-9]{4}$")) {
            System.out.print("Invalid format. Re-enter (E[C/P]XXXX): ");
            empID = sc.next();
        }
        this.employeeID = empID;
        // Gender validation
        System.out.print("Enter Gender (MALE/FEMALE): ");
        String g = sc.next().toUpperCase();
        while (!(g.equals("MALE") || g.equals("FEMALE"))) {
            System.out.print("Invalid gender. Enter MALE/FEMALE: ");
            g = sc.next().toUpperCase();
        }
        this.gender = Gender.valueOf(g);
    }
    // Constructor for DB loading
    Employee(String employeeID, String name, Gender gender, int age, int salary, Designation designation) {
        this.employeeID = employeeID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
        this.designation = designation;
    }
    public String getEmployeeID() {
        return employeeID;
    }
    public void display() {
        System.out.println("======================================");
        System.out.println("Name        : " + name);
        System.out.println("Employee ID : " + employeeID);
        System.out.println("Age         : " + age);
        System.out.println("Gender      : " + gender);
        System.out.println("Salary      : " + salary);
        System.out.println("Designation : " + designation);
        System.out.println("======================================");
    }
}

final class Clerk extends Employee {
    Clerk(Scanner sc) {
        super(20000, Designation.CLERK, sc);
    }
    Clerk(String employeeID, String name, Gender gender, int age, int salary) {
        super(employeeID, name, gender, age, salary, Designation.CLERK);
    }
}

final class Programmer extends Employee {
    Programmer(Scanner sc) {
        super(50000, Designation.PROGRAMMER, sc);
    }
    Programmer(String employeeID, String name, Gender gender, int age, int salary) {
        super(employeeID, name, gender, age, salary, Designation.PROGRAMMER);
    }
}

final class Manager extends Employee {
    Manager(Scanner sc) {
        super(100000, Designation.MANAGER, sc);
    }
    Manager(String employeeID, String name, Gender gender, int age, int salary) {
        super(employeeID, name, gender, age, salary, Designation.MANAGER);
    }
}

class Menu {
    public void displayMenu() {
        System.out.println("\n========= EMPLOYEE MENU =========");
        System.out.println("1. Create Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Raise Salary");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
        System.out.println("=================================");
    }
    public void displayEmployeeTypes() {
        System.out.println("\nSelect Employee Type to Create:");
        System.out.println("1. Clerk");
        System.out.println("2. Programmer");
        System.out.println("3. Manager");
    }
    public int getChoice(Scanner sc) {
        System.out.print("Enter choice: ");
        return sc.nextInt();
    }
}

public class Assignment 
{
    private static boolean existsEmployee(String empId) {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            PreparedStatement ps = con.prepareStatement("SELECT 1 FROM employees WHERE employee_id = ?");
            ps.setString(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("DB Error (existsEmployee): " + e.getMessage());
            return false;
        }
    }

    private static void insertEmployee(Employee e) {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            PreparedStatement ps = con.prepareStatement("INSERT INTO employees (employee_id, name, age, gender, salary, designation) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, e.employeeID);
            ps.setString(2, e.name);
            ps.setInt(3, e.age);
            ps.setString(4, e.gender.toString());
            ps.setInt(5, e.salary);
            ps.setString(6, e.designation.toString());
            ps.executeUpdate();
            System.out.println("Employee created successfully (saved to DB).");
        } catch (Exception ex) {
            System.out.println("DB Error (insertEmployee): " + ex.getMessage());
        }
    }

    private static List<Employee> fetchAllEmployees() {
        List<Employee> list = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            PreparedStatement ps = con.prepareStatement("SELECT employee_id, name, age, gender, salary, designation FROM employees ORDER BY employee_id");
            ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                String id = rs.getString("employee_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Gender gender = Gender.valueOf(rs.getString("gender"));
                int salary = rs.getInt("salary");
                Designation des = Designation.valueOf(rs.getString("designation"));
                Employee e;
                if(des == Designation.CLERK) {
                    e = new Clerk(id, name, gender, age, salary);
                } 
                else if (des == Designation.PROGRAMMER) {
                    e = new Programmer(id, name, gender, age, salary);
                } 
                else {
                    e = new Manager(id, name, gender, age, salary);
                }
                list.add(e);
            }
         }catch (Exception e) {
            System.out.println("DB Error (fetchAllEmployees): " + e.getMessage());
        }
        return list;
    }

    private static void raiseSalary(String empId, int amount) {
        try{
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            PreparedStatement ps = con.prepareStatement("UPDATE employees SET salary = salary + ? WHERE employee_id = ?");
            ps.setInt(1, amount);
            ps.setString(2, empId);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                System.out.println("No employee found with ID: " + empId);
            } else {
                System.out.println("Salary updated successfully.");
            }
        } catch (Exception e) {
            System.out.println("DB Error (raiseSalary): " + e.getMessage());
        }
    }

    private static void deleteEmployee(String empId) {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE employee_id = ?");
            ps.setString(1, empId);
            int deleted = ps.executeUpdate();
            if (deleted == 0) {
                System.out.println("No employee found with ID: " + empId);
            } else {
                System.out.println("Employee deleted successfully.");
            }
        } catch (Exception e) {
            System.out.println("DB Error (deleteEmployee): " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        int ch;
        do {
            menu.displayMenu();
            ch = menu.getChoice(sc);
            switch (ch) {
                case 1: {
                    menu.displayEmployeeTypes();
                    int type = menu.getChoice(sc);
                    Employee e = null;
                    if (type == 1) 
                        e = new Clerk(sc);
                    else if (type == 2) 
                        e = new Programmer(sc);
                    else if (type == 3) 
                        e = new Manager(sc);
                    else {
                        System.out.println("Invalid employee type.");
                        break;
                    }
                    if (existsEmployee(e.getEmployeeID())) {
                        System.out.println("Employee ID already exists. Try a unique ID.");
                        break;
                    }
                    insertEmployee(e);
                    break;
                }
                case 2: {
                    List<Employee> all = fetchAllEmployees();
                    if (all.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        System.out.println("\n---- Employee Records ----");
                        for (Employee emp : all) {
                            emp.display();
                        }
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter Employee ID: ");
                    String id = sc.next();
                    System.out.print("Enter raise amount: ");
                    int amt = sc.nextInt();
                    raiseSalary(id, amt);
                    break;
                }
                case 4: {
                    System.out.print("Enter Employee ID to delete: ");
                    String id = sc.next();
                    deleteEmployee(id);
                    break;
                }
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (ch != 5);
        sc.close();
    }
}