import java.sql.*;

public class JdbcDemo4 {
     public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 

            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");

            //Step 2 : Batch Statement creation
            CallableStatement cstmt = con.prepareCall("CALL abc()");//only this compiles..rest executes
            cstmt.execute();
            //best practice to close connection and release resources
            //rs.close();
            cstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}   

