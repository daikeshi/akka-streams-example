package models.ticketmaster

import org.joda.time.DateTime
import scalikejdbc._

case class TicketmasterEvent(
  eventId: Long,
  ticketmasterEventId: String,
  status: String,
  name: String,
  url: String,
  eventDate: Option[DateTime] = None,
  onsaleDate: Option[DateTime] = None,
  presaleDate: Option[DateTime] = None,
  category: String,
  categoryId: Int,
  parentCategory: String,
  parentCategoryId: Int,
  minPrice: String,
  maxPrice: String,
  currency: String,
  description: String) {

  def save()(implicit session: DBSession = TicketmasterEvent.autoSession): TicketmasterEvent = TicketmasterEvent.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterEvent.autoSession): Unit = TicketmasterEvent.destroy(this)(session)
}


object TicketmasterEvent extends SQLSyntaxSupport[TicketmasterEvent] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_event"

  override val columns = Seq("event_id", "ticketmaster_event_id", "status", "name", "url", "event_date", "onsale_date", "presale_date", "category", "category_id", "parent_category", "parent_category_id", "min_price", "max_price", "currency", "description", "created_at", "updated_at")

  def apply(te: SyntaxProvider[TicketmasterEvent])(rs: WrappedResultSet): TicketmasterEvent = apply(te.resultName)(rs)
  def apply(te: ResultName[TicketmasterEvent])(rs: WrappedResultSet): TicketmasterEvent = new TicketmasterEvent(
    eventId = rs.get(te.eventId),
    ticketmasterEventId = rs.get(te.ticketmasterEventId),
    status = rs.get(te.status),
    name = rs.get(te.name),
    url = rs.get(te.url),
    eventDate = rs.get(te.eventDate),
    onsaleDate = rs.get(te.onsaleDate),
    presaleDate = rs.get(te.presaleDate),
    category = rs.get(te.category),
    categoryId = rs.get(te.categoryId),
    parentCategory = rs.get(te.parentCategory),
    parentCategoryId = rs.get(te.parentCategoryId),
    minPrice = rs.get(te.minPrice),
    maxPrice = rs.get(te.maxPrice),
    currency = rs.get(te.currency),
    description = rs.get(te.description)
  )

  val te = TicketmasterEvent.syntax("te")

  override val autoSession = AutoSession

  def find(eventId: Long)(implicit session: DBSession = autoSession): Option[TicketmasterEvent] = {
    withSQL {
      select.from(TicketmasterEvent as te).where.eq(te.eventId, eventId)
    }.map(TicketmasterEvent(te.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TicketmasterEvent] = {
    withSQL(select.from(TicketmasterEvent as te)).map(TicketmasterEvent(te.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TicketmasterEvent as te)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TicketmasterEvent] = {
    withSQL {
      select.from(TicketmasterEvent as te).where.append(where)
    }.map(TicketmasterEvent(te.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TicketmasterEvent] = {
    withSQL {
      select.from(TicketmasterEvent as te).where.append(where)
    }.map(TicketmasterEvent(te.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TicketmasterEvent as te).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(entity: TicketmasterEvent)(implicit session: DBSession = autoSession): TicketmasterEvent = {
    withSQL {
      insert.into(TicketmasterEvent).columns(
        column.eventId,
        column.ticketmasterEventId,
        column.status,
        column.name,
        column.url,
        column.eventDate,
        column.onsaleDate,
        column.presaleDate,
        column.category,
        column.categoryId,
        column.parentCategory,
        column.parentCategoryId,
        column.minPrice,
        column.maxPrice,
        column.currency,
        column.description
      ).values(
        entity.eventId,
        entity.ticketmasterEventId,
        entity.status,
        entity.name,
        entity.url,
        entity.eventDate,
        entity.onsaleDate,
        entity.presaleDate,
        entity.category,
        entity.categoryId,
        entity.parentCategory,
        entity.parentCategoryId,
        entity.minPrice,
        entity.maxPrice,
        entity.currency,
        entity.description
      )
    }.update.apply()
    entity
  }

  def merge(entity: TicketmasterEvent)(implicit session: DBSession = autoSession): TicketmasterEvent = {
    val query = s"select merge_ticketmaster_event(" +
      s"'${entity.eventId}', '${entity.ticketmasterEventId}', '${entity.status}', '${entity.name}', '${entity.url}', " +
      s"${if (entity.eventDate.isDefined) s"'${entity.eventDate.get}'" else "null"}," +
      s"${if (entity.onsaleDate.isDefined) s"'${entity.eventDate.get}'" else "null"}," +
      s"${if (entity.presaleDate.isDefined) s"'${entity.eventDate.get}'" else "null"}," +
      s"'${entity.category}', '${entity.categoryId}', '${entity.parentCategory}', '${entity.parentCategoryId}', " +
      s"'${entity.minPrice}', '${entity.maxPrice}', '${entity.currency}', " +
      s"'${if (entity.description == null) "" else entity.description}')"
    SQL(query).execute().apply()
    entity
  }

  def batchInsert(entities: Seq[TicketmasterEvent])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'eventId -> entity.eventId,
        'ticketmasterEventId -> entity.ticketmasterEventId,
        'status -> entity.status,
        'name -> entity.name,
        'url -> entity.url,
        'eventDate -> entity.eventDate,
        'onsaleDate -> entity.onsaleDate,
        'presaleDate -> entity.presaleDate,
        'category -> entity.category,
        'categoryId -> entity.categoryId,
        'parentCategory -> entity.parentCategory,
        'parentCategoryId -> entity.parentCategoryId,
        'minPrice -> entity.minPrice,
        'maxPrice -> entity.maxPrice,
        'currency -> entity.currency,
        'description -> entity.description))
        SQL("""insert into ticketmaster_event(
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
        {eventId},
        {ticketmasterEventId},
        {status},
        {name},
        {url},
        {eventDate},
        {onsaleDate},
        {presaleDate},
        {category},
        {categoryId},
        {parentCategory},
        {parentCategoryId},
        {minPrice},
        {maxPrice},
        {currency},
        {description}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TicketmasterEvent)(implicit session: DBSession = autoSession): TicketmasterEvent = {
    withSQL {
      update(TicketmasterEvent).set(
        column.eventId -> entity.eventId,
        column.ticketmasterEventId -> entity.ticketmasterEventId,
        column.status -> entity.status,
        column.name -> entity.name,
        column.url -> entity.url,
        column.eventDate -> entity.eventDate,
        column.onsaleDate -> entity.onsaleDate,
        column.presaleDate -> entity.presaleDate,
        column.category -> entity.category,
        column.categoryId -> entity.categoryId,
        column.parentCategory -> entity.parentCategory,
        column.parentCategoryId -> entity.parentCategoryId,
        column.minPrice -> entity.minPrice,
        column.maxPrice -> entity.maxPrice,
        column.currency -> entity.currency,
        column.description -> entity.description
      ).where.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterEvent)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterEvent).where.eq(column.eventId, entity.eventId) }.update.apply()
  }


  def addEvent(event: TicketmasterEvent, artists: List[TicketmasterArtist], venue: TicketmasterVenue)
    (implicit session: DBSession = autoSession): Unit = {
    val addEvent = List(
      s"select merge_ticketmaster_event(" +
      s"'${event.eventId}', '${event.ticketmasterEventId}', '${event.status}', '${event.name}', '${event.url}', " +
      s"${if (event.eventDate.isDefined) s"'${event.eventDate.get}'" else "null"}," +
      s"${if (event.onsaleDate.isDefined) s"'${event.eventDate.get}'" else "null"}," +
      s"${if (event.presaleDate.isDefined) s"'${event.eventDate.get}'" else "null"}," +
      s"'${event.category}', '${event.categoryId}', '${event.parentCategory}', '${event.parentCategoryId}', " +
      s"'${event.minPrice}', '${event.maxPrice}', '${event.currency}', " +
      s"'${if (event.description == null) "" else event.description}')"
    )

    val addArtists = artists.map { artist ⇒ s"select merge_ticketmaster_artist(" +
      s"'${artist.artistId}', '${artist.ticketmasterArtistId}', '${artist.name}', '${artist.url}', '${artist.imageUrl}', " +
      s"'${artist.category}', '${artist.categoryId}', '${artist.parentCategory}', '${artist.parentCategoryId}')"
    }
    
    val addVenue = List(
      s"select merge_ticketmaster_venue(" +
      s"'${venue.venueId}', '${venue.ticketmasterVenueId}', '${venue.name}', '${venue.street}', '${venue.city}', " +
      s"'${venue.country}', '${venue.postcode}', '${venue.url}', '${venue.imageUrl}', '${venue.state}', " +
      s"${if (venue.longitude != null) s"'${venue.longitude}'" else "null"}, " +
      s"${if (venue.latitude != null) s"'${venue.latitude}'" else "null"})"
    )

    val addArtistStream = artists.map { artist ⇒
      s"select merge_ticketmaster_event_artist('${event.eventId}', '${artist.artistId}')"
    }

    val addVenueStream = List(s"select merge_ticketmaster_event_venue('${event.eventId}', '${venue.venueId}')")

    NamedDB('default) localTx { implicit session ⇒
      val sqlStatements = addEvent ++ addArtists ++ addVenue ++ addVenueStream ++ addArtistStream
      sqlStatements.foreach { sqlStatement ⇒ SQL(sqlStatement).execute().apply() }
    }
  }

}
