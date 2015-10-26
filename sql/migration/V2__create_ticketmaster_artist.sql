create table ticketmaster_artist(
  artist_id bigint not null primary key,
  ticketmaster_artist_id varchar(255) not null,
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

create table ticketmaster_event_artist(
  event_id bigint references ticketmaster_event,
  artist_id bigint references ticketmaster_artist,
  primary key (event_id, artist_id)
);

drop function if exists merge_ticketmaster_artist(bigint, varchar(255), varchar(512), varchar(1024), varchar(1024), varchar(512), int, varchar(512),  int);
create function merge_ticketmaster_artist(
   partist_id bigint,
   pticketmaster_artist_id varchar(255),
   pname varchar(512),
   purl varchar(1024),
   pimage_url varchar(1024),
   pcategory varchar(512),
   pcategory_id int,
   pparent_category varchar(512),
   pparent_category_id int
  ) returns void as
$$
begin
    loop
        -- first try to update the key
        update ticketmaster_artist set
          name = pname,
          url = purl,
          image_url = pimage_url,
          category = pcategory,
          category_id = pcategory_id,
          parent_category = pparent_category,
          parent_category_id = pparent_category_id
        where artist_id = partist_id and ticketmaster_artist_id = pticketmaster_artist_id;
        if found then
            return;
        end if;
        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get a unique-key failure
        begin
            insert into ticketmaster_artist(
              artist_id,
              ticketmaster_artist_id,
              name,
              url,
              image_url,
              category,
              category_id,
              parent_category,
              parent_category_id
            ) values (
              partist_id,
              pticketmaster_artist_id,
              pname,
              purl,
              pimage_url,
              pcategory,
              pcategory_id,
              pparent_category,
              pparent_category_id
            );
            return;
        exception when unique_violation then
            -- do nothing, and loop to try the update again
        end;
    end loop;
end;
$$
language plpgsql;

drop function if exists merge_ticketmaster_event_artist(bigint, bigint);
create function merge_ticketmaster_event_artist(
   pevent_id bigint,
   partist_id bigint
  ) returns void as
$$
begin
    loop
        -- first try to update the key
        update ticketmaster_event_artist set
          artist_id = partist_id
        where event_id = pevent_id and artist_id = partist_id;
        if found then
            return;
        end if;
        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get a unique-key failure
        begin
            insert into ticketmaster_event_artist(
              event_id,
              artist_id
            ) values (
              pevent_id,
              partist_id
            );
            return;
        exception when unique_violation then
            -- do nothing, and loop to try the update again
        end;
    end loop;
end;
$$
language plpgsql;
