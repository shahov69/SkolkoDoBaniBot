create sequence crown_sequence start 1 increment 1;
create table crown
(
    id                   int8 not null,
    territory            varchar(500),
    confirmed_today      int4,
    deaths_today         int4,
    recoveries_today     int4,
    confirmed_yesterday  int4,
    deaths_yesterday     int4,
    recoveries_yesterday int4,
    today                bool,
    flag                 bytea,
    primary key (id)
);

insert into setting (id, name, value, default_value)
values (15, 'CROWN_DAY', null, null);