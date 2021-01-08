CREATE DATABASE IF NOT EXISTS `firstDB`;

create table user (
    user_id INTEGER PRIMARY key autoincrement not null,
    login TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL
);

insert into user (login,password,name) values ('Pavel','pass','pavel');