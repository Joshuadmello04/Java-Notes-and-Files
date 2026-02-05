import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DeserializeDemo {
    public static void main(String[] args) {
        try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"));
        Person p1 = (Person) ois.readObject();

        System.out.println("Deserialized successfully");
        System.out.println("Your name is : "+ p1.name);
        System.out.println("And u'r "+p1.age+" years old");
    } catch (Exception e) {
        System.out.println("Serialized successfully");
    }
    }
}
