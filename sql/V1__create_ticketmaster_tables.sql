create table ticketmaster_event(
  event_id bigint not null primary key,
  ticketmaster_event_id varchar(255) not null,
  status varchar(20),
  name varchar(1024) not null,
  url varchar(1024),
  event_date timestamp not null,
  onsale_date timestamp,
  presale_date timestamp,
  category varchar(512),
  category_id int,
  parent_category varchar(512),
  parent_categoryid int,
  min_price numeric(16, 2),
  max_price numeric(16, 2),
  currency varchar(10),
  description text,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);


create table ticketmaster_artist(
  artist_id bigint not null primary key,
  ticketmaster_artist_id bigint not null,
  name varchar(512) not null,
  url varchar(1024),
  image_url varchar(1024),
  category varchar(512),
  category_id int,
  parent_category varchar(512),
  parent_category_id int,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);

create table ticketmaster_venue(
  venue_id bigint not null primary key,
  ticketmaster_venue_id bigint not null,
  name varchar(512) not null,
  street varchar(1024),
  city varchar(256),
  country varchar(10),
  postcode varchar(10),
  url varchar(1024),
  image_url varchar(1024),
  state varchar(5),
  longitude float,
  latitude float,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);

create table ticketmaster_event_artist(
  event_id bigint references ticketmaster_event,
  artist_id bigint references ticketmaster_artist
);

create table ticketmaster_event_venue(
  event_id bigint references ticketmaster_event,
  venue_id bigint references ticketmaster_venue
);
