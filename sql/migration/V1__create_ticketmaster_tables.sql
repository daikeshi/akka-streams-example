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
  parent_category_id int,
  min_price varchar(20),
  max_price varchar(20),
  currency varchar(10),
  description text,
  updated_at timestamp default current_timestamp
);

drop function if exists merge_ticketmaster_event(bigint, varchar(255), varchar(20), varchar(1024), varchar(1024), timestamp, timestamp, timestamp, varchar(512), int, varchar(512),  int, varchar(20), varchar(20), varchar(10), text);
create function merge_ticketmaster_event(
   pevent_id bigint,
   pticketmaster_event_id varchar(255),
   pstatus varchar(20),
   pname varchar(1024),
   purl varchar(1024),
   pevent_date timestamp,
   ponsale_date timestamp,
   ppresale_date timestamp,
   pcategory varchar(512),
   pcategory_id int,
   pparent_category varchar(512),
   pparent_category_id int,
   pmin_price varchar(20),
   pmax_price varchar(20),
   pcurrency varchar(10),
   pdescription text
  ) returns void as
$$
begin
    loop
        -- first try to update the key
        update ticketmaster_event set
          status = pstatus,
          name = pname,
          url = purl,
          event_date = pevent_date,
          onsale_date = ponsale_date,
          presale_date = ppresale_date,
          category = pcategory,
          category_id = pcategory_id,
          parent_category = pparent_category,
          parent_category_id = pparent_category_id,
          min_price = pmin_price,
          max_price = pmax_price,
          currency = pcurrency,
          description = pdescription
        where event_id = pevent_id and ticketmaster_event_id = pticketmaster_event_id;
        if found then
            return;
        end if;
        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get a unique-key failure
        begin
            insert into ticketmaster_event(
              event_id,
              ticketmaster_event_id,
              status,
              name,
              url,
              event_date,
              onsale_date,
              presale_date,
              category,
              category_id,
              parent_category,
              parent_category_id,
              min_price,
              max_price,
              currency,
              description
            ) values (
              pevent_id,
              pticketmaster_event_id,
              pstatus,
              pname,
              purl,
              pevent_date,
              ponsale_date,
              ppresale_date,
              pcategory,
              pcategory_id,
              pparent_category,
              pparent_category_id,
              pmin_price,
              pmax_price,
              pcurrency,
              pdescription
            );
            return;
        exception when unique_violation then
            -- do nothing, and loop to try the update again
        end;
    end loop;
end;
$$
language plpgsql;
