import java.io.*;

class Abc{
    public void a() throws ArithmeticException,IOException,UserException {

            int a=10;
            for(int i=1;i<=20;i++)
            {
                System.out.println(i);
                int res = a/(a-1);
                if(i==15){
                    throw new NullPointerException();
                }
                if(i==12)
                    throw new IOException();
                if(i==50)
                    throw new NullPointerException("Dummy Reason");
                if(i==70)
                    System.exit(0);
                if(i==30)
                    throw new UserException("User defined reason");
            }
        } 

    public void b() throws ArithmeticException,IOException,UserException{
        a();
    }

    public void c() throws ArithmeticException,IOException,UserException{
        b();
    }
}


public class ExceptionDemo {
    public static void main(String[] args) {
        try{
            Abc a1 = new Abc();
        a1.c();
        System.out.println("Program continues");
        }
        catch(IOException e){
            System.out.println("IOException Handled");
            System.out.println("Reason "+e.getMessage());
        }
        catch(NullPointerException e){
            System.out.println("Custom Exception handler");
            System.out.println("reason "+e.getMessage());
        }
        catch(UserException e){
            System.out.println("Reason "+e.getMessage());
            e.display();
        }
        catch(Exception e){
            System.out.println("Default exception handler");
        }
        finally{
            System.out.println("Thanks");
        }
    }
}

class UserException extends Exception{
    public UserException()
    {
        super();
    }
    public UserException(String msg){
        super(msg);
    }
    public void display(){
        System.out.println();
    }
}