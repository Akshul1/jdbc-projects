import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// Student Management System 
// update this with prepared statement
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
            // Statement stmt = con.createStatement();

            

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
                        addStudent(con, sc);
                        break;

                    case 2:
                        studentRecord(con);
                        break;   
                        
                    case 3:
                        updateStuDetail(con, sc);
                        break;

                    case 4:
                        deleteStuRecord(con, sc);
                        break;

                    case 5:
                        getGrade(con, sc);
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
    public static void addStudent(Connection con,Scanner sc){

        System.out.println("Enter the student name");
        String name = sc.next();
        System.out.println("Enter the avg. grade of student");
        String grade = sc.next();

        try {
            String query = "insert into student_db(name, grade) values(?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, grade);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected>0) {
                System.out.println("Student record added successfully");
            }else{
                System.out.println("Student record added unsuccessfully");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void studentRecord(Connection con){

        try {
            String query = "select * from student_db";
            PreparedStatement preparedstatement = con.prepareStatement(query);
            ResultSet rs = preparedstatement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String grade = rs.getString("grade");

                System.out.println("====================================");
                System.out.println("id"+id+"Name: "+name+" Grade: "+grade);
                System.out.println("====================================");
            }
                
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public static void updateStuDetail(Connection con, Scanner sc){
// change the grades with id not with the name
        try {
            System.out.println("Enter the student id");
            int id = sc.nextInt();
            System.out.println("Enter the new grade of student");
            String grade = sc.next();

            String query = "update student_db set grade = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, grade);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected>0) {
                System.out.println("Student record updated successfully");
            }else{
                System.out.println("Student record updated unsuccessfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStuRecord(Connection con, Scanner sc) {
        System.out.println("Enter the student id to delete");
        int id = sc.nextInt();

        try {
            String query = "delete from student_db where id = ?";
            PreparedStatement preparedstatement = con.prepareStatement(query);
            preparedstatement.setInt(1, id);
            int rowsAffected = preparedstatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student record deleted successfully");
            } else {
                System.out.println("Student record deletion unsuccessful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getGrade(Connection con,  Scanner sc) {
        System.out.println("Enter the student roll number");
        int roll = sc.nextInt();

        try {
            String query = "select grade from student_db where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, roll);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                String grade = rs.getString("grade");
                System.out.println("Grade: "+grade);
            }else{
            System.out.println("Student not found");
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
