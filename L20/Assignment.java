
import java.sql.*;
import java.util.Scanner;



public class Assignment {

    public abstract class Property {
        protected int siteId;
        protected boolean occupied;
        protected int ownerId;
        public abstract String getPropertyType();
        public abstract double calculateMaintenance(double area);
    }

    public class Villa extends Property {
        @Override
        public String getPropertyType() {
            return "VILLA";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }

    public class Apartment extends Property {
        @Override
        public String getPropertyType() {
            return "APARTMENT";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }

    public class IndependentHouse extends Property {
        @Override
        public String getPropertyType() {
            return "INDEPENDENT_HOUSE";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }

    public class OpenSite extends Property {
        @Override
        public String getPropertyType() {
            return "OPEN_SITE";
        }
        public double calculateMaintenance(double area) {
            return area * 6; // Rs/sqft
        }
    }

    static class PropertyFactory{
        static Property createProperty(int choice){
            switch(choice){
                case 1 : return new Assignment().new Villa();
                case 2 : return new Assignment().new Apartment();
                case 3 : return new Assignment().new IndependentHouse();
                case 4 : return new Assignment().new OpenSite();
                default: return null;
            }
        }
    }

    static class Site 
    {
        int siteId;
        int siteNumber;
        int area;
        String status;
    }

    static class SiteManager{
        public Site getSite(int siteNo){
            try {
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM sites WHERE site_number = ?"
                );
                ps.setInt(1, siteNo);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Site s = new Site();
                    s.siteId = rs.getInt("site_id");
                    s.siteNumber = rs.getInt("site_number");
                    s.area = rs.getInt("area");
                    s.status = rs.getString("status");
                    return s;
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class User{
        int userId;
        String name;
        String role;
        String email;
        String phone;
    }

    static class AuthManager{
        public User login(String email,String password)
        {
            try{
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement psmt = con.prepareStatement("SELECT * from users where email=? and password=?");
                psmt.setString(1, email);
                psmt.setString(2, password);
                ResultSet rs = psmt.executeQuery();
                if(rs.next())
                {
                    User u = new User();
                    u.userId = rs.getInt("user_id");
                    u.email = rs.getString("email");
                    u.phone = rs.getString("phone");
                    u.role = rs.getString("role");
                    u.name = rs.getString("name");
                    return u;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    static class MaintenanceManager{
        public void saveMaintenance(int siteId,double amount){
            try {
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement("""
                INSERT INTO maintenance (site_id, total_amount, paid_amount, pending_amount)
                VALUES (?, ?, 0, ?)
                ON CONFLICT (site_id)
                DO UPDATE SET
                    total_amount = EXCLUDED.total_amount,
                    pending_amount = EXCLUDED.pending_amount
                    """);
                ps.setInt(1, siteId);    
                ps.setDouble(2, amount);
                ps.setDouble(3, amount);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void viewPendingDues(){
            try {
                Connection con = DBConnection.getInstance().getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("""
                    SELECT s.site_number, m.total_amount, m.paid_amount, m.pending_amount
                    FROM maintenance m
                    JOIN sites s ON m.site_id = s.site_id
                """);

                System.out.println("\n--- Pending Maintenance ---");
                while (rs.next()) {
                    System.out.println(
                        "Site " + rs.getInt("site_number") +
                        " | Total: ₹" + rs.getDouble("total_amount") +
                        " | Paid: ₹" + rs.getDouble("paid_amount") +
                        " | Pending: ₹" + rs.getDouble("pending_amount")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    static class Menu{
        Scanner sc = new Scanner(System.in);
        void start()
        {
            try {  
                AuthManager auth = new AuthManager();
                System.out.print("Email: ");
                String email = sc.next().trim();
                System.out.print("Password: ");
                String password = sc.next().trim();
                User user = auth.login(email, password);
                if(user == null)
                {
                    System.out.println("Invalid credentials");
                    return;
                }
                System.out.println("Welcome "+ user.name);
                if ("ADMIN".equals(user.role)) {
                    System.out.println("Admin login successful");
                    MaintenanceManager mm = new MaintenanceManager();
                    mm.viewPendingDues();
                } 
                else {
                    System.out.println("User login successful");
                    //userMenu(user);   (later)
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print("Enter site number to view: ");
            int siteNo = sc.nextInt();

            SiteManager sm = new SiteManager();
            Site site = sm.getSite(siteNo);

            if (site != null) {
                System.out.println("Site Number: " + site.siteNumber);
                System.out.println("Area: " + site.area);
                System.out.println("Status: " + site.status);
            } else {
                System.out.println("Site not found");
            }

            System.out.println("\nSelect Property Type:");
            System.out.println("1. Villa");
            System.out.println("2. Apartment");
            System.out.println("3. Independent House");
            System.out.println("4. Open Site");

            int choice = sc.nextInt();
            Property property = PropertyFactory.createProperty(choice);

            if (property == null) {
                System.out.println("Invalid property type");
                return;
            }

            double maintenance = property.calculateMaintenance(site.area);
            System.out.println("Monthly Maintenance Amount: ₹" + maintenance);
            MaintenanceManager mm = new MaintenanceManager();
            mm.saveMaintenance(site.siteId, maintenance);
            System.out.println("Maintenance saved successfully.");
        }
    }

    public static void main(String[] args) {
            Menu menu = new Menu();
            menu.start();
    }
}
