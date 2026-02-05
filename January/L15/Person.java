import java.io.Serializable;

public class Person implements Serializable{
    String name;
    int age;
}

//to allow deserialize we make changes..knowing structure of class is not enough..it has to be exactly same to deserialize