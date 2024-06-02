import java.sql.*;
import java.util.Scanner;


public class database {
    public static void main(String[] args) throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(
                /*connection goes here*/
        Statement st = con.createStatement();
        try {
            st.executeQuery("drop table pierr_dog");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            st.executeQuery("drop table pierr_breed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        st.executeQuery("create table pierr_breed (breed_id number, breed_desc varchar2(20), primary key(breed_id))");
        st.executeQuery("create table pierr_dog (name varchar2(20), breed_id number, constraint fk_breed_id foreign key (breed_id) references pierr_breed (breed_id))");
        showMenu();
    }


    //Display the user input menu and handles inputs
    public static void showMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("1) Insert");
            System.out.println("2) Delete");
            System.out.println("3) Update");
            System.out.println("4) View");
            System.out.println("5) Quit");
            input = scanner.nextInt();
            if (input == 1) {
                showInsert();
            }
            if (input == 2) {
                showDelete();
            }
            if (input == 3) {
                showUpdate();
            }
            if (input == 4) {
                showView();
            }
        }
        while (input != 5);
    }


    //Displays the inserting menu and handles inserts
    public static void showInsert() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String input;
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@sabzevi2.homeip.net:1521:orcl", "csus", "student");
        Statement st = con.createStatement();

        System.out.println("Enter the dog's name, it's breed, and give an ID, seperated by commas");
        input = scanner.nextLine();
        input = input.toLowerCase();
        String attributes[] = new String[4];
        attributes = input.split(",", 3);
        try {
//            while (checkIDDuplicate(attributes[1], attributes[2])) {
//                System.out.println("Tried overriding a breed. Try again");
//                System.out.println("Enter the dog's name, it's breed, and give an ID, seperated by commas");
//                input = scanner.nextLine();
//                input = input.toLowerCase();
//                attributes = input.split(",", 3);
//            }
            st.executeQuery("insert into pierr_breed values (" + attributes[2] + ", '" + attributes[1] + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL threw an error. Invalid entry attempted.");
        }

        try {
            st.executeQuery("insert into pierr_dog values (" + "'" + attributes[0] + "'," + attributes[2] + ")");
        } catch (SQLException e) {
            System.out.println("SQL threw an error. Invalid entry attempted.");
        }


//        System.out.println("What table are you inserting in? Dog or Breed?");
//        input = scanner.nextLine();
//        input.toLowerCase();
//        if (input.equals("dog")) {
//            System.out.println("Enter the dogs name, and its breed id seperated by commas");
//            input = scanner.nextLine();
//            String attributes[] = new String[2];
//            attributes = input.split(",", 2);
//            try {
//                st.executeQuery("insert into pierr_dog values (" + "'" + attributes[0]  + "'," + attributes[1] + ")");
//                System.out.println("Inserted name " + attributes[0] + " and breed_id " + attributes[1] + " into the dog table");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("Insert Failed");
//            }
//        }
//
//        if (input.equals("breed")) {
//            System.out.println("Enter the breed's id, and description seperated by commas");
//            input = scanner.nextLine();
//            String attributes[] = new String[2];
//            attributes = input.split(",", 2);
//            try {
//                st.executeQuery("insert into pierr_breed values (" + attributes[0] + ", '" + attributes[1] + "')");
//                System.out.println("Inserted breed_id " + attributes[0] + " and description " + attributes[1] + " into the breed table");
//            } catch (SQLException e) {
//               e.printStackTrace();
//                System.out.println("Insert failed");
//            }
//        }
    }


    //Displays the deleting menu and handles deletes
    public static void showDelete() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String input;
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@sabzevi2.homeip.net:1521:orcl", "csus", "student");
        Statement st = con.createStatement();

        System.out.println("Who are you trying to delete from the dog table?");
        input = scanner.nextLine();
        try {
            st.executeUpdate("delete from pierr_dog where name = " + "'" + input.toLowerCase() + "'");
            System.out.println(input + " successfully deleted");
        } catch (SQLException e) {
            System.out.println(input + " delete unsuccessful");
            e.printStackTrace();
        }
    }


    //Displays the update menu and handles updates
    public static void showUpdate() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String preUpdateInput;
        String newUpdateInput;
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@sabzevi2.homeip.net:1521:orcl", "csus", "student");
        Statement st = con.createStatement();

        System.out.println("What breed are you trying to update?");
        preUpdateInput = scanner.nextLine();
        preUpdateInput.toLowerCase();
        System.out.println("What are you trying to change it to?");
        newUpdateInput = scanner.nextLine();
        try {
            st.executeUpdate("update pierr_breed set breed_desc = " + "'" + newUpdateInput + "'" + "where breed_desc = " + "'" + preUpdateInput + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Displays all records from both tables omitting the keys.
    public static void showView() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@sabzevi2.homeip.net:1521:orcl", "csus", "student");
        Statement st = con.createStatement();
        ResultSet rs=st.executeQuery("select name,pierr_breed.breed_desc from pierr_dog join pierr_breed on pierr_dog.breed_id = pierr_breed.breed_id ");
        while (rs.next()) {
            //System.out.print(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + "\n");
            System.out.print(rs.getString(1) + " " + rs.getString(2) + "\n");
        }

    }

//    public static boolean checkIDDuplicate(String breed, String breedID) throws SQLException {
//        Connection con = DriverManager.getConnection(
//                "jdbc:oracle:thin:@sabzevi2.homeip.net:1521:orcl", "csus", "student");
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery("select * from pierr_breed");
//            while (rs.next()) {
//                System.out.println(rs.getString(2) + " " + rs.getString(1));
//                if (rs.getString(2).equals(breed) && rs.getString(1).equals(breedID)) {
//                    return true;
//                }
//            }
//            return false;
//    }
}
