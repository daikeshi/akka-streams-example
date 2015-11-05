create table events
(
  id serial not null,
  title character varying(255) not null,
  description text,
  url text,
  img_url text,
  type character varying(255),
  time_start timestamp with time zone,
  time_end timestamp with time zone,
  on_sale_date timestamp with time zone,
  active boolean,
  source_id character varying(255),
  source_name character varying(255),
  source_img_url text,
  created_at timestamp with time zone not null,
  updated_at timestamp with time zone not null,
  deleted_at timestamp with time zone,
  constraint events_pkey primary key (id),
  constraint source_name_id_key unique (source_name, source_id)
)
with (
  oids=false
);
alter table events
  owner to eventflow;


drop function if exists merge_events(
  varchar(255),  text, text, text, varchar(255), timestamp with time zone, timestamp with time zone, timestamp with time zone,
  boolean, varchar(255), varchar(255), text);
create function merge_events(
   ptitle varchar(255),
   pdescription text,
   purl text,
   pimg_url text,
   ptype varchar(255),
   ptime_start timestamp with time zone,
   ptime_end timestamp with time zone,
   pon_sale_date timestamp with time zone,
   pactive boolean,
   psource_id character varying(255),
   psource_name character varying(255),
   psource_img_url text
  ) returns void as
$$
begin
    loop
        -- first try to update the key
        update events set
          title = ptitle,
          description = pdescription,
          url = purl,
          img_url = pimg_url,
          type = ptype,
          time_start = ptime_start,
          time_end = ptime_end,
          on_sale_date = pon_sale_date,
          active = pactive,
          source_img_url = psource_img_url,
          updated_at = current_timestamp
        where source_id = psource_id and source_name = psource_name;
        if found then
            return;
        end if;
        -- not there, so try to insert the key
        -- if someone else inserts the same key concurrently,
        -- we could get a unique-key failure
        begin
            insert into ticketmaster_event(
              title,
              description,
              url,
              img_url,
              type,
              time_start,
              time_end,
              on_sale_date,
              active,
              source_id,
              source_name,
              source_img_url,
              created_at,
              updated_at
            ) values (
              ptitle,
              pdescription,
              purl,
              pimg_url,
              ptype,
              ptime_start,
              ptime_end,
              pon_sale_date,
              pactive,
              psource_id,
              psource_name,
              psource_img_url,
              current_timestamp,
              current_timestamp
            );
            return;
        exception when unique_violation then
            -- do nothing, and loop to try the update again
        end;
    end loop;
end;
$$
language plpgsql;

