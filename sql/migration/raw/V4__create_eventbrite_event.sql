create table eventbrite_event(
  name varchar(256) not null,
  description varchar(1024),
  id bigint not null primary key,
  url varchar(1024),
  start_time timestamp,
  end_time timestamp,
  created timestamp,
  changed timestamp,
  capability int,
  status varchar(32),
  currency varchar(16),
  listed boolean,
  shareable boolean,
  online_event boolean,
  tx_time_limit int,
  hide_start_date boolean,
  locale varchar(16),
  logo_id bigint,
  organizer_id bigint references eventbrite_organizer,
  venue_id bigint references eventbrite_venue,
  category_id int,
  subcategory_id int,
  format_id int,
  resource_uri varchar(1024),
  logo varchar(1024),
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);

drop function if exists merge_eventbrite_event(varchar(1024), bigint, url, start_time, );
create function merge_ticketmaster_event(
  pname varchar(256) not null,
  pdescription varchar(1024),
  pid bigint not null primary key,
  purl varchar(1024),
  pstart_time timestamp,
  pend_time timestamp,
  pcreated timestamp,
  pchanged timestamp,
  pcapability int,
  pstatus varchar(32),
  pcurrency varchar(16),
  plisted boolean,
  pshareable boolean,
  ponline_event boolean,
  ptx_time_limit int,
  phide_start_date boolean,
  plocale varchar(16),
  plogo_id bigint,
  porganizer_id bigint references eventbrite_organizer,
  pvenue_id bigint references eventbrite_venue,
  pcategory_id int,
  psubcategory_id int,
  pformat_id int,
  presource_uri varchar(1024),
  plogo varchar(1024)
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
