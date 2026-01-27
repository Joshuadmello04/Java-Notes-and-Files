
import java.util.*;

//23-01-2026

class Student{
    int univNo;
    String name;

    public Student(int univNo, String name){
        this.univNo = univNo;
        this.name = name;
    }

    public String toString(){
        System.err.println("University NO" + univNo);
        System.out.println("Name:" + name);
        return "";
    }

    public boolean equals(Object obj){
        Student s1 = (Student) obj;
        if(this.univNo == s1.univNo){
            return true;
        } else{
            return false;
        }
    }

    public int hashCode(){ //put in same bucket then check using equals
        return univNo;
    }
}



public class CollectionDemo2{
    public static void main(String[] args) {
        Set<Student> studentList = new HashSet<Student>();

        studentList.add(new Student(7,"Daniel"));
        studentList.add(new Student(71,"Pik"));
        studentList.add(new Student(23,"Resh"));
        studentList.add(new Student(53,"hjabd"));
        studentList.add(new Student(71,"mango"));
        studentList.add(new Student(52,"aiuwh"));
        studentList.add(new Student(45,"oijer"));

        for(Student s: studentList){
            System.out.println(s);
        }
    }
}