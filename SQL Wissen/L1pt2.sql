create table emp(
EID int primary key,
name varchar(20),
age int,
email varchar(30) unique
);

drop table emp;

select * from emp;

describe table EMP;

insert into EMP values(101,'Josh',21,'josh@gmail.com');
insert into EMP(EID,NAME,AGE) values(102,'Badal',23)


create table EMP(EID int primary key,NAME varchar(20))