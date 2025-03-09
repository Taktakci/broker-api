create table CUSTOMER (
  id long auto_increment,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL
);

insert into CUSTOMER
  (username, password, role )
values
  -- https://bcrypt-generator.com/
  ('user1', '$2a$12$DZz2wL6nULDeBpGrZ0/M0ey0couGuQGFKB.tdBVkx.iYCRRLzgc.a', 'USER'),  -- userpass
  ('admin', '$2a$12$Z6LpWw3IEqIhEgv6zpbwMOsq5irL.fzo9PV4us39x2HCBSP7eWk86', 'USER,ADMIN')  -- adminpass
;


create table ASSET (
  id int auto_increment,
  customer_id int NOT NULL,
  asset_name varchar(255) NOT NULL,
  size int NOT NULL,
  usable_size int NOT NULL,

  CONSTRAINT customer_asset_unique_constraint
    UNIQUE (customer_id, asset_name)
);

insert into ASSET
  (customer_id, asset_name, size, usable_size )
values
  (1, 'TRY', 200, 200),
  (1, 'stock1', 5, 5),
  (1, 'stock2', 15, 15),
  (1, 'stock3', 40, 40),
  (2, 'TRY', 500, 500),
  (2, 'stock2', 10, 10),
  (2, 'stock4', 20, 20)
;


create table STOCK_ORDER (
  id int auto_increment,
  customer_id int NOT NULL,
  asset_name varchar(255) NOT NULL,
  order_side varchar(255) NOT NULL,
  size int NOT NULL,
  price int NOT NULL,
  status varchar(255) NOT NULL,
  create_date datetime NOT NULL
);

insert into STOCK_ORDER
  (customer_id, asset_name, order_side, size, price, status, create_date )
values
  (1, 'stock10', 'BUY', 5, 200, 'PENDING', '2025-02-05'),
  (1, 'stock11', 'SELL', 5, 200, 'PENDING', CURRENT_DATE()),
  (2, 'stock12', 'SELL', 5, 200, 'PENDING', CURRENT_DATE())
;



