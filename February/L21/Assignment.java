//Requirement create 3 threads of the above class with name different names and run them simultaneously
//no modification possible to A class..no extends/implements nth
class A{
    public void abc(String name)
    {
        for(int i=1;i<=50;i++)
        {
            System.out.println(name+" : "+i+" mtrs");
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
public class Assignment {
    public static void main(String[] args) {
        //we need to call and not modify so we need lambda function and anonymous classes...no suspend resume needed
        A obj = new A();

        Thread t1 = new Thread(()->{
            obj.abc("Joshua");
        });

        Thread t2 = new Thread(()->{
            obj.abc("Badal");
        });

        Thread t3 = new Thread(()->{
            obj.abc("Aryan");
        });

        t1.start();
        t2.start();
        t3.start();

        //if we were to use an anonymous method also its doable
        Thread t4 = new Thread(){
            public void run(){
                A a1 = new A();
                a1.abc("Shardul");
            }
        };
        String[] names = {"Joshua","Anish","Badal","Shardul"};
        for(String name:names)
        {
            Thread t = new Thread(()-> t.start());
        }

        //best way
        //thread object ... inside it we put lambda function to start
        new Thread(()->new A().abc("Anish")).start();
    }
}
