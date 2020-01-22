create sequence setting_sequence start 1 increment 1;
create table setting
(
    id            int8         not null,
    name          varchar(255) not null,
    value         varchar(10000),
    default_value varchar(10000),
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

create table usr
(
    id         int4 not null,
    city_id    int4,
    first_name varchar(255),
    is_bot     boolean,
    lang_code  varchar(255),
    last_name  varchar(255),
    user_name  varchar(255),
    primary key (id)
);

create table omen
(
    day         int4 not null,
    title       varchar(255),
    all_titles  varchar(2000),
    description varchar(2000),
    omens       varchar(2000),
    names       varchar(2000),
    dreams      varchar(2000),
    talismans   varchar(2000),
    primary key (day)
)