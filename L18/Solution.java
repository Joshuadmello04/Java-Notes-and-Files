
// import java.sql.*;
// import java.util.List;

// public class Solution {
//     class Employee{

//     }
//     class Clerk extends Employee{

//     }
//     class Programmer extends Employee{

//     }
//     class Manager extends Employee{

//     }
//     interface EmpDAO{
//        public boolean save(Employee e);
//        public boolean delete(int id);
//        public boolean update(Employee e);
//        public List<Employee> retrieve();   
//     }
// abstract class StorageHelper{
//     if choice = 1 then DB DB operation
//     else ... file operations...
//     then tom we can add csv choices too
// }
//     class EmployeeDBOperations implements EmpDAO{
//         public boolean save(Employee e){
//             try {
//                 PreparedStatement pstmt = con.prepareStatement("insert into employee values(?,?,?,?,?)");
//                 pstmt.setInt(1,e.getID());
//                     pstmt.execute()
//                     return true;
//             } catch (Exception ex) {
//                 ex.printStackTrace();
//             }
//         }
//         //do the same for other methods here
//     }
//     class EmployeeFileOperations implements EmployeeDAO{
//         public
//     }
//         class EMployeeCSVOperations implements EmployeeDAO{
//             .....
//         }
// class DBConnection
//         {
//             private Connection con = null;
//             private DBConnection(){}
//             public static Connection getConnection(String dbName)
//             {
//                 if(con==null){
//                     Class.forName(dbName);
//                     Connection con = DriverManager.getConnection("","","");
//                     return con;
//                 }
//             }
//         }
//     public static void main(String[] args) {
//         sout enter preferred storage file,db,csv,JSon
//         string storage = br.readLine();//buffered reader
//         Employee
//         case 1; .... dao.save();
//         case 2 : dao.display();
//         case 3 : dao.delete(with id);
//         case 4 : List<>... dao.retrieve();
//         display(list);
//     }
// }

// This follow open closed principle and Single Responsibility Principle