
CREATE TABLE IF NOT EXISTS employees (
    employee_id VARCHAR(10) PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    age         INT NOT NULL CHECK (age > 0),
    gender      VARCHAR(10) NOT NULL,
    salary      INT NOT NULL CHECK (salary >= 0),
    designation VARCHAR(20) NOT NULL
);

select * from employees;