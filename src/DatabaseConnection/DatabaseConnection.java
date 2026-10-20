package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Stores the live connection to the database
    public static Connection conn;

    // connect() opens the connection to the database
    // throws SQLException so main() can catch the error if something goes wrong
    public static void connect() throws SQLException {

        String URL      = "jdbc:mysql://localhost:3306/"; // Database server address
        String DATABASE = "car_rent";                     // Database name
        // The username and password are read from environment variables,
        // so the real password never goes into the code or into GitHub
        String USERNAME = System.getenv().getOrDefault("DB_USER", "root"); // DB username (root if not set)
        String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", ""); // DB password

        conn = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
        System.out.println("Connected to " + DATABASE + " database.");
    }
}
