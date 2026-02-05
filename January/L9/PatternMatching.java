class A 
{
 	public void abc(Object obj)
	{
		if(obj instanceof String str)
		{
		   System.out.println(str); 
		}
		if(obj instanceof Integer i)
		{
		  System.out.println(i);	
		}
	}

    public void switchMatching(Object obj)
    {
       if(obj instanceof String s)
        {
            switch(s)
        {
            case "Hi" -> System.out.println("HI Good Morning");
            case "hello" -> System.out.println("Hello Good Morning");
            default -> System.out.println("Bye Have a good day");     
        }
        }
    }
}
//Record matching below wil work for java 21 onwards
// public void recordMatching(Object obj)
// {
//     switch (obj) {
//         case Person(String name,int age) -> System.out.println("Name "+name+" \t Age "+age);
//             break;
//         default: System.out.println("Sorry invalid");
//             break;
//     }
// }

public class PatternMatching
{
	public static void main(String args[])
	{
		//Integer i =10;
		String s = "Hi";

		A a1 = new A();
        a1.switchMatching(s);
		//a1.abc(i);
		//a1.abc(s);
	}
}