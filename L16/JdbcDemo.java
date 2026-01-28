
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 

            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:<port>/<db name>", "<username>", "<passwd>");

            //Step 2 : Statement creation
            Statement stmt = con.createStatement();

            //Step 3: execute satetement
            // ResultSet rs = stmt.executeQuery("select * from emp");//this will retrieve records
            // while(rs.next()){
            //     System.out.println("Name : "+rs.getString(1));
            //     System.out.println("Age : "+rs.getString(2));
            // }

            stmt.executeUpdate("insert into EMP values(105,'Joja',33);");

            stmt.executeUpdate("update EMP set AGE = AGE+3");

            ResultSet rs = stmt.executeQuery("Select * from EMP");
            while(rs.next()){
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getString(2));
            }
            //best practice to close connection and release resources
            //rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

