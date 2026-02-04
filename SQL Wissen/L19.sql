create table Employee(
EID int primary key,
emp_name TEXT,
salary int,
designation text,
manager_id int
);


find employee who earns more than their manager
insert into Employee values
(1, 'Amit',     90000, 'Manager',        NULL),
(2, 'Rahul',    60000, 'Employee',       1),
(3, 'Sneha',    70000, 'Employee',       1),
(4, 'Neha',     95000, 'Employee',       1),
(5, 'Rohan',    85000, 'Manager',        NULL),
(6, 'Priya',    80000, 'Employee',       5),
(7, 'Karan',    88000, 'Employee',       5),
(8, 'Meera',    75000, 'Employee',       5);

select * from Employee;
drop table Employee;

select e.emp_name,e.salary 
FROM Employee AS e
JOIN Employee AS m
  ON e.manager_id = m.EID
WHERE e.salary > m.salary;


using foreign keys
create table Employee(
EID int primary key,
emp_name TEXT,
salary int,
designation text,
manager_id int references Employee(EID)
)
