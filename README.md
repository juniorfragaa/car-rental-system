# Car Rental System

A Java console application connected to a MySQL database, built as a database assignment (CA2) at CCT College Dublin, Higher Diploma in Computing.

Staff use a text menu to work with four areas of a car rental business: customers, cars, bookings and services. The program can list and search records, update a customer's address and add new services. Every query uses prepared statements.

## What it does

| Menu | Options |
|---|---|
| Customers | Show all, search by name, update address |
| Cars | Show all car types, search by ID, find cars cheaper than a price |
| Bookings | Show all, search by booking ID, search by customer ID |
| Services | Show all, create a new service |

## Database

The `car_rent` database has five related tables: `car_type`, `customer`, `booking_details`, `service_details` and `service_booking` (the services added to each booking). Primary and foreign keys link them.

`SQL-DATABASE/CA2_DataBase.sql` creates the tables. `SQL-DATABASE/CA2_DataBase2.sql` inserts sample data and runs the reports, written with JOIN, GROUP BY and COUNT, such as total bookings per car type and the most booked services.

## Project structure

```
src/App/App.java                                 menus and input reading
src/App/SQL.java                                 every SQL query and how its results are printed
src/DatabaseConnection/DatabaseConnection.java   opens the connection to MySQL
SQL-DATABASE/                                    scripts to create and fill the database
```

## How to run

1. Install MySQL and run the two scripts in `SQL-DATABASE/`, in order.
2. Open the project in Apache NetBeans and add the MySQL Connector/J jar to the project libraries.
3. Set your database login as environment variables before running:

   ```
   DB_USER=root
   DB_PASSWORD=your_password
   ```

4. Run `App.java`.

The password is read from an environment variable so it never appears in the code.

## Built with

Java, MySQL, JDBC, Apache NetBeans
