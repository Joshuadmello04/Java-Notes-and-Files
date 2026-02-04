import java.sql.*;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        String url = "jdbc:postgresql://localhost:5432/Assignment";
        String user = "postgres";
        String password = "josh@reb1";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } 
        catch(SQLException e) {
             e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}