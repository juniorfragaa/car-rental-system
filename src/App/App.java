package App; 

// I import DatabaseConnection so I can call DatabaseConnection.connect() inside main()
import DatabaseConnection.DatabaseConnection;

// I import SQLException because main() uses catch (SQLException e) to handle connection errors
import java.sql.SQLException;

// I import Scanner because kb is of type Scanner it reads what the user types
import java.util.Scanner;

public class App {

    // Stores the keyboard reader (static so every method in this class can use it)
    public static Scanner kb;


    public static void main(String[] args) {

        // Try to connect to the database and stop the program if it fails
        try {
            DatabaseConnection.connect();
        } catch (SQLException e) {
            System.out.println("Error: Could not connect to database: " + e.getMessage());
            return; // no point continuing without a database connection
        }

        // new Scanner(System.in) turns kb on and now it can read whatever the user types
        kb = new Scanner(System.in);

        // I call mainMenu() to start showing options to the user
        mainMenu();
    }


    public static void mainMenu() {

        // I use String because the options mix numbers and the letter "x"
        String option;

        // do/while guarantees the menu always shows at least once before checking anything
        do {
            System.out.println("========================================");
            System.out.println("            CAR RENTAL SYSTEM           ");
            System.out.println("========================================");
            System.out.println("1 - Customer Menu");
            System.out.println("2 - Car Menu");
            System.out.println("3 - Bookings Menu");
            System.out.println("4 - Services Menu");
            System.out.println("x - Exit");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            option = kb.nextLine();

            switch (option) {
                case "1" -> customerMenu();  // go to the customer submenu
                case "2" -> carMenu();       // go to the car submenu
                case "3" -> bookingMenu();   // go to the bookings submenu
                case "4" -> servicesMenu();  // go to the services submenu
                case "x" -> { return; }     // return exits the method immediately
                default  -> System.out.println("Invalid option");
            }

        } while (true); // keeps looping until the user types "x" and return runs
    }


    public static void customerMenu() {

        String option;

        do {
            System.out.println("========================================");
            System.out.println("            CUSTOMER MENU               ");
            System.out.println("========================================");
            System.out.println("1 - Show all customers");
            System.out.println("2 - Search customer by name");
            System.out.println("3 - Update customer address");
            System.out.println("x - Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            option = kb.nextLine();

            switch (option) {
                case "1" -> SQL.showAllCustomers();        // SELECT - shows every customer
                case "2" -> SQL.searchCustomerByName();    // WHERE LIKE - finds by name
                case "3" -> SQL.updateCustomerAddress();   // UPDATE - changes the address of one customer
                case "x" -> { return; }                    // exits this method - goes back to mainMenu
                default  -> System.out.println("Invalid option");
            }

        } while (true);
    }


    public static void carMenu() {

        String option;

        do {
            System.out.println("========================================");
            System.out.println("               CAR MENU                 ");
            System.out.println("========================================");
            System.out.println("1 - Show all cars");
            System.out.println("2 - Search car by ID");
            System.out.println("3 - Cars cheaper than a price");
            System.out.println("x - Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            option = kb.nextLine();

            switch (option) {
                case "1" -> SQL.showAllCars();        // SELECT - shows every car
                case "2" -> SQL.searchCarByID();      // WHERE = - finds one car by ID
                case "3" -> SQL.searchCarsByPrice();  // WHERE < - finds cars cheaper than a price
                case "x" -> { return; }               // exits this method - goes back to mainMenu
                default  -> System.out.println("Invalid option");
            }

        } while (true);
    }


    public static void bookingMenu() {

        String option;

        do {
            System.out.println("========================================");
            System.out.println("             BOOKINGS MENU              ");
            System.out.println("========================================");
            System.out.println("1 - Show all bookings");
            System.out.println("2 - Search booking by ID");
            System.out.println("3 - Search bookings by customer ID");
            System.out.println("x - Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            option = kb.nextLine();

            switch (option) {
                case "1" -> SQL.showAllBookings();
                case "2" -> SQL.searchBookingByID();
                case "3" -> SQL.searchBookingsByCustomerID();
                case "x" -> { return; }  // exits this method - goes back to mainMenu
                default  -> System.out.println("Invalid option");
            }

        } while (true);
    }


    public static void servicesMenu() {

        String option;

        do {
            System.out.println("========================================");
            System.out.println("            SERVICES MENU               ");
            System.out.println("========================================");
            System.out.println("1 - Show all services");
            System.out.println("2 - Create service");
            System.out.println("x - Back to Main Menu");
            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            option = kb.nextLine();

            switch (option) {
                case "1" -> SQL.showAllServices(); // SELECT - shows every service
                case "2" -> SQL.createService();   // INSERT - adds a new service
                case "x" -> { return; }            // exits this method - goes back to mainMenu
                default  -> System.out.println("Invalid option");
            }

        } while (true);
    }

    // readString() shows a message on screen and returns whatever the user typed as text
    public static String readString(String message) {
        System.out.print(message);       // I use print so the user types on the same line as the message
        return kb.nextLine().trim();     // reads everything the user typed and removes any extra spaces
    }


    // readInt() keeps asking until the user types a valid whole number
    // I use nextLine() to read everything including the Enter key - so nothing is left behind
    public static int readInt(String message) {

        while (true) {
            System.out.print(message);
            String typed = kb.nextLine();

            try {
                return Integer.parseInt(typed); // converts text like "5" into the number 5
            } catch (NumberFormatException e) {
                // if the text cannot become a number, ask the user to type again
                System.out.println("Please type a whole number.");
            }
        }
    }


    // readDouble() does the same as readInt() but for decimal numbers like prices (example: 29.99)
    public static double readDouble(String message) {

        while (true) {
            System.out.print(message);
            String typed = kb.nextLine();

            try {
                return Double.parseDouble(typed); // converts text like "29.99" into the decimal number 29.99
            } catch (NumberFormatException e) {
                System.out.println("Please type a number decimal (example: 29.99).");
            }
        }
    }
}
