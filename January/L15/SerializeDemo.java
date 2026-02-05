
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class SerializeDemo {
  public static void main(String[] args) {
    try {
        Person p1 = new Person();
        p1.name="suman";
        p1.age=25;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.ser"));
        oos.writeObject(p1);

        System.out.println("Serialized successfully");
    } catch (Exception e) {
        System.out.println("Serialized successfully");
    }
  }   
}
