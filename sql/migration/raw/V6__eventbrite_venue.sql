create table eventbrite_venue(
  street varchar(1024),
  city varchar(256),
  region varchar(64),
  country varchar(256),
  resource_uri varchar(1024),
  id bigint not null primary key,
  name varchar(256),
  latitude float,
  longitude float
);
