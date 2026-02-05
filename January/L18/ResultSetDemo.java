import java.sql.*;

public class ResultSetDemo {
    public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 
            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            //Step 2 : Statement creation
            //Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //Step 3: execute satetement
            // ResultSet rs = stmt.executeQuery("select * from emp");//this will retrieve records
            // while(rs.next()){
            //     System.out.println("Name : "+rs.getString(1));
            //     System.out.println("Age : "+rs.getString(2));
            // }

            ResultSet rs = stmt.executeQuery("Select * from EMP");
            while(rs.next()){
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getInt(2));
            }
            System.out.println("<---------->");
            while(rs.previous()){
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getInt(2));
            }
            System.out.println("<---------->");
            rs.last();
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getInt(2));
            
            System.out.println("<---------->");
            rs.first();
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getInt(2));

            rs.absolute(-5);
                System.out.println("Name : "+rs.getString(1));
                System.out.println("Age : "+rs.getInt(2));
                
            //best practice to close connection and release resources
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
}
