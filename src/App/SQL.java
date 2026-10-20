package App;

import DatabaseConnection.DatabaseConnection; // I import DatabaseConnection so I can acess DatabaseConnection.conn which holds the live database connection

import java.sql.PreparedStatement;// I import PreparedStatement because I nedd this type to store and execute my SQL statements

import java.sql.ResultSet;//I import ResultSet because executeQuery() returns a ResultSet - it holds all the rows the database found

import java.sql.SQLException;//I import SQLException because all database operations can fail and I need to catch those errors

//I created this class with ONE job: send SQL to the database and print the results
//I never show menus here - that is App.java's job
public class SQL {


    //I made this method static so App.java can call SQL.showAllCustomers() without creating an object
    //I used void because this method doesn't return anything - it just prints to the screen
    public static void showAllCustomers(){

        //I use try/catch because the database can give an error at any time
        try{
            System.out.println("========================================");
            System.out.println("            ALL CUSTOMERS               ");
            System.out.println("========================================");

            //I store the SQL in a String variable so it is easy to read and change
            String SQL = "SELECT cust_ID, name, DOB, cust_address FROM customer";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            //I use boolean found because if the table is empty the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            //I use while because there can be many customers, the loop runs once for each row
            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("ID: "      + result.getInt("cust_ID"));      //I use getInt() because cust_ID is a number
                System.out.println("Name: "    + result.getString("name"));      //I use getString() because name is text
                System.out.println("DOB: "     + result.getString("DOB"));       //I use getString() because dates come back as text
                System.out.println("Address: " + result.getString("cust_address")); //I use getString() because cust_address is text
                System.out.println("----------------------------------------");
            }
            //I check found after the loop because if it is still false, the table had no rows, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No customers found.");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    //I made this method static so App.java can call SQL.searchCustomerByName() without creating an object
    public static void searchCustomerByName(){

        //I use try/catch because the database can give an error at any time
        try{
            //I use readString() to ask the user to type a name or part of a name as a text (String)
            String name = App.readString("Enter customer name to search: ");

            //I check if the name is empty because searching with an empty string would return all customers
            if(name.isEmpty()){
                System.out.println("Please enter a name to search.");
                return;
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use LIKE with ? because I want to search for partial matches
            String SQL = "SELECT cust_ID, name, cust_address FROM customer WHERE name LIKE ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            //I add % before and after so the database finds the name anywhere in the text
            stmt.setString(1, "%" + name + "%");

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            System.out.println("========================================");
            System.out.println("  CUSTOMERS MATCHING: " + name);
            System.out.println("========================================");

            //I use boolean found because if the table is empty the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("ID: "      + result.getInt("cust_ID"));         //I use getInt() to read cust_ID which is stored as an integer
                System.out.println("Name: "    + result.getString("name"));         //I use getString() to read name which holds text
                System.out.println("Address: " + result.getString("cust_address")); //I use getString() to read cust_address which holds text
                System.out.println("----------------------------------------");
            }
            //I check found after the loop because if it is still false, the table had no rows, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No customers found matching: " + name);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateCustomerAddress(){

        //I use try/catch because the database can give an error at any time
        try{
            int id = App.readInt("Enter customer ID: "); //I use readInt() because the ID is a number

            if(id <= 0){
                System.out.println("ID must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            String address = App.readString("Enter new address: "); //I use readString() because address is text

            //I check if the address is empty because saving an empty address in the database makes no sense
            if(address.isEmpty()){
                System.out.println("Address cannot be empty.");
                return;
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use SET cust_address = ? and WHERE cust_ID = ? to update only the address of one customer
            String SQL = "UPDATE customer SET cust_address = ? WHERE cust_ID = ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setString(1, address); //I use setString() because address is text
            stmt.setInt(2, id);         //I use setInt() because the ID is a number

            //I use executeUpdate() because UPDATE changes data - it returns how many rows were changed
            int rows = stmt.executeUpdate();

            //I check rows because if it is 0 no customer was found with that ID so nothing was updated
            if(rows > 0){
                System.out.println("Address updated successfully.");
            } else {
                System.out.println("No customer found with ID: " + id);
            }

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showAllCars(){

        //I use try/catch because the database can give an error at any time
        try{
            System.out.println("========================================");
            System.out.println("            ALL CAR TYPES               ");
            System.out.println("========================================");

            //I store the SQL in a String variable so it is easy to read and change
            String SQL = "SELECT car_type_ID, car_type, base_rent FROM car_type";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            //I use boolean found because if the table is empty the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            //I use while because there can be many car types, the loop runs once for each row
            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("ID: "        + result.getInt("car_type_ID"));   //I use getInt() because car_type_ID is stored as an integer
                System.out.println("Type: "      + result.getString("car_type"));   //I use getString() to read the car type name
                System.out.println("Base Rent: " + result.getDouble("base_rent"));  //I use getDouble() because base_rent is a decimal number
                System.out.println("----------------------------------------");
            }

            //I check found after the loop because if it is still false, the table had no rows, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No car types found.");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchCarByID(){

        //I use try/catch because the database can give an error at any time
        try{
            int id = App.readInt("Enter car type ID: "); //I use readInt() because the ID is a number

            if(id <= 0){
                System.out.println("ID must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use WHERE car_type_ID = ? to find exactly one car type by its ID
            String SQL = "SELECT car_type_ID, car_type, base_rent FROM car_type WHERE car_type_ID = ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setInt(1, id); //I use setInt() because the ID is a number

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            System.out.println("========================================");
            System.out.println("          CAR TYPE DETAILS              ");
            System.out.println("========================================");

            //I use if instead of while because an ID is unique so the database returns only one row or nothing
            if(result.next()){ //I use result.next() to move to the first row - it returns true if a row exists and false if nothing was found
                System.out.println("ID: "        + result.getInt("car_type_ID"));   //I use getInt() because the ID column holds a whole number
                System.out.println("Type: "      + result.getString("car_type"));   //I use getString() because car_type holds a text value
                System.out.println("Base Rent: " + result.getDouble("base_rent"));  //I use getDouble() because the rent price has decimal places
            } else { //I use else because if result.next() is false no car type was found with that ID
                System.out.println("No car type found with ID: " + id);
            }
            System.out.println("========================================");

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchCarsByPrice(){

        //I use try/catch because the database can give an error at any time
        try{
            double price = App.readDouble("Enter maximum price per day: "); //I use readDouble() because price has decimals

            if(price <= 0){
                System.out.println("Price must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use WHERE base_rent < ? to find all car types cheaper than the price the user typed
            String SQL = "SELECT car_type_ID, car_type, base_rent FROM car_type WHERE base_rent < ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setDouble(1, price); //I use setDouble() because price has decimals

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            System.out.println("========================================");
            System.out.println("   CAR TYPES CHEAPER THAN: " + price);
            System.out.println("========================================");

            //I use boolean found because if no rows match the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            //I use while because there can be many car types cheaper than the price
            while(result.next()){ //I use result.next() to move to the first row - it returns true if a row exists and false if nothing was found
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("ID: "        + result.getInt("car_type_ID"));   //I use getInt() to get the car ID as a whole number
                System.out.println("Type: "      + result.getString("car_type"));   //I use getString() because the car type name is text
                System.out.println("Base Rent: " + result.getDouble("base_rent"));  //I use getDouble() because base_rent has decimal places
                System.out.println("----------------------------------------");
            }

            //I check found after the loop because if it is still false, no car type matched the price, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No car types found cheaper than: " + price);
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showAllBookings(){

        //I use try/catch because the database can give an error at any time
        try{
            System.out.println("========================================");
            System.out.println("            ALL BOOKINGS                ");
            System.out.println("========================================");

            //I store the SQL in a String variable so it is easy to read and change
            String SQL = "SELECT booking_ID, car_type_ID, cust_ID, date_of_booking FROM booking_details";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            //I use boolean found because if the table is empty the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            //I use while because there can be many bookings, the loop runs once for each row
            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("Booking ID:  " + result.getInt("booking_ID"));         //I use getInt() because booking_ID is stored as an integer
                System.out.println("Car Type ID: " + result.getInt("car_type_ID"));        //I use getInt() to read the car type ID as a whole number
                System.out.println("Customer ID: " + result.getInt("cust_ID"));            //I use getInt() to read the customer ID as a whole number
                System.out.println("Date:        " + result.getString("date_of_booking")); //I use getString() because the date comes back as text from the database
                System.out.println("----------------------------------------");
            }

            //I check found after the loop because if it is still false, the table had no rows, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No bookings found.");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchBookingByID(){

        //I use try/catch because the database can give an error at any time
        try{
            int id = App.readInt("Enter booking ID: "); //I use readInt() because the ID is a number

            if(id <= 0){
                System.out.println("ID must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use JOIN to get the car type name from car_type table because booking_details only stores the car_type_ID
            //I use WHERE booking_ID = ? to find exactly one booking by its ID
            String SQL = "SELECT bd.booking_ID, ct.car_type, bd.cust_ID, bd.date_of_booking " +
                         "FROM booking_details bd " +
                         "JOIN car_type ct ON bd.car_type_ID = ct.car_type_ID " +
                         "WHERE bd.booking_ID = ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setInt(1, id); //I use setInt() because the ID is a number

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            System.out.println("========================================");
            System.out.println("          BOOKING DETAILS               ");
            System.out.println("========================================");

            //I use if instead of while because an ID is unique so the database returns only one row or nothing
            if(result.next()){ //I use result.next() to move to the first row - it returns true if a row exists and false if nothing was found
                System.out.println("Booking ID:  " + result.getInt("booking_ID"));         //I use getInt() because booking_ID is a whole number
                System.out.println("Car Type:    " + result.getString("car_type"));        //I use getString() because car_type holds the name of the car as text
                System.out.println("Customer ID: " + result.getInt("cust_ID"));            //I use getInt() to read the customer ID as an integer
                System.out.println("Date:        " + result.getString("date_of_booking")); //I use getString() because MySQL returns dates as text
            } else { //I use else because if result.next() is false no booking was found with that ID
                System.out.println("No booking found with ID: " + id);
            }

            System.out.println("========================================");

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchBookingsByCustomerID(){

        //I use try/catch because the database can give an error at any time
        try{
            int id = App.readInt("Enter customer ID: "); //I use readInt() because the ID is a number

            if(id <= 0){
                System.out.println("ID must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use WHERE cust_ID = ? to find all bookings that belong to one customer
            String SQL = "SELECT booking_ID, car_type_ID, cust_ID, date_of_booking FROM booking_details WHERE cust_ID = ?";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setInt(1, id); //I use setInt() because the ID is a number

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            System.out.println("========================================");
            System.out.println("   BOOKINGS FOR CUSTOMER ID: " + id);
            System.out.println("========================================");

            //I use boolean found because one customer can have many bookings so I need to know if any were returned
            boolean found = false;

            //I use while because one customer can have more than one booking, the loop runs once for each row
            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("Booking ID:  " + result.getInt("booking_ID"));         //I use getInt() to read booking_ID as a whole number
                System.out.println("Car Type ID: " + result.getInt("car_type_ID"));        //I use getInt() because car_type_ID is stored as an integer
                System.out.println("Date:        " + result.getString("date_of_booking")); //I use getString() because MySQL returns date values as text
                System.out.println("----------------------------------------");
            }

            //I check found after the loop because if it is still false, no bookings exist for that customer ID, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No bookings found for customer ID: " + id);
            }

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showAllServices(){

        //I use try/catch because the database can give an error at any time
        try{
            System.out.println("========================================");
            System.out.println("            ALL SERVICES                ");
            System.out.println("========================================");

            //I store the SQL in a String variable so it is easy to read and change
            String SQL = "SELECT service_ID, service_name, service_charge FROM service_details";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            //I use executeQuery() because SELECT gets rows from the database and puts them in the ResultSet
            ResultSet result = stmt.executeQuery();

            //I use boolean found because if the table is empty the while loop never runs so found stays false and I can show a message after
            boolean found = false;

            //I use while because there can be many services, the loop runs once for each row
            while(result.next()){
                found = true; //I set found to true so I know at least one row was returned
                System.out.println("ID: "     + result.getInt("service_ID"));       //I use getInt() because service_ID is stored as a whole number
                System.out.println("Name: "   + result.getString("service_name"));  //I use getString() to read the service name as text
                System.out.println("Charge: " + result.getDouble("service_charge"));//I use getDouble() because the charge has decimal places like 10.00
                System.out.println("----------------------------------------");
            }

            //I check found after the loop because if it is still false, the table had no rows, so I put NOT(!found) for the if
            if(!found){
                System.out.println("No services found.");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createService(){

        //I use try/catch because the database can give an error at any time
        try{
            String name   = App.readString("Enter service name: ");   //I use readString() because service_name is text

            //I check if the name is empty because a service must always have a name
            if(name.isEmpty()){
                System.out.println("Service name cannot be empty.");
                return;
            }

            double charge = App.readDouble("Enter service charge: "); //I use readDouble() because service_charge has decimals

            if(charge <= 0){
                System.out.println("Charge must be greater than zero.");
                return; //I use return to go back to the menu without running the query
            }

            //I store the SQL in a String variable so it is easy to read and change
            //I use INSERT INTO to add a new row to the service_details table
            String SQL = "INSERT INTO service_details (service_name, service_charge) VALUES (?, ?)";

            //I use prepareStatement() to send my SQL to the database and get it ready to run
            PreparedStatement stmt = DatabaseConnection.conn.prepareStatement(SQL);

            stmt.setString(1, name);   //I use setString() because service_name is text
            stmt.setDouble(2, charge); //I use setDouble() because service_charge has decimals

            //I use executeUpdate() because INSERT adds a new row to the database - it returns 1 if the row was added and 0 if it failed
            int rows = stmt.executeUpdate();

            //I check rows because if it is 0 the service was not added
            if(rows > 0){
                System.out.println("Service created successfully.");
            } else {
                System.out.println("Could not create service.");
            }

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
