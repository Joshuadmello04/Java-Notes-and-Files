import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TransactionDemo {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        try {
            //Class.forName("org.postgresql.Driver"); was compulsory till v8 of java 

            //Step 1 : load Driver
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "josh@reb1");
            con.setAutoCommit(false);
            //Step 2 : Statement creation
            PreparedStatement pstmt = con.prepareStatement("insert into emp values(?,?)");//only this compiles..rest executes
            
            for(int i=1;i<=6;i++)
            {
                System.out.println("Enter Name:");
                String name = br.readLine();
                System.out.println("Enter Age:");
                int age = Integer.parseInt(br.readLine());

                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.execute();

                if(i==5)
                {
                    con.rollback();
                }//this means all from 5 and before are removed leaving only whats left after 5 ie 6 and later
            }        
            con.commit();//compulsory to do this
            //best practice to close connection and release resources
            //rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
