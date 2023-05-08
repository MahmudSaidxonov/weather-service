create table usr (
    id serial primary key not null,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    phone_number varchar(16) not null unique,
    email varchar(128) not null unique,
    password varchar(256) not null,
    role varchar(16) default 'ROLE_USER' not null
);

create table city (
  id serial primary key not null,
  city_name varchar(128) not null unique,
  country varchar(128) not null,
  enabled boolean default true
);

create table weather (
    id serial primary key not null,
    city_id int not null,
    temperature int not null,
    date timestamp default CURRENT_TIMESTAMP not null
);

create table subscription (
  id serial primary key not null,
  user_id int not null,
  city_id int not null
);