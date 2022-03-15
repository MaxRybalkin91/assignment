create table proxy
(
    id bigserial primary key,
    name varchar(120) not null,
    type varchar not null,
    hostname varchar(120) not null,
    port integer not null,
    username text not null,
    password text not null,
    active boolean not null
);