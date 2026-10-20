-- I use DROP DATABASE IF EXISTS to delete the old database if it exists so I can start fresh without errors
DROP DATABASE IF EXISTS car_rent;

-- I use CREATE DATABASE IF NOT EXISTS to create the database only if it does not already exist
CREATE DATABASE IF NOT EXISTS car_rent;

-- I use USE to tell MySQL which database I want to work in
USE car_rent;

-- I use SHOW TABLES to check if there are any tables already in the database
SHOW TABLES;

-- I create car_type to store the types of cars available for rent and their base price
CREATE TABLE car_type (
    car_type_ID INT AUTO_INCREMENT PRIMARY KEY, -- I use AUTO_INCREMENT so the ID is set automatically for each new row
    car_type VARCHAR(20) NOT NULL,              -- I use NOT NULL because every car type must have a name
    base_rent DECIMAL (6,2) NOT NULL            -- I use DECIMAL(6,2) because the rent price has decimals like 50.00
);

-- I use DESCRIBE to check if the table was created with the correct columns
DESCRIBE car_type;

-- I create customer to store the personal details of each customer
CREATE TABLE customer (
    cust_ID INT PRIMARY KEY,           -- I use INT PRIMARY KEY without AUTO_INCREMENT because I set the customer ID manually
    name VARCHAR(50) NOT NULL,         -- I use NOT NULL because every customer must have a name
    DOB DATE,                          -- I use DATE because the date of birth is stored as a date value
    cust_address VARCHAR(100)          -- I allow NULL here because the address is optional
);

-- I use DESCRIBE to check if the table was created with the correct columns
DESCRIBE customer;

-- I create booking_details to store each booking made by a customer for a car type
CREATE TABLE booking_details (
    booking_ID INT AUTO_INCREMENT PRIMARY KEY,                          -- I use AUTO_INCREMENT so each booking gets a unique ID automatically
    car_type_ID INT NOT NULL,                                           -- I use NOT NULL because a booking must have a car type
    cust_ID INT NOT NULL,                                               -- I use NOT NULL because a booking must belong to a customer
    date_of_booking DATE NOT NULL,                                      -- I use NOT NULL because every booking must have a date
    FOREIGN KEY (car_type_ID) REFERENCES car_type(car_type_ID),        -- I use FOREIGN KEY so car_type_ID must exist in the car_type table
    FOREIGN KEY (cust_ID) REFERENCES customer(cust_ID)                 -- I use FOREIGN KEY so cust_ID must exist in the customer table
);

-- I use DESCRIBE to check if the table was created with the correct columns
DESCRIBE booking_details;

-- I create service_details to store the extra services customers can add to their booking
CREATE TABLE service_details (
    service_ID INT AUTO_INCREMENT PRIMARY KEY,                          -- I use AUTO_INCREMENT so each service gets a unique ID automatically
    service_name VARCHAR(50) NOT NULL,                                  -- I use NOT NULL because every service must have a name
    service_charge DECIMAL(6,2) NOT NULL CHECK(service_charge > 0)     -- I use CHECK to make sure the charge is always greater than zero
);

-- I use DESCRIBE to check if the table was created with the correct columns
DESCRIBE service_details;

-- I create service_booking to link bookings to the services they include
-- I use a composite PRIMARY KEY because the same booking and service combination cannot appear twice
CREATE TABLE service_booking (
    booking_ID INT,
    service_ID INT,
    PRIMARY KEY (booking_ID, service_ID),                               -- I use composite PRIMARY KEY because one booking can have many services
    FOREIGN KEY (booking_ID) REFERENCES booking_details(booking_ID),   -- I use FOREIGN KEY so booking_ID must exist in booking_details
    FOREIGN KEY (service_ID) REFERENCES service_details(service_ID)    -- I use FOREIGN KEY so service_ID must exist in service_details
);

-- I use DESCRIBE to check if the table was created with the correct columns
DESCRIBE service_booking;
