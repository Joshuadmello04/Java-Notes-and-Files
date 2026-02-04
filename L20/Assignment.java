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
        Integer ownerId;//can be nullable
    }

    static class User{
            int userId;
            String name;
            String role;
            String email;
            String phone;
    }

    static class Maintenance{
        double total;
        double paid;
        double pending;
    }

    interface UserDAO{
        User login(String email,String password);
    }
    interface SiteDAO{
        Site getSite(int siteNumber);
    }
    interface MaintenanceDAO {
        void saveMaintenance(int siteId, double amount);
        void viewPendingDues();
        void payMaintenance(int siteId, double amount);
        Maintenance getMaintenanceBySiteId(int siteId);
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
                        s.ownerId = rs.getObject("owner_id",Integer.class);
                        return s;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
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
                INSERT INTO maintenance (site_id, total_amount, paid_amount)
                VALUES (?, ?, 0)
                ON CONFLICT (site_id) DO NOTHING
                    """);
                ps.setInt(1, siteId);    
                ps.setDouble(2, amount);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    System.out.println("Maintenance already exists for this site.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void viewPendingDues(){
            try {
                Connection con = DBConnection.getInstance().getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("""
                    SELECT s.site_number, m.total_amount, m.paid_amount, (m.total_amount - m.paid_amount) AS pending_amount
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

        public void payMaintenance(int siteId, double amount){
            try {
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement("""
                    UPDATE maintenance
                    SET paid_amount = paid_amount + ?
                    WHERE site_id = ? AND (total_amount - paid_amount) >= ?
                """);
                ps.setDouble(1, amount);
                ps.setInt(2, siteId);
                ps.setDouble(3, amount);

                int rows = ps.executeUpdate();
                if(rows>0){
                    System.out.println("Payment of Rs." + amount + " successful.");
                } else {
                    System.out.println("Payment failed. Check pending amount.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @java.lang.Override
        public Maintenance getMaintenanceBySiteId(int siteId) {
            try{
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement("""
                        SELECT total_amount, paid_amount,
                                           (total_amount - paid_amount) AS pending
                                    FROM maintenance
                                    WHERE site_id = ?
                        """);
                ps.setInt(1, siteId);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    Maintenance m = new Maintenance();
                    m.total = rs.getDouble("total_amount");
                    m.paid = rs.getDouble("paid_amount");
                    m.pending = rs.getDouble("pending");
                    return m;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
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
                    SELECT ar.site_id, ar.new_value,ar.owner_id, s.area
                    FROM approval_requests ar
                    JOIN sites s ON ar.site_id = s.site_id
                    WHERE ar.request_id = ? AND ar.status = 'PENDING'
                """);
                ps1.setInt(1, requestId);
                ResultSet rs = ps1.executeQuery();
                if (!rs.next()) {
                    System.out.println("Invalid or already approved request.");
                    con.rollback();
                    return;
                }
                int siteId = rs.getInt("site_id");
                String newStatus = rs.getString("new_value");
                int ownerId  = rs.getInt("owner_id");
                int area = rs.getInt("area");

                //to Update site status
                PreparedStatement ps2 = con.prepareStatement(
                        "UPDATE sites SET status=? WHERE site_id=?"
                );
                ps2.setString(1, newStatus);
                ps2.setInt(2, siteId);
                ps2.executeUpdate();

                // to assign owner when occupied
                if ("OCCUPIED".equalsIgnoreCase(newStatus)) {
                    PreparedStatement psOwner = con.prepareStatement(
                            "UPDATE sites SET owner_id=? WHERE site_id=?"
                    );
                    psOwner.setInt(1, ownerId);
                    psOwner.setInt(2, siteId);
                    psOwner.executeUpdate();
                }

                // to Generate maintenance ONCE when occupied
                if ("OCCUPIED".equalsIgnoreCase(newStatus)) {
                    double maintenanceAmount = area * 9;
                    PreparedStatement psMaint = con.prepareStatement("""
                        INSERT INTO maintenance (site_id, total_amount, paid_amount)
                        VALUES (?, ?, 0)
                        ON CONFLICT (site_id) DO NOTHING
                    """);
                    psMaint.setInt(1, siteId);
                    psMaint.setDouble(2, maintenanceAmount);
                    psMaint.executeUpdate();
                }

                // to Mark approval as approved
                PreparedStatement ps3 = con.prepareStatement(
                        "UPDATE approval_requests SET status='APPROVED' WHERE request_id=?"
                );
                ps3.setInt(1, requestId);
                ps3.executeUpdate();

                con.commit();
                System.out.println("Request approved successfully.");

            } catch (SQLException e) {
                try {
                    Connection con = DBConnection.getInstance().getConnection();
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    Connection con = DBConnection.getInstance().getConnection();
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
            System.out.println("2. Pay Maintenance");
            System.out.println("3. Request Occupancy Change");
            System.out.println("4. Logout");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.print("Enter site number: ");
                int siteNo = sc.nextInt();

                Site site = siteDAO.getSite(siteNo);
                if (site == null) {
                    System.out.println("Site not found");
                    continue;
                }
                if (site.ownerId == null || site.ownerId != user.userId) {
                    System.out.println("You do not own this site.");
                    continue;
                }

                System.out.println("\n--- Site Details ---");
                System.out.println("Site Number : " + site.siteNumber);
                System.out.println("Area        : " + site.area);
                System.out.println("Status      : " + site.status);

                Maintenance m = maintenanceDAO.getMaintenanceBySiteId(site.siteId);
                if (m == null) {
                    System.out.println("Maintenance not generated yet.");
                } else {
                    System.out.println("\n--- Maintenance Details ---");
                    System.out.println("Total   : Rs." + m.total);
                    System.out.println("Paid    : Rs." + m.paid);
                    System.out.println("Pending : Rs." + m.pending);
                }
            }
            else if (choice ==2) {
                System.out.println("Enter site number: ");
                int siteNo = sc.nextInt();

                Site site = siteDAO.getSite(siteNo);
                if (site == null) {
                    System.out.println("Site not found");
                    continue;
                }
                if (site.ownerId == null || site.ownerId != user.userId) {
                    System.out.println("You can pay maintenance only for your own site.");
                    continue;
                }
                //if unoccupied, disallow payment
                if (!"OCCUPIED".equalsIgnoreCase(site.status)) {
                    System.out.println("Maintenance payment allowed only for occupied sites.");
                    continue;
                }
                Maintenance m = maintenanceDAO.getMaintenanceBySiteId(site.siteId);
                    if (m == null) {
                        System.out.println("Maintenance not generated yet.");
                        continue;
                    }
                System.out.println("Pending amount: Rs." + m.pending);
                System.out.print("Enter amount to pay: ");
                double amount = sc.nextDouble();
                maintenanceDAO.payMaintenance(site.siteId, amount);
            }

                else if (choice == 3) {
                    System.out.print("Enter site number: ");
                    int siteNo = sc.nextInt();
                    Site site = siteDAO.getSite(siteNo);
                    if (site == null) {
                        System.out.println("Site not found");
                        continue;
                    }
                    if (site.ownerId != null) {
                        System.out.println("This site already has an owner.");
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
