package models.ticketmaster

import scalikejdbc._

case class TicketmasterVenue(
  venueId: Long,
  ticketmasterVenueId: Long,
  name: String,
  street: String,
  city: String,
  country: String,
  postcode: String,
  url: String,
  imageUrl: String,
  state: String,
  longitude: String,
  latitude: String) {

  def save()(implicit session: DBSession = TicketmasterVenue.autoSession): TicketmasterVenue = TicketmasterVenue.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterVenue.autoSession): Unit = TicketmasterVenue.destroy(this)(session)
}


object TicketmasterVenue extends SQLSyntaxSupport[TicketmasterVenue] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_venue"

  override val columns = Seq("venue_id", "ticketmaster_venue_id", "name", "street", "city", "country", "postcode", "url", "image_url", "state", "longitude", "latitude", "created_at", "updated_at")

  def apply(tv: SyntaxProvider[TicketmasterVenue])(rs: WrappedResultSet): TicketmasterVenue = apply(tv.resultName)(rs)
  def apply(tv: ResultName[TicketmasterVenue])(rs: WrappedResultSet): TicketmasterVenue = new TicketmasterVenue(
    venueId = rs.get(tv.venueId),
    ticketmasterVenueId = rs.get(tv.ticketmasterVenueId),
    name = rs.get(tv.name),
    street = rs.get(tv.street),
    city = rs.get(tv.city),
    country = rs.get(tv.country),
    postcode = rs.get(tv.postcode),
    url = rs.get(tv.url),
    imageUrl = rs.get(tv.imageUrl),
    state = rs.get(tv.state),
    longitude = rs.get(tv.longitude),
    latitude = rs.get(tv.latitude)
  )

  val tv = TicketmasterVenue.syntax("tv")

  override val autoSession = AutoSession

  def find(venueId: Long)(implicit session: DBSession = autoSession): Option[TicketmasterVenue] = {
    withSQL {
      select.from(TicketmasterVenue as tv).where.eq(tv.venueId, venueId)
    }.map(TicketmasterVenue(tv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TicketmasterVenue] = {
    withSQL(select.from(TicketmasterVenue as tv)).map(TicketmasterVenue(tv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TicketmasterVenue as tv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TicketmasterVenue] = {
    withSQL {
      select.from(TicketmasterVenue as tv).where.append(where)
    }.map(TicketmasterVenue(tv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TicketmasterVenue] = {
    withSQL {
      select.from(TicketmasterVenue as tv).where.append(where)
    }.map(TicketmasterVenue(tv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TicketmasterVenue as tv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(entity: TicketmasterVenue)(implicit session: DBSession = autoSession): TicketmasterVenue = {
    withSQL {
      insert.into(TicketmasterVenue).columns(
        column.venueId,
        column.ticketmasterVenueId,
        column.name,
        column.street,
        column.city,
        column.country,
        column.postcode,
        column.url,
        column.imageUrl,
        column.state,
        column.longitude,
        column.latitude
      ).values(
          entity.venueId,
          entity.ticketmasterVenueId,
          entity.name,
          entity.street,
          entity.city,
          entity.country,
          entity.postcode,
          entity.url,
          entity.imageUrl,
          entity.state,
          entity.longitude,
          entity.latitude
        )
    }.update.apply()
    entity
  }

  def merge(entity: TicketmasterVenue)(implicit session: DBSession = autoSession): TicketmasterVenue = {
    val query = s"select merge_ticketmaster_venue(" +
      s"'${entity.venueId}', '${entity.ticketmasterVenueId}', '${entity.name}', '${entity.street}', '${entity.city}', " +
      s"'${entity.country}', '${entity.postcode}', '${entity.url}', '${entity.imageUrl}', '${entity.state}', " +
      s"${if (entity.longitude != null) s"'${entity.longitude}'" else "null"}, " +
      s"${if (entity.latitude != null) s"'${entity.latitude}'" else "null"})"
    SQL(query).execute().apply()
    entity
  }

  def batchInsert(entities: Seq[TicketmasterVenue])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'venueId -> entity.venueId,
        'ticketmasterVenueId -> entity.ticketmasterVenueId,
        'name -> entity.name,
        'street -> entity.street,
        'city -> entity.city,
        'country -> entity.country,
        'postcode -> entity.postcode,
        'url -> entity.url,
        'imageUrl -> entity.imageUrl,
        'state -> entity.state,
        'longitude -> entity.longitude,
        'latitude -> entity.latitude))
        SQL("""insert into ticketmaster_venue(
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
        latitude,
        created_at,
        updated_at
      ) values (
        {venueId},
        {ticketmasterVenueId},
        {name},
        {street},
        {city},
        {country},
        {postcode},
        {url},
        {imageUrl},
        {state},
        {longitude},
        {latitude}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TicketmasterVenue)(implicit session: DBSession = autoSession): TicketmasterVenue = {
    withSQL {
      update(TicketmasterVenue).set(
        column.venueId -> entity.venueId,
        column.ticketmasterVenueId -> entity.ticketmasterVenueId,
        column.name -> entity.name,
        column.street -> entity.street,
        column.city -> entity.city,
        column.country -> entity.country,
        column.postcode -> entity.postcode,
        column.url -> entity.url,
        column.imageUrl -> entity.imageUrl,
        column.state -> entity.state,
        column.longitude -> entity.longitude,
        column.latitude -> entity.latitude
      ).where.eq(column.venueId, entity.venueId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterVenue)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterVenue).where.eq(column.venueId, entity.venueId) }.update.apply()
  }

}
