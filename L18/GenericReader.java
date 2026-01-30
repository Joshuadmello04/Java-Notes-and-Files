import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class GenericReader {
    public static void main(String[] args) {
        try {
            System.out.println("Enter table name");
            String table = new BufferedReader(new InputStreamReader(System.in)).readLine();
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from "+table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            while(rs.next()){
                for(int i=1;i<=columns;i++)
                {
                    System.out.println(rsmd.getColumnName(i) + " : "+rs.getString(i));
                }  
                System.out.println();  
            }            
            //best practice to close connection and release resources
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
}

