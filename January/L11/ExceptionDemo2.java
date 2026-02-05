public class ExceptionDemo2 {
    public static void main(String[] args) {
        try {
            for(int i=1;i<=20;i++){
                System.out.println(i);
                int res =(i)/(i-5);
            }
        } 
        //Exceptions have a heirarchy:
        //RuntimeException handles Arithmetic Exception....
        //therefore whenever we put multiple catch-blocks it should be in heirarchical error:

        catch (NullPointerException e) {
            System.out.println("Null pointer Exception handled");
        }
        catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception  handled");
        }
        catch (RuntimeException e) {
            System.out.println("Runtime Exception handled");
        }
        catch (Exception e) {
            // try{

            // }catch(Exception b){

            // }
            // finally{

            // }
            System.out.println("DEfault Exception handled");
        }
        //throwable can handle any error
        // catch(Throwable e){
        //     System.out.println("Super Exception handled");
        // }

        //Multi Exception Handler
        // catch(ArithmeticException | RuntimeException e){
        //     System.out.println();...same logic
        // }
        finally{
            // try {
                
            // } catch (Exception e) {
            // }
            System.out.println("Thank You");
        }
    }
}
