import java.sql.*;

public class JdbcDemo2 {
     public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 

            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");

            //Step 2 : Statement creation
            PreparedStatement pstmt = con.prepareStatement("insert into emp values(?,?,?)");//only this compiles..rest executes
            int eid = Integer.parseInt(args[0]);
            String name = args[1];//java JdbcDemo __RAHIM__ 33
            int age = Integer.parseInt(args[2]);//java JdbcDemo RAHIM __33___
            
            
            pstmt.setInt(1, eid);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.execute();
            //best practice to close connection and release resources
            //rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}   

