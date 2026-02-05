import java.util.*;

//23-01-2026

class Student{
    int age;
    String name;
    public Student(int age, String name){
        this.age = age;
        this.name = name;
    }
    public String toString(){
        System.out.println("Age:" + age);
        System.out.println("Name: "+ name);
        return "";
    }
}

class AgeSorter implements Comparator<Student>{

    @Override
    public int compare(Student s1, Student s2) {
        Integer a1 = s1.age;
        Integer a2 = s2.age;
        return a1.compareTo(a2);
    }

}

class DescAgeSorter implements Comparator<Student>{

    @Override
    public int compare(Student s1, Student s2) {
        Integer a1 = s1.age;
        Integer a2 = s2.age;
        return a2.compareTo(a1);
    }

}

class nameSorter implements Comparator<Student>{

    @Override
    public int compare(Student s1, Student s2) {
        String a1 = s1.name;
        String a2 = s2.name;
        return a1.compareTo(a2);
    }

}

public class AlgorithmClass{
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(222,444,111,333,777,888,666);

        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

        Collections.reverse(list);
        System.out.println(list);

        Collections.swap(list, 2, 5);
        System.out.println(list);

        Collections.shuffle(list);
        System.out.println(list);

        Set<Student> studentList = new TreeSet<Student>(new DescAgeSorter());
        studentList.add(new Student(7,"Daniel"));
        studentList.add(new Student(71,"Pik"));
        studentList.add(new Student(23,"Resh"));
        studentList.add(new Student(53,"hjabd"));
        studentList.add(new Student(31,"mango"));
        studentList.add(new Student(52,"aiuwh"));
        studentList.add(new Student(45,"oijer"));

        for(Student s: studentList){
            System.out.println(s);
        }

    }
}