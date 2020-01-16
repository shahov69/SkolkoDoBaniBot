create sequence setting_sequence start 1 increment 1;
create table setting
(
    id            int8         not null,
    name          varchar(255) not null,
    value         varchar(255),
    default_value varchar(255),
    primary key (id)
);

create sequence banya_sequence start 1 increment 1;
create table banya
(
    id        int8 not null,
    chat_id   int8 not null,
    chat_name varchar(255),
    start     timestamp,
    finish    timestamp,
    picture   varchar(255),
    primary key (id)
);
