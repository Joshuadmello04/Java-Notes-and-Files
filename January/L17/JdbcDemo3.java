import java.sql.*;

public class JdbcDemo3 {
     public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 

            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");

            //Step 2 : Batch Statement creation
            Statement bstmt = con.createStatement();//only this compiles..rest executes
            
            bstmt.addBatch("insert into emp values (91,'Deepti',39)");
            bstmt.addBatch("insert into emp values (92,'Deeksha',29)");
            bstmt.addBatch("insert into emp values (93,'Daniel',23)");
            bstmt.addBatch("insert into emp values (94,'Deep',19)");
            
            System.out.println("None of these records entered yet");
            Thread.sleep(10000);
            bstmt.executeBatch();
            System.out.println("You can check now");
            //best practice to close connection and release resources
            //rs.close();
            bstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}   

