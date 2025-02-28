
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class hotelreservation {
    private static String url = "jdbc:mysql://localhost:3306/hotel_reservation";
    private static String username = "root";
    private static String password = "admin123";
    public static void main(String[] args) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
           System.out.println(e.getMessage());
        }


        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt= con.createStatement();

           while (true) {
            
            System.out.println("HOTEL RESERVATION SYSTEM");
            System.out.println();
            Scanner sc = new Scanner(System.in);
                System.out.println("1) Reserve a room ");
                System.out.println("2) View reservations");
                System.out.println("3) Get a room number");
                System.out.println("4) Update reservation");
                System.out.println("5) Delete reservation");
                System.out.println("6) Exit");
                System.out.println("Choose one option ");

            int choice = sc.nextInt();
            sc.nextLine();
                switch (choice) {
                    case 1:
                        reserveRoom(con, stmt, sc);
                        break;

                    case 2:
                        viewReservations(con, stmt);
                        break;

                    case 3:
                        getRoomNo(sc, con, stmt);
                        break;

                    case 4:
                        updateReservation(con, stmt, sc);
                        break;

                    case 5:
                        deleteReservation(con, stmt, sc);
                        break;

                    case 6:
                        exit();
                        return;
                
                    default:
                        break;
                }
                
           }

            
        } catch (Exception e) {
            
        }
    }

    public static void reserveRoom(Connection con, Statement stmt,Scanner sc){
     // name, room number, contact
        
        System.out.println("Enter the guest name");
        String guestName = sc.next();
        sc.nextLine();
        System.out.println("Enter the room number");
        int roomnum = sc.nextInt();
        System.out.println("Enter the contact number");
        String contact = sc.next();

        String sql = "INSERT INTO reservation (name, room_num, contact_num)"+
        "VALUES ('"+guestName+"', '"+ roomnum +"', '" +contact+"')" ;  
        
        try {
            int rowsAffected = stmt.executeUpdate(sql);

        if (rowsAffected>0) {
            System.out.println("Regestration successfull");
        } else{
            System.out.println("Regestration unsuccessfull");
        }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        

    }

    public static void viewReservations(Connection con, Statement stmt) throws SQLException{
 
        String query = "SELECT * FROM reservation";
        ResultSet resultSet = stmt.executeQuery(query);

        System.out.println();
        System.out.println("+---------+-----------------+-------------+------------+--------+");
        System.out.println("|ID       |NAME             | ROOM NUMBER | CONTACT    | DATE   |");
        System.out.println("+---------+-----------------+-------------+------------+--------+");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
        String name= resultSet.getString("name");
        int room_num = resultSet.getInt("room_num");
        String contact= resultSet.getString("contact_num");
        Timestamp resertime= resultSet.getTimestamp("reser_date");


        System.out.println();

          // Format and display the reservation data in a table-like format
          System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
          id, name, room_num, contact, resertime);
        }
        

    }

    public static void getRoomNo(Scanner sc, Connection con, Statement stmt){

        System.out.println("Enter the reservation id");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the guest name");
        String name= sc.nextLine();

        String sql= "SELECT room_num FROM reservation WHERE id = " + id + " AND name = '" + name + "'";


        try {
            ResultSet resultSet= stmt.executeQuery(sql);
            if (resultSet.next()) {
                int room_num = resultSet.getInt("room_num");
                System.out.println("The room number for the given id: "+ id+"and name: "+name +"is: "+room_num);
            }else{
                System.out.println("Reservation not found for the given id and username");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
       

    }

    public static void updateReservation(Connection con, Statement stmt, Scanner sc){
        System.out.println("Enter the id you want to Update");
        int reservationId= sc.nextInt();
        sc.nextLine();  

        if (!reservationExists(con, stmt, reservationId)) {
            System.out.println("Reservation not found for the given id");
            return;
        }

        System.out.println("Enter the new guest name");
        String name = sc.nextLine();
        System.out.println("Enter the room number ");
        int roomNo = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the contact number");
        String contact = sc.nextLine();



        String query = "UPDATE reservation SET name = '" + name + "', " +
                    "room_num = " + roomNo + ", " +
                    "contact_num = '" + contact + "' " +
                    "WHERE id = " + reservationId;


        try {
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected>0) {
                System.out.println("Reservaion updated successfully");
            } else {
                System.out.println("Reservation updation uncessfull");
            }
        } catch (
            SQLException e) {
            e.printStackTrace();
        }



    }

    public static void deleteReservation(Connection con, Statement stmt, Scanner sc){

        System.out.println("Enter the id you want to delete");
        int resrvationId= sc.nextInt();

        if (!reservationExists(con, stmt, resrvationId)) {
            System.out.println("Reservation not found for the given id ");
            return;
        }

        String sql = "delete from reservation where id = "+resrvationId;

        try {
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected>0) {
                System.out.println("Deletion successfull");
            } else {
                System.out.println("Deletion unsuccesssfull");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean reservationExists(Connection con, Statement stmt, int reservationId){

        try {
            String sql = "SELECT id from reservation where id = "+ reservationId;
            ResultSet resultSet = stmt.executeQuery(sql);
            return resultSet.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException{

        System.out.print("Exiting system");
        int i= 5;
        while (i>=0) {
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println("Thanks for using the hotel reservation sytem");
    }

}
