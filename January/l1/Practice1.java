import java.util.Scanner;

import L8.Employee;


//class KeyInput{ // its better to have a class for input stream 
//	public static Scanner sc = new Scanner(System.in);
//}
class Employee{ //business class shouldnt have main fn
	private String name;
	private int age;
	private int salary;
	private String designation;
	
	public Employee(){
		    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter Employee name: ");
                    name = sc.next();

                    System.out.print("Enter Employee age: ");
                    age = sc.nextInt();

                    System.out.print("Enter Employee salary: ");
                    salary = sc.nextInt();

                    System.out.print("Enter Employee designation: ");
                    designation = sc.next();
	}
	
	// Scanner sc = new Scanner(); -> u shouldnt have this here employee doesnt HAS Scanner object so you cant create it here ... creation inside method is good but not inside class

	public void raiseSalary(){ // we are not passing int amt 
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter amount to raise salary by : ");
		//int amt = KeyInput.sc.nextInt(); // u shouldnt be including the raise or increment amt in the class since its not a attribute of Employee 
		int amt = sc.nextInt();
		salary += amt;
		System.out.println("Successfully incremented the salary of " + name + " by " + amt);
	}
	
	public void display(){
        	System.out.println("Employee name: " + name);
        	System.out.println("Employee age: " + age);
        	System.out.println("Employee salary: " + salary);
        	System.out.println("Employee designation: " + designation);
	}
}


public class Practice1{
	public static void main(String args[]){
		int ch=0;
		Employee emp = null;
		// Employee emp = null; u should only create obj whenever required

		Scanner sc = new Scanner(System.in);

		do{
			System.out.println("----------------------------");
			System.out.println("1. Create ");
			System.out.println("2. Display ");
			System.out.println("3. Raise Salary ");
			System.out.println("4. Exit ");
			System.out.println("----------------------------");
			System.out.println("Enter Choice ");
			ch = sc.nextInt();

			switch(ch){
				case 1:
					emp = new Employee();
					break;

				case 2:
					if(emp != null)
						emp.display();
					else
						System.out.println("Please create employee first");
					break;

				case 3:
					if(emp != null)
						emp.raiseSalary();
					else
						System.out.println("Please create employee first");
					break;

				case 4:
					System.out.println("Exiting...");
					break;

				default:
					System.out.println("Invalid choice");
			}

		}while(ch!=4);
	}
}