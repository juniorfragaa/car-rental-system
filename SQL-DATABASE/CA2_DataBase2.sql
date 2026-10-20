-- I use USE to tell MySQL which database I want to work in
USE car_rent;

-- I insert the car types with their base rent prices
INSERT INTO car_type (car_type, base_rent) VALUES
('Sedan', 50),
('SUV', 80),
('Hatchback', 40),
('Convertible', 70),
('Truck', 90);

-- I use SELECT to confirm all car types were inserted correctly
SELECT * FROM car_type;

-- I insert 20 customers with their ID, name, date of birth and address
INSERT INTO customer (cust_ID, name, DOB, cust_address) VALUES
(101, 'John', '1990-05-15', '123 Main St, City'),
(102, 'Sarah', '1985-07-21', '456 Elm St, Town'),
(103, 'Michael', '1995-01-10', '789 Oak St Village'),
(104, 'Emily', '1988-11-30', '101 Pine Rd, County'),
(105, 'Daniel', '1989-09-05', '222 Cedar Ln, City'),
(106, 'Jessica', '1992-04-20', '333 Birch Ave Town'),
(107, 'Brian', '1987-08-12', '444 Oak Rd, City'),
(108, 'Amanda', '1993-01-25', '555 Elm St Village'),
(109, 'Robert', '1997-06-03', '666 Maple Ln County'),
(110, 'Olivia', '1996-12-18', '777 Pine St, Town'),
(111, 'Glen', '1991-01-06', '128 Willow St, Town'),
(112, 'Junior', '1999-01-18', '592 Riverbend Rd, County'),
(113, 'Edson', '1998-10-08', '847 Maple Grove Ave, City'),
(114, 'Wesley', '1999-07-31', '310 Sunset Blvd, Village'),
(115, 'Taina', '1998-10-03', '921 Cedar Hill Ln, Town'),
(116, 'Leo', '2000-02-08', '463 Highland Park Rd, County'),
(117, 'Gustavo', '2002-01-31', '754 Oakridge Dr, City'),
(118, 'Lucas', '1999-05-16', '289 Birchwood St, Village'),
(119, 'Joao', '2019-04-27', '670 Pinecrest Way, County'),
(120, 'Elenice', '1970-11-03', '512 Elmwood St, City');

-- I use SELECT to confirm all customers were inserted correctly
SELECT * FROM customer;

-- I insert 20 bookings linking each customer to a car type and a date
INSERT INTO booking_details (car_type_ID, cust_ID, date_of_booking) VALUES
(1, 101, '2023-10-17'),
(2, 102, '2023-10-16'),
(3, 103, '2023-10-15'),
(4, 104, '2023-10-14'),
(5, 105, '2023-10-13'),
(1, 106, '2023-10-12'),
(3, 107, '2023-10-11'),
(2, 108, '2023-10-10'),
(4, 109, '2023-10-09'),
(1, 110, '2023-10-08'),
(5, 111, '2023-10-18'),
(3, 112, '2023-10-19'),
(2, 113, '2022-10-29'),
(3, 114, '2021-09-06'),
(1, 115, '2023-12-19'),
(3, 116, '2024-07-18'),
(5, 117, '2020-03-09'),
(2, 118, '2019-08-16'),
(4, 119, '2022-09-14'),
(4, 120, '2022-09-29');

-- I use SELECT to confirm all bookings were inserted correctly
SELECT * FROM booking_details;

-- I insert the 4 extra services available for customers to add to their bookings
INSERT INTO service_details (service_name, service_charge) VALUES
('GPS', 10),
('Child Seat', 15),
('Wash & Vacuum', 20),
('Extra Driver', 15);

-- I use SELECT to confirm all services were inserted correctly
SELECT * FROM service_details;

-- I insert the services chosen for each booking
INSERT INTO service_booking (booking_ID, service_ID) VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 3),
(4, 1),
(4, 3),
(5, 1),
(6, 2),
(7, 3),
(8, 2),
(9, 1),
(9, 4),
(10, 3),
(11, 1),
(11, 3),
(12, 2),
(13, 2),
(14, 1),
(14, 2),
(15, 3),
(16, 3),
(17, 1),
(18, 1),
(18, 4),
(19, 3),
(20, 4);

-- I use SELECT to confirm all service bookings were inserted correctly
SELECT * FROM service_booking;

-- I check all customers before making changes
SELECT * FROM customer;

-- I insert a new customer to test the INSERT operation
INSERT INTO customer (cust_ID, name, DOB, cust_address)
VALUES (121, 'Carlos', '1995-03-10', '100 New St, City');

