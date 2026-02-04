create table users(
user_id serial primary key,
name varchar(100) not null,
email varchar(100) unique not null,
phone varchar(15),
role varchar(10) check (role in ('ADMIN','USER')) not null
);

CREATE TABLE sites (
    site_id SERIAL PRIMARY KEY,
    site_number INT UNIQUE NOT NULL,
    length INT NOT NULL,
    breadth INT NOT NULL,
    area INT NOT NULL,
    status VARCHAR(10) CHECK (status IN ('VACANT', 'OCCUPIED')) DEFAULT 'VACANT'
);

-- Sites 1–10 : 40x60
INSERT INTO sites (site_number, length, breadth, area)
SELECT generate_series(1,10), 40, 60, 2400;

-- Sites 11–20 : 30x50
INSERT INTO sites (site_number, length, breadth, area)
SELECT generate_series(11,20), 30, 50, 1500;

-- Sites 21–35 : 30x40
INSERT INTO sites (site_number, length, breadth, area)
SELECT generate_series(21,35), 30, 40, 1200;

create table properties(
property_id serial primary key,
site_id int unique references sites(site_id),
owner_id int references users(user_id),
property_type varchar(20) check(
	property_type in ('VILLA', 'APARTMENT', 'INDEPENDENT_HOUSE', 'OPEN_SITE')),
is_built boolean default false	
);

create table maintenance(
maintenance_id serial primary key,
site_id int unique references sites(site_id).
total_amount numeric(10,2) not null,
paid_a
)

select * from maintenance;

ALTER TABLE maintenance
DROP COLUMN pending_amount;

SELECT s.site_number, m.total_amount, m.paid_amount, (m.total_amount - m.paid_amount) AS pending_amount
FROM maintenance m
JOIN sites s ON m.site_id = s.site_id;