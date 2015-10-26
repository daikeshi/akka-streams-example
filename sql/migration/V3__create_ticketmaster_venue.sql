create table ticketmaster_venue(
  venue_id bigint not null primary key,
  ticketmaster_venue_id varchar(255) not null,
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

create table ticketmaster_event_venue(
  event_id bigint references ticketmaster_event,
  venue_id bigint references ticketmaster_venue
);

drop function if exists merge_ticketmaster_venue(bigint, varchar(255), varchar(512), varchar(1024), varchar(256), varchar(10), varchar(10),  varchar(1024), varchar(1024), varchar(5), float, float)

create function merge_ticketmaster_venue(
   pvenue_id bigint,
   pticketmaster_venue_id varchar(255),
   pname varchar(512),
   pstreet varchar(1024),
   pcity varchar(256),
   pcountry varchar(10),
   postcode varchar(10),
   purl varchar(1024),
   pimage_url varchar(1024),
   pstate varchar(5),
   plongitude float,
   platitude float
  ) returns void as
$$
begin
    loop
        -- first try to update the key
        update ticketmaster_venue set
          name = pname,
          street = pstreet,
          city = pcity,
          country = pcountry,
          postcode = ppostcode,
          url = purl,
          image_url = pimage_url,
          state = pstate,
          longitude = plongitude,
          latitude = platitude
        where venue_id = pvenue_id and ticketmaster_venue_id = pticketmaster_venue_id;
        if found then
            return;
        end if;
        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get a unique-key failure
        begin
            insert into ticketmaster_event(
              venue_id,
              ticketmaster_venue_id,
              name,
              street,
              city,
              country,
              postcode,
              url,
              image_url,
              state,
              longitude,
              latitude
            ) values (
              pvenue_id,
              pticketmaster_venue_id,
              pname,
              pstreet,
              pcity,
              pcountry,
              ppostcode,
              purl,
              pimage_url,
              pstate,
              plongitude,
              platitude
            );
            return;
        exception when unique_violation then
            -- do nothing, and loop to try the update again
        end
    end loop;
end;
$$
language plpgsql;
