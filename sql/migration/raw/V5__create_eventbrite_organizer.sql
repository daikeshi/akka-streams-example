create table eventbrite_organizer(
  description varchar(1024),
  logo varchar(1024),
  resource_uri varchar(1024),
  id bigint not null primary key,
  name varchar(256),
  url varchar(1024),
  num_past_events int,
  num_future_events int,
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);
