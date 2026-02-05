import java.io.*;
import java.sql.*;

/* 
   MAIN APPLICATION
*/
public class Practice {



    static class User {
        int user_id;
        String name;
        String email;
        String password;
        String role;
    }

    static abstract class Sites {
        int site_id;
        int length;
        int breadth;
        int area;
        boolean is_built;

        public abstract String getPropertyType();
        public abstract double calculateMaintenance(double area);
    }

    static class Villa extends Sites {
        public String getPropertyType() { return "VILLA"; }
        public double calculateMaintenance(double area) { return area * 9; }
    }

    static class Apartment extends Sites {
        public String getPropertyType() { return "APARTMENT"; }
        public double calculateMaintenance(double area) { return area * 9; }
    }

    static class IndependentHouse extends Sites {
        public String getPropertyType() { return "INDEPENDENT"; }
        public double calculateMaintenance(double area) { return area * 9; }
    }

    static class OpenSite extends Sites {
        public String getPropertyType() { return "OPEN_SITE"; }
        public double calculateMaintenance(double area) { return area * 6; }
    }

    /* 
       FACTORY
    */

    static class PropertyFactory {
        static Sites createProperty(int choice) {
            switch (choice) {
                case 1: return new Villa();
                case 2: return new Apartment();
                case 3: return new IndependentHouse();
                case 4: return new OpenSite();
                default: throw new IllegalArgumentException("Invalid type");
            }
        }
    }

    /* 
       DAO INTERFACES
     */

    interface UserDAO {
        User login(String email, String password);
    }

    interface AdminUserDAO {
        void addUser(User user);
        void updateUser(User user);
        void deleteUser(int userId);
        void viewAllUsers();
    }

    interface AdminSiteDAO {
        void addSite(Sites site);
        void updateSite(Sites site);
        void deleteSite(int siteId);
        void viewAllSites();
    }

    interface SiteRequestDAO {
        // user
        void requestSite(int siteId, int userId);
        void viewAvailableSites();

        // admin
        void viewPendingRequests();
        void approveRequest(int requestId);
        void rejectRequest(int requestId);
    }


    /* 
       DAO IMPLEMENTATIONS
    */

    static class UserDAOImpl implements UserDAO {
        public User login(String email, String password) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE email=? AND password=?"
                );
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    User u = new User();
                    u.user_id = rs.getInt("user_id");
                    u.name = rs.getString("name");
                    u.email = rs.getString("email");
                    u.role = rs.getString("role");
                    return u;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class AdminUserDAOImpl implements AdminUserDAO {

        public void addUser(User user) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(name,email,password,role) VALUES (?,?,?,?)"
                );
                ps.setString(1, user.name);
                ps.setString(2, user.email);
                ps.setString(3, user.password);
                ps.setString(4, "USER");
                ps.executeUpdate();
                System.out.println("User added successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateUser(User user) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE users SET name=?, email=?, password=? WHERE user_id=? AND role='USER'"
                );
                ps.setString(1, user.name);
                ps.setString(2, user.email);
                ps.setString(3, user.password);
                ps.setInt(4, user.user_id);

