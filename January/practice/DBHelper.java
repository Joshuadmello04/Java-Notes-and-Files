import java.sql.*;

public class DBHelper{
   private static DBHelper instance;
   private Connection connection;

   private DBHelper()
   {
      String url = "jdbc:postgresql://localhost:5432/test";
      String user = "postgres";
      String password = "josh@reb1";
      try {
           connection = DriverManager.getConnection(url,user,password);
      } catch (SQLException e) {
        e.printStackTrace();
      }
   }

   public static DBHelper getInstance()
   {
      if(instance==null)
      {
            instance = new DBHelper();
      }
      return instance;
   }

   public Connection getConnection()
   {
        return connection;
   }
}