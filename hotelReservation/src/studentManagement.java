import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// Student Management System 
public class studentManagement {
    private static String url= "jdbc:mysql://localhost:3306/hotel_reservation";
    private static String username = "root";
    private static String password = "admin123";
    public static void main(String[] args) throws InterruptedException {
     
        
        try {
            Class.forName("com.mysql.jdbc.cj.Driver");

        } catch (ClassNotFoundException e) {
           System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);
            Statement stmt = con.createStatement();

            

            while (true) {
                System.out.println("Student Management System");
                System.out.println();
                System.out.println("1) Add a new student record.");
                System.out.println("2)Display all student records.");
                System.out.println("3)Update student details (marks, course).");
                System.out.println("4)Delete student records.");
                System.out.println("5)Search for students grade based on name or roll number.");
                System.out.println("6)Exit ");
                System.out.println("--------------------------------------------------------");

                System.out.println("Choose one from the following:-");

                
                
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(con, stmt, sc);
                        break;

                    case 2:
                        studentRecord(con, stmt);
                        break;   
                        
                    case 3:
                        updateStuDetail(con, stmt, sc);
                        break;

                    case 4:
                        deleteStuRecord(con, stmt, sc);
                        break;

                    case 5:
                        getGrade(con, stmt, sc);
                        break;

                    case 6:
                        exit();
                        sc.close();
                        return;

                
                    default:
                       System.out.println("Enter the valid input");
                        break;
                }

        
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public static void addStudent(Connection con, Statement stmt,Scanner sc){

        System.out.println("Enter the student name");
        String name = sc.next();
        System.out.println("Enter the avg. grade of student");
        String grade = sc.next();

        try {
            String query = "insert into student_db(name, grade) values('"+name+"', '"+grade+"')";
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected>0) {
                System.out.println("Student record added successfully");
            }else{
                System.out.println("Student record added unsuccessfully");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void studentRecord(Connection con, Statement stmt){

        try {
            String query = "select * from student_db";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String grade = rs.getString("grade");

                System.out.println("Name: "+name+" Grade: "+grade);
            }
                
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public static void updateStuDetail(Connection con, Statement stmt, Scanner sc){
// change the grades with id not with the name
        try {
            System.out.println("Enter the student id");
            int id = sc.nextInt();
            System.out.println("Enter the new grade of student");
            String grade = sc.next();

            String query = "update student_db set grade = '"+grade+"' where id = '"+id+"'";
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected>0) {
                System.out.println("Student record updated successfully");
            }else{
                System.out.println("Student record updated unsuccessfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStuRecord(Connection con, Statement stmt, Scanner sc) {
        System.out.println("Enter the student id to delete");
        int id = sc.nextInt();

        try {
            String query = "delete from student_db where id = '"+id+"'";
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Student record deleted successfully");
            } else {
                System.out.println("Student record deletion unsuccessful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getGrade(Connection con, Statement stmt, Scanner sc) {
        System.out.println("Enter the student name");
        String name = sc.next();
        System.out.println("Enter the student roll number");
        int roll = sc.nextInt();

        try {
            String query = "select grade from student_db where name = '"+name+"' or id = '"+roll+"'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String grade = rs.getString("grade");
                System.out.println("Grade: "+grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }
    public static void exit() throws InterruptedException{
        System.out.print("Exiting");
        int i = 5;
        while (i>=0) {
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
    }
}
