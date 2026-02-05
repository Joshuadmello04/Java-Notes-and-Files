abstract class  Animal{
    public void eat(){
        System.out.println("Munch");
    }
    public abstract void talk();

}
abstract class Mammal extends Animal{

}
class Dog extends Mammal{
    public void talk()
    {
        System.out.println("Dog Barks");
    }
}

class Cat extends Mammal{
    public void talk()
    {
        System.out.println("Cat Meows");
    }
}

public class AbstractDemo {
  public static void main(String[] args) {
    Dog dog = new Dog();
    dog.talk();
    Cat cat = new Cat();
    cat.talk();
  }
}
