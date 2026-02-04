1.) --

drop table employees;

CREATE TABLE employees (
    emp_id SERIAL PRIMARY KEY,
    emp_name TEXT,
    manager_id INT
);

INSERT INTO employees (emp_name, manager_id) VALUES
('CEO', NULL),
('CTO', 1),
('CFO', 1),
('Dev1', 2),
('Dev2', 2),
('Accountant', 3);

SELECT e.emp_name  AS employee,
       m.emp_name  AS manager
FROM employees e
JOIN employees m
ON e.manager_id = m.emp_id;


2.) Customers Living in the Same City
Real-world use
Marketing segmentation
Referral programs
Fraud detection (shared location)

drop table customers;

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name TEXT,
    city TEXT
);
INSERT INTO customers (name, city) VALUES
('Rahul', 'Mumbai'),
('Karan', 'Mumbai'),
('Anita', 'Delhi'),
('Neha', 'Delhi'),
('Pooja', 'Pune');

SELECT c1.name AS customer_1,
       c2.name AS customer_2,
       c1.city
FROM customers c1
JOIN customers c2
ON c1.city = c2.city
AND c1.customer_id < c2.customer_id;

3️.) Duplicate User Emails
Real-world use
Data cleanup
Account merge
Security audits

drop table users;

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email TEXT
);
INSERT INTO users (email) VALUES
('a@gmail.com'),
('b@gmail.com'),
('a@gmail.com'),
('c@gmail.com');
SELECT u1.user_id,
       u1.email
FROM users u1
JOIN users u2
ON u1.email = u2.email
AND u1.user_id <> u2.user_id;



4️.)Employees With Same Salary (Payroll / HR) 
Real-world use
Salary audits
Compensation analysis
Pay parity checks

drop table employees;

CREATE TABLE employees (
    emp_id SERIAL PRIMARY KEY,
    emp_name TEXT,
    salary INT
);

INSERT INTO employees (emp_name, salary) VALUES
('Raj', 50000),
('Neha', 50000),
('Amit', 60000),
('Rohit', 60000),
('Pooja', 70000);

SELECT e1.emp_name AS emp1,
       e2.emp_name AS emp2,
       e1.salary
FROM employees e1
JOIN employees e2
ON e1.salary = e2.salary
AND e1.emp_id < e2.emp_id;


5.)Comparing Orders by the Same Customer ie Similarity & Matching Problems
Real-world use
Detect repeat purchases
Compare spending behavior
Fraud analysis

drop table orders;

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INT,
    amount NUMERIC,
    order_date DATE
);

INSERT INTO orders (customer_id, amount, order_date) VALUES
(1, 500, '2024-01-01'),
(1, 800, '2024-01-10'),
(2, 300, '2024-01-05'),
(2, 250, '2024-01-20');

SELECT o1.order_id AS earlier_order,
       o2.order_id AS later_order,
       o1.customer_id
FROM orders o1
JOIN orders o2
ON o1.customer_id = o2.customer_id
AND o1.order_date < o2.order_date;


6.)Social Media: Mutual Followers (Advanced & Real)
Network & Graph Relationships
Real-world use
Instagram / Twitter mutuals
Recommendation systems

drop table follows;

CREATE TABLE follows (
    follower_id INT,
    following_id INT
);
INSERT INTO follows VALUES
(1, 2),
(2, 1),
(2, 3),
(3, 4),
(4, 3),
(5, 6);
SELECT f1.follower_id AS user1,
       f1.following_id AS follows_user
FROM follows f1
JOIN follows f2
ON f1.follower_id = f2.following_id;
AND f1.following_id = f2.follower_id
AND f1.follower_id < f1.following_id;



7.)Product Price Comparison Over Time ie Temporal Comparisons (Time-based)
Real-world use
Price tracking
Analytics dashboards
Inflation monitoring

drop table product_prices;

CREATE TABLE product_prices (
    product_id INT,
    price NUMERIC,
    recorded_at DATE
);
INSERT INTO product_prices VALUES
(101, 100, '2024-01-01'),
(101, 120, '2024-02-01'),
(102, 200, '2024-01-01'),
(102, 180, '2024-02-01');

SELECT p1.product_id,
       p1.price AS old_price,
       p2.price AS new_price
FROM product_prices p1
JOIN product_prices p2
ON p1.product_id = p2.product_id
AND p1.recorded_at < p2.recorded_at
AND p2.price > p1.price;

