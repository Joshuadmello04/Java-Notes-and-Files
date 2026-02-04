
CREATE TABLE IF NOT EXISTS employees (
    employee_id VARCHAR(10) PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    age         INT NOT NULL CHECK (age > 0),
    gender      VARCHAR(10) NOT NULL,
    salary      INT NOT NULL CHECK (salary >= 0),
    designation VARCHAR(20) NOT NULL
);

select * from employees;

select * from emp;
delete from emp where name = 'Badal';
alter table emp drop column email;

create table Student (ROLLNO int ,NAME varchar(10),STANDARD int);

insert into STUDENT values(11,'Rinki',2);
insert into STUDENT values(12,'Pinki',3);
insert into STUDENT values(13,'Chinki',4);