class Person{
    private int age;
    private String name;
    public Person(){

    }
    public Person(int age,String name)
    {
        this.name=name;
        this.age=age;
    }
    public void setName(String name)
    {
        this.name =name;
    }
    public String getName()
    {
        return name;
    }
    public void setAge(int age)
    {
        this.age =age;
    }
    public int getAge()
    {
        return age;
    }
}
public class ImmutableDemo{
    public static void main(String[] args) {
        String s1 = "hi";
        //System.out.println(s1.hashCode());
        s1 = s1.toUpperCase();
        //System.out.println(s1.hashCode());
        Person p1 = new Person();
        p1.setName("Joshua");//same hashcode -> obj is not modified
        System.out.println(p1);
        Person p2 = new Person();
        p2.setName("Badal");
        System.out.println(p2);//diff hashcode from p1..so we can see 2 diff objects
    }
}