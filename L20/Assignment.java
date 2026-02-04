
import java.sql.*;
import java.util.Scanner;



public class Assignment {
    
    public static abstract class Property {
        protected int siteId;
        protected boolean occupied;
        protected int ownerId;
        public abstract String getPropertyType();
        public abstract double calculateMaintenance(double area);
    }
    
    public static class Villa extends Property {
        @Override
        public String getPropertyType() {
            return "VILLA";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }
    
    public static class Apartment extends Property {
        @Override
        public String getPropertyType() {
            return "APARTMENT";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }
    
    public static class IndependentHouse extends Property {
        @Override
        public String getPropertyType() {
            return "INDEPENDENT_HOUSE";
        }
        public double calculateMaintenance(double area) {
            return area * 9; // Rs/sqft
        }
    }
    
    public static class OpenSite extends Property {
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
                case 1 : return new Villa();
                case 2 : return new Apartment();
                case 3 : return new IndependentHouse();
                case 4 : return new OpenSite();
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
    
    interface UserDAO{
        User login(String email,String password);
    }
    interface SiteDAO{
        Site getSite(int siteNumber);
    }
    interface MaintenanceDAO{
        void saveMaintenance(int siteId,double amount);
        void viewPendingDues();
    }
    
    interface ApprovalDAO{
        void requestOccupancyChange(int siteId,int ownerId,String oldStatus,String newStatus);
        void viewPendingRequests();
        void approveRequest(int requestId);
    }
    static class SiteDAOImpl implements SiteDAO{

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
        

    static class UserDAOImpl implements  UserDAO{

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

    static class MaintenanceDAOImpl implements MaintenanceDAO{

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

    static class ApprovalDAOImpl implements ApprovalDAO {
        public void requestOccupancyChange(int siteId, int ownerId, String oldStatus, String newStatus) {
            try {
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement("""
                    INSERT INTO approval_requests
                    (site_id, owner_id, request_type, old_value, new_value)
                    VALUES (?, ?, 'OCCUPANCY', ?, ?)
                """);

                ps.setInt(1, siteId);
                ps.setInt(2, ownerId);
                ps.setString(3, oldStatus);
                ps.setString(4, newStatus);

                ps.executeUpdate();
                System.out.println("Request submitted for admin approval.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void viewPendingRequests() {
            try {
                Connection con = DBConnection.getInstance().getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("""
                    SELECT request_id, site_id, old_value, new_value
                    FROM approval_requests
                    WHERE status = 'PENDING'
                """);

                System.out.println("\n--- Pending Requests ---");
                while (rs.next()) {
                    System.out.println(
                        "Request ID: " + rs.getInt("request_id") +
                        " | Site ID: " + rs.getInt("site_id") +
                        " | " + rs.getString("old_value") +
                        " → " + rs.getString("new_value")
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void approveRequest(int requestId) {
            try {
                Connection con = DBConnection.getInstance().getConnection();
                con.setAutoCommit(false);

                PreparedStatement ps1 = con.prepareStatement("""
                    SELECT site_id, new_value
                    FROM approval_requests
                    WHERE request_id = ?
                """);
                ps1.setInt(1, requestId);
                ResultSet rs = ps1.executeQuery();

                if (rs.next()) {
                    int siteId = rs.getInt("site_id");
                    String newStatus = rs.getString("new_value");

                    PreparedStatement ps2 = con.prepareStatement(
                        "UPDATE sites SET status=? WHERE site_id=?"
                    );
                    ps2.setString(1, newStatus);
                    ps2.setInt(2, siteId);
                    ps2.executeUpdate();

                    PreparedStatement ps3 = con.prepareStatement(
                        "UPDATE approval_requests SET status='APPROVED' WHERE request_id=?"
                    );
                    ps3.setInt(1, requestId);
                    ps3.executeUpdate();

                    con.commit();
                    System.out.println("Request approved.");
                }

                con.setAutoCommit(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class Menu {
    Scanner sc = new Scanner(System.in);
    UserDAO userDAO = new UserDAOImpl();
    SiteDAO siteDAO = new SiteDAOImpl();
    MaintenanceDAO maintenanceDAO = new MaintenanceDAOImpl();
    ApprovalDAO approvalDAO = new ApprovalDAOImpl();
    void start() {
        try {
            System.out.print("Email: ");
            String email = sc.next().trim();
            System.out.print("Password: ");
            String password = sc.next().trim();

            User user = userDAO.login(email, password);
            if (user == null) {
                System.out.println("Invalid credentials");
                return;
            }

            System.out.println("Welcome " + user.name);

            if ("ADMIN".equals(user.role)) {
                adminMenu(user);
            } else {
                userMenu(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= ADMIN MENU ================= */

    void adminMenu(User admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Pending Maintenance");
            System.out.println("2. View Approval Requests");
            System.out.println("3. Approve Request");
            System.out.println("4. Logout");
            int choice = sc.nextInt();

            if(choice == 1) {
                maintenanceDAO.viewPendingDues();
            }
            else if (choice == 2) {
                approvalDAO.viewPendingRequests();
            }
            else if (choice == 3) {
                System.out.print("Enter request ID: ");
                int reqId = sc.nextInt();
                approvalDAO.approveRequest(reqId);
            }
            else {
                System.out.println("Logged out (Admin)");
                break;
            }
        }
    }

    /* ================= USER MENU ================= */

    void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Site & Maintenance");
            System.out.println("2. Request Occupancy Change");
            System.out.println("3. Logout");
            int choice = sc.nextInt();
            if(choice == 1) {
                System.out.print("Enter site number: ");
                int siteNo = sc.nextInt();

                Site site = siteDAO.getSite(siteNo);
                if (site == null) {
                    System.out.println("Site not found");
                    continue;
                }

                System.out.println("Site Number: " + site.siteNumber);
                System.out.println("Area: " + site.area);
                System.out.println("Status: " + site.status);

                System.out.println("\nSelect Property Type:");
                System.out.println("1. Villa");
                System.out.println("2. Apartment");
                System.out.println("3. Independent House");
                System.out.println("4. Open Site");

                int p = sc.nextInt();
                Property property = PropertyFactory.createProperty(p);
                if (property == null) {
                    System.out.println("Invalid property type");
                    continue;
                }

                double maintenance = property.calculateMaintenance(site.area);
                System.out.println("Monthly Maintenance Amount: Rs." + maintenance);

                maintenanceDAO.saveMaintenance(site.siteId, maintenance);
                System.out.println("Maintenance saved.");
            }
            else if (choice == 2) {
                System.out.print("Enter site number: ");
                int siteNo = sc.nextInt();

                Site site = siteDAO.getSite(siteNo);
                if (site == null) {
                    System.out.println("Site not found");
                    continue;
                }

                approvalDAO.requestOccupancyChange(
                    site.siteId,
                    user.userId,
                    site.status,
                    "OCCUPIED"
                );
            }
            else {
                System.out.println("Logged out (User)");
                break;
            }
        }
    }
}


    public static void main(String[] args) {
            Menu menu = new Menu();
            menu.start();
    }
}
