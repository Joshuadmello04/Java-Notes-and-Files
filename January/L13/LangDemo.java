public class LangDemo {
    public static void main(String[] args) throws Exception {
        String s1 = "hi";
        System.out.println(s1+" " + s1.hashCode());
        s1 = "hello";//new obj
        System.out.println(s1+" " + s1.hashCode());
        s1 = s1.toUpperCase();//new obj
        System.out.println(s1+" " + s1.hashCode());
        s1 = s1.concat("thanks");//creating a new object
        System.out.println(s1+" " + s1.hashCode());
        StringBuffer sb1 = new StringBuffer("hi");
        System.out.println(sb1);
        sb1.append("hello");//append only in string buffer
        System.out.println(sb1);

        String s2 = "meow";
        System.out.println(s2+" " + s2.hashCode());
        String s3 = "hello";//same obj
        System.out.println(s3+" " + s3.hashCode());
        String s4 = "hi";//new obj
        System.out.println(s4+" " + s4.hashCode());
        String s5 = "turn";//new obj
        System.out.println(s5+" " + s5.hashCode());
        String s6 = "meow";//same obj
        System.out.println(s6+" " + s6.hashCode());

        StringBuilder sb2 = new StringBuilder("Hi");
        System.out.println(sb2+ " "+sb2.hashCode());
        sb2.append("Thanks");
        System.out.println(sb2 +" " + sb2.hashCode());


        String s7 = "new";
        String s8 = "new";
        System.out.println(s7 + " "+s7.hashCode());
        System.out.println(s8 + " "+s8.hashCode());

        s7 = s7.concat("throw");
        System.out.println(s7 + " "+s7.hashCode());
        System.out.println(s8 + " "+s8.hashCode());

        Runtime rt =  Runtime.getRuntime();
        rt.exec("notepad ReflectionAPIDemo.java");
    }
}