                if (ps.executeUpdate() > 0)
                    System.out.println("User updated");
                else
                    System.out.println("Cannot update ADMIN or invalid ID");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteUser(int userId) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM users WHERE user_id=? AND role='USER'"
                );
                ps.setInt(1, userId);

                if (ps.executeUpdate() > 0)
                    System.out.println("User deleted");
                else
                    System.out.println("Cannot delete ADMIN or invalid ID");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void viewAllUsers() {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users");

                System.out.println("ID | NAME | EMAIL | ROLE");
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("user_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("email") + " | " +
                            rs.getString("role")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class AdminSiteDAOImpl implements AdminSiteDAO {

        boolean isValidSize(int l, int b) {
            return (l == 40 && b == 60) ||
                   (l == 30 && b == 50) ||
                   (l == 30 && b == 40);
        }

        public void addSite(Sites site) {
            if (!isValidSize(site.length, site.breadth)) {
                System.out.println("Invalid site size");
                return;
            }
            site.area = site.length * site.breadth;

            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO sites(length,breadth,area,type,is_built) VALUES (?,?,?,?,?)"
                );
                ps.setInt(1, site.length);
                ps.setInt(2, site.breadth);
                ps.setInt(3, site.area);
                ps.setString(4, site.getPropertyType());
                ps.setBoolean(5, site.is_built);
                ps.executeUpdate();
                System.out.println("Site added");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateSite(Sites site) {
            if (!isValidSize(site.length, site.breadth)) {
                System.out.println("Invalid site size");
                return;
            }
            site.area = site.length * site.breadth;

            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE sites SET length=?, breadth=?, area=?, type=?, is_built=? WHERE site_id=?"
                );
                ps.setInt(1, site.length);
                ps.setInt(2, site.breadth);
                ps.setInt(3, site.area);
                ps.setString(4, site.getPropertyType());
                ps.setBoolean(5, site.is_built);
                ps.setInt(6, site.site_id);

                if (ps.executeUpdate() > 0)
                    System.out.println("Site updated");
                else
                    System.out.println("Site not found");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteSite(int siteId) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM sites WHERE site_id=? AND status='VACANT'"
                );
                ps.setInt(1, siteId);

                if (ps.executeUpdate() > 0)
                    System.out.println("Site deleted");
                else
                    System.out.println("Cannot delete occupied site");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void viewAllSites() {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM sites");

                System.out.println("ID | TYPE | SIZE | AREA | STATUS");
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("site_id") + " | " +
                            rs.getString("type") + " | " +
                            rs.getInt("length") + "x" + rs.getInt("breadth") + " | " +
                            rs.getInt("area") + " | " +
                            rs.getString("status")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class SiteRequestDAOImpl implements SiteRequestDAO {

        // USER → see vacant sites
        public void viewAvailableSites() {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT * FROM sites WHERE status='VACANT'"
                );

                System.out.println("---- AVAILABLE SITES ----");
                while (rs.next()) {
                    System.out.println(
                        rs.getInt("site_id") + " | " +
                        rs.getString("type") + " | " +
                        rs.getInt("length") + "x" +
                        rs.getInt("breadth")
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // USER → request site
        public void requestSite(int siteId, int userId) {
            try {
                Connection con = DBHelper.getInstance().getConnection();

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO site_requests(site_id,user_id) VALUES (?,?)"
                );
                ps.setInt(1, siteId);
                ps.setInt(2, userId);

                ps.executeUpdate();
                System.out.println("Site request sent to admin");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ADMIN → view pending
        public void viewPendingRequests() {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT r.request_id, u.name, s.site_id, s.type " +
                    "FROM site_requests r " +
                    "JOIN users u ON r.user_id=u.user_id " +
                    "JOIN sites s ON r.site_id=s.site_id " +
                    "WHERE r.status='PENDING'"
                );

                System.out.println("---- PENDING REQUESTS ----");
                while (rs.next()) {
                    System.out.println(
                        "ReqID:" + rs.getInt("request_id") +
                        " | User:" + rs.getString("name") +
                        " | Site:" + rs.getInt("site_id") +
                        " | " + rs.getString("type")
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ADMIN → approve
        public void approveRequest(int requestId) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                con.setAutoCommit(false);

                // 1. get site + user
                PreparedStatement ps1 = con.prepareStatement(
                    "SELECT site_id, user_id FROM site_requests WHERE request_id=?"
                );
                ps1.setInt(1, requestId);
                ResultSet rs = ps1.executeQuery();
                if (!rs.next()) {
                    System.out.println("Invalid request");
                    return;
                }

                int siteId = rs.getInt("site_id");
                int userId = rs.getInt("user_id");

                // 2. assign owner & occupy
                PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE sites SET status='OCCUPIED', owner_id=? WHERE site_id=?"
                );
                ps2.setInt(1, userId);
                ps2.setInt(2, siteId);
                ps2.executeUpdate();

                // 3. mark request approved
                PreparedStatement ps3 = con.prepareStatement(
                    "UPDATE site_requests SET status='APPROVED' WHERE request_id=?"
                );
                ps3.setInt(1, requestId);
                ps3.executeUpdate();

                con.commit();
                System.out.println("Request approved");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ADMIN → reject
        public void rejectRequest(int requestId) {
            try {
                Connection con = DBHelper.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE site_requests SET status='REJECTED' WHERE request_id=?"
                );
                ps.setInt(1, requestId);
                ps.executeUpdate();
                System.out.println("Request rejected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* 
       MENU
     */

    static class Menu {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserDAO userDao = new UserDAOImpl();
        AdminUserDAO adminUserDao = new AdminUserDAOImpl();
        AdminSiteDAO adminSiteDao = new AdminSiteDAOImpl();

        void start() throws IOException {
            System.out.print("Email: ");
            String email = br.readLine();
            System.out.print("Password: ");
            String password = br.readLine();

            User user = userDao.login(email, password);
            if (user == null) {
                System.out.println("Invalid credentials");
                return;
            }

            if (user.role.equals("ADMIN"))
                adminMenu();
            else
                System.out.println("User menu not implemented yet");
        }

        void adminMenu() throws IOException {
            while (true) {
                SiteRequestDAO reqDao = new SiteRequestDAOImpl();
                System.out.println("\n1.Add User 2.Update User 3.Delete User 4.View Users");
                System.out.println("5.Add Site 6.Update Site 7.Delete Site 8.View Sites");
                System.out.println("9.View Requests 10.Approve 11.Reject");
                int ch = Integer.parseInt(br.readLine());
                if (ch == 9) return;

                switch (ch) {
                    case 1:
                        User u = new User();
                        System.out.print("Name: "); u.name = br.readLine();
                        System.out.print("Email: "); u.email = br.readLine();
                        System.out.print("Password: "); u.password = br.readLine();
                        adminUserDao.addUser(u);
                        break;

                    case 2:
                        User uu = new User();
                        System.out.print("User ID: "); uu.user_id = Integer.parseInt(br.readLine());
                        System.out.print("Name: "); uu.name = br.readLine();
                        System.out.print("Email: "); uu.email = br.readLine();
                        System.out.print("Password: "); uu.password = br.readLine();
                        adminUserDao.updateUser(uu);
                        break;

                    case 3:
                        adminUserDao.deleteUser(Integer.parseInt(br.readLine()));
                        break;

                    case 4:
                        adminUserDao.viewAllUsers();
                        break;

                    case 5:
                        System.out.print("Type(1-4): ");
                        Sites s = PropertyFactory.createProperty(Integer.parseInt(br.readLine()));
                        System.out.print("Length: "); s.length = Integer.parseInt(br.readLine());
                        System.out.print("Breadth: "); s.breadth = Integer.parseInt(br.readLine());
                        s.is_built = !(s instanceof OpenSite);
                        adminSiteDao.addSite(s);
                        break;

                    case 6:
                        System.out.print("Site ID: ");
                        Sites us = PropertyFactory.createProperty(Integer.parseInt(br.readLine()));
                        us.site_id = Integer.parseInt(br.readLine());
                        System.out.print("Length: "); us.length = Integer.parseInt(br.readLine());
                        System.out.print("Breadth: "); us.breadth = Integer.parseInt(br.readLine());
                        us.is_built = !(us instanceof OpenSite);
                        adminSiteDao.updateSite(us);
                        break;

                    case 7:
                        adminSiteDao.deleteSite(Integer.parseInt(br.readLine()));
                        break;

                    case 8:
                        adminSiteDao.viewAllSites();
                        break;

                    case 9:
                        reqDao.viewPendingRequests();
                        break;

                    case 10:
                        System.out.print("Request ID: ");
                        reqDao.approveRequest(Integer.parseInt(br.readLine()));
                        break;

                    case 11:
                        System.out.print("Request ID: ");
                        reqDao.rejectRequest(Integer.parseInt(br.readLine()));
                        break;
                    
                    case 12: break;

                }
            }
        }
    
        void userMenu(User user) throws IOException {
            SiteRequestDAO reqDao = new SiteRequestDAOImpl();

            while (true) {
                System.out.println("\n1.View Sites 2.Request Site 3.Exit");
                int ch = Integer.parseInt(br.readLine());

                if (ch == 3) return;

                switch (ch) {
                    case 1:
                        reqDao.viewAvailableSites();
                        break;

                    case 2:
                        System.out.print("Enter Site ID: ");
                        int sid = Integer.parseInt(br.readLine());
                        reqDao.requestSite(sid, user.user_id);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Menu().start();
    }
}
