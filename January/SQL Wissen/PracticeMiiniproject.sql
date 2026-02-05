create table sites(
site_id serial primary key,

length int not null,
breadth int not null,
area numeric(10,2) not null,
status varchar(10) check (status in ('OCCUPIED','VACANT')) default 'VACANT',
type varchar(30) check (type in ('VILLA','APPARTMENT','INDEPENDENT','OPEN_SITE')),
is_built boolean default false
);

alter table sites
add column type varchar(30) check (type in ('VILLA','APPARTMENT','INDEPENDENT','OPEN_SITE'));

alter table sites 
add column owner_id int references users(owner_id);
select * from sites;

create table users(
user_id serial primary key,
name varchar(100) not null,
email varchar(100) unique not null,
password varchar(100) unique not null,
role varchar(10) check(role in ('ADMIN','USER')) not null
);
select * from users;

insert into users values(1,'Joshua','joshua@gmail.com','123456','ADMIN');
insert into users values(2,'Badal','badal@gmail.com','badal123','USER');
select * from users;


create table maintenance(
maintenance_id serial primary key,
site_id int references sites(site_id),
total_amount numeric(10,2) not null,
paid_amount numeric(10,2),
pending_amount numeric(10,2) GENERATED ALWAYS AS (total_amount - paid_amount) STORED
);

select * from maintenance;

-- 1) Drop in dependency order (safe if re-running)
DROP TABLE IF EXISTS maintenance CASCADE;
DROP TABLE IF EXISTS sites CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 2) Create users first (FK target)
CREATE TABLE users (
    user_id   SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    password  VARCHAR(100) UNIQUE NOT NULL,
    role      VARCHAR(10)  NOT NULL CHECK (role IN ('ADMIN','USER'))
);

-- 3) Create sites (reference users.user_id)
CREATE TABLE sites (
    site_id  SERIAL PRIMARY KEY,
    length   INT NOT NULL,
    breadth  INT NOT NULL,
    area     NUMERIC(10,2) NOT NULL,
    status   VARCHAR(10) DEFAULT 'VACANT' CHECK (status IN ('OCCUPIED','VACANT')),
    type     VARCHAR(30) CHECK (type IN ('VILLA','APPARTMENT','INDEPENDENT','OPEN_SITE')),
    is_built BOOLEAN DEFAULT FALSE,
    owner_id INT REFERENCES users(user_id)
    -- Optional: ensure area matches length * breadth if you want data integrity:
    -- , CONSTRAINT sites_area_chk CHECK (area = (length * breadth)::NUMERIC)
    -- Or use a generated column (Postgres 12+):
    -- , area NUMERIC(10,2) GENERATED ALWAYS AS ((length * breadth)::NUMERIC) STORED
);

-- 4) Create maintenance (reference sites)
CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    site_id        INT REFERENCES sites(site_id),
    total_amount   NUMERIC(10,2) NOT NULL,
    paid_amount    NUMERIC(10,2),
    pending_amount NUMERIC(10,2) GENERATED ALWAYS AS (total_amount - COALESCE(paid_amount, 0)) STORED
);

-- 5) Seed data (optional)
INSERT INTO users (user_id, name, email, password, role) VALUES
(1, 'Joshua', 'joshua@gmail.com', '123456', 'ADMIN'),
(2, 'Badal',  'badal@gmail.com',  'badal123','USER');

-- Example insert into sites (optional)
-- INSERT INTO sites (length, breadth, area, status, type, is_built, owner_id)
-- VALUES (40, 30, 1200.00, 'VACANT', 'OPEN_SITE', FALSE, 1);

-- Quick checks
SELECT * FROM users;
SELECT * FROM sites;
SELECT * FROM maintenance;

CREATE TABLE site_requests (
    request_id SERIAL PRIMARY KEY,
    site_id INT REFERENCES sites(site_id),
    user_id INT REFERENCES users(user_id),
    status VARCHAR(10) CHECK (status IN ('PENDING','APPROVED','REJECTED')) DEFAULT 'PENDING'
);
