
record Person(String name,int age){

}
public class RecordDemo {
  public static void main(String[] args) {
    Person p1 = new Person("Ramesh", 21);
    System.out.println(p1);
    System.out.println(p1.age());
    System.out.println(p1.name());
  }   
}