-- I use SELECT with WHERE to confirm the new customer was added
SELECT * FROM customer WHERE cust_ID = 121;

-- I update the address of customer 101 to test the UPDATE operation
UPDATE customer
SET cust_address = '999 New Road, City'
WHERE cust_ID = 101;

-- I use SELECT with WHERE to confirm the address was updated
SELECT * FROM customer WHERE cust_ID = 101;

-- I check if customer 121 has any bookings before deleting - I need to do this to avoid a FOREIGN KEY error
SELECT * FROM booking_details WHERE cust_ID = 121;

-- I delete customer 121 because they have no bookings so it is safe to remove them
DELETE FROM customer WHERE cust_ID = 121;

-- I use SELECT with WHERE to confirm the customer was deleted
SELECT * FROM customer WHERE cust_ID = 121;

-- I use SELECT to see all customers after the delete
SELECT * FROM customer;

-- I use WHERE LIKE and ORDER BY to show only customers from City sorted by name
SELECT * FROM customer
WHERE cust_address LIKE '%City%'
ORDER BY name ASC;

-- I use JOIN, COUNT and GROUP BY to show how many bookings each car type has
-- I use ct and bd as short names (aliases) for the tables to make the query easier to read
-- I use HAVING to show only car types with 2 or more bookings
-- I use ORDER BY to show the most booked car type first
SELECT ct.car_type, COUNT(bd.booking_ID) AS total_bookings
FROM car_type ct
JOIN booking_details bd ON ct.car_type_ID = bd.car_type_ID
GROUP BY ct.car_type
HAVING COUNT(bd.booking_ID) >= 2
ORDER BY total_bookings DESC;

-- I use JOIN, COUNT and GROUP BY to show how many times each service was booked
-- I use ORDER BY to show the most booked service first
SELECT sd.service_name, COUNT(sb.booking_ID) AS times_booked
FROM service_details sd
JOIN service_booking sb ON sd.service_ID = sb.service_ID
GROUP BY sd.service_name
ORDER BY times_booked DESC;

-- I use INNER JOIN to show only customers who have at least one booking
SELECT c.name, c.cust_address, bd.booking_ID, bd.date_of_booking
FROM customer c
INNER JOIN booking_details bd ON c.cust_ID = bd.cust_ID;

-- I use LEFT JOIN to show all customers even if they have no booking - the booking columns will be NULL for those
SELECT c.name, c.cust_address, bd.booking_ID, bd.date_of_booking
FROM customer c
LEFT JOIN booking_details bd ON c.cust_ID = bd.cust_ID;

-- I use RIGHT JOIN to show all services even if they were never booked - the booking_ID will be NULL for those
SELECT sd.service_name, sd.service_charge, sb.booking_ID
FROM service_booking sb
RIGHT JOIN service_details sd ON sb.service_ID = sd.service_ID;

-- I use UNION of LEFT JOIN and RIGHT JOIN to simulate a FULL JOIN because MySQL does not support FULL JOIN directly
SELECT sd.service_name, sd.service_charge, sb.booking_ID
FROM service_details sd
LEFT JOIN service_booking sb ON sd.service_ID = sb.service_ID

UNION

SELECT sd.service_name, sd.service_charge, sb.booking_ID
FROM service_details sd
RIGHT JOIN service_booking sb ON sd.service_ID = sb.service_ID;

-- I use COUNT, AVG, MIN, MAX and SUM to get basic statistics about the base rental prices
SELECT
    COUNT(*) AS total_cars,
    AVG(base_rent) AS average_rent,
    MIN(base_rent) AS cheapest_rent,
    MAX(base_rent) AS most_expensive_rent,
    SUM(base_rent) AS total_rent
FROM car_type;

-- I use LEFT JOIN, COUNT and GROUP BY to count how many bookings each customer has
-- I use LEFT JOIN so customers with no bookings still appear with a count of 0
SELECT c.name, COUNT(bd.booking_ID) AS total_bookings
FROM customer c
LEFT JOIN booking_details bd ON c.cust_ID = bd.cust_ID
GROUP BY c.name
ORDER BY total_bookings DESC;

-- I use COUNT and multiplication to calculate the total revenue each service has generated
SELECT sd.service_name,
       COUNT(sb.booking_ID) AS times_booked,
       sd.service_charge,
       COUNT(sb.booking_ID) * sd.service_charge AS total_revenue
FROM service_details sd
LEFT JOIN service_booking sb ON sd.service_ID = sb.service_ID
GROUP BY sd.service_name, sd.service_charge
ORDER BY total_revenue DESC;
