
import java.util.Scanner;

public class ArrayDemo2 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Emp employees[] = new Emp[4];//array of Objects...this creates 0 objects..it doesnt make an employee..coz we made an Array..we didnt call a contructor
    //it creates an array that can hold upto 4 Employee objects in it
    ///so create an object
    for(int i=0;i<employees.length;i++)
    {
        employees[i] = new Emp();
    }

    //this below wont work coz e is local variable
    // for(Emp e : employees)
    // {
    //     e = new Emp();
    // }
    for(int i=0;i<employees.length;i++)
    {
        System.out.println("Enter emp "+(i+1)+ " name");
        employees[i].name = sc.next();
        System.out.println("Employee "+(i+1)+ " "+employees[i].name);
    }
  }   
}
class Emp{
    String name;
}