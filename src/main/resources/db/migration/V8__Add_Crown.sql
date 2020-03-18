create sequence crown_sequence start 1 increment 1;
create table crown
(
    id                   int8         not null,
    territory            varchar(500),
    confirmed_today      int8,
    deaths_today         int8,
    recoveries_today     int8,
    confirmed_yesterday  int8,
    deaths_yesterday     int8,
    recoveries_yesterday int8,
    primary key (id)
);
