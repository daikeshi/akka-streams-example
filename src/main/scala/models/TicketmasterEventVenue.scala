package models

import scalikejdbc._

case class TicketmasterEventVenue(eventId: Long, venueId: Long) {

  def save()(implicit session: DBSession = TicketmasterEventVenue.autoSession): TicketmasterEventVenue = TicketmasterEventVenue.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterEventVenue.autoSession): Unit = TicketmasterEventVenue.destroy(this)(session)

}


object TicketmasterEventVenue extends SQLSyntaxSupport[TicketmasterEventVenue] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_event_venue"

  override val columns = Seq("event_id", "venue_id")

  def apply(tev: SyntaxProvider[TicketmasterEventVenue])(rs: WrappedResultSet): TicketmasterEventVenue = apply(tev.resultName)(rs)
  def apply(tev: ResultName[TicketmasterEventVenue])(rs: WrappedResultSet): TicketmasterEventVenue = new TicketmasterEventVenue(
    eventId = rs.get(tev.eventId),
    venueId = rs.get(tev.venueId)
  )

  val tev = TicketmasterEventVenue.syntax("tev")

  override val autoSession = AutoSession

  def find(eventId: Option[Long], venueId: Option[Long])(implicit session: DBSession = autoSession): Option[TicketmasterEventVenue] = {
    withSQL {
      select.from(TicketmasterEventVenue as tev).where.eq(tev.eventId, eventId).and.eq(tev.venueId, venueId)
    }.map(TicketmasterEventVenue(tev.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TicketmasterEventVenue] = {
    withSQL(select.from(TicketmasterEventVenue as tev)).map(TicketmasterEventVenue(tev.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TicketmasterEventVenue as tev)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TicketmasterEventVenue] = {
    withSQL {
      select.from(TicketmasterEventVenue as tev).where.append(where)
    }.map(TicketmasterEventVenue(tev.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TicketmasterEventVenue] = {
    withSQL {
      select.from(TicketmasterEventVenue as tev).where.append(where)
    }.map(TicketmasterEventVenue(tev.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TicketmasterEventVenue as tev).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(eventId: Long, venueId: Long)(implicit session: DBSession = autoSession): TicketmasterEventVenue = {
    withSQL {
      insert.into(TicketmasterEventVenue).columns(
        column.eventId,
        column.venueId
      ).values(
        eventId,
        venueId
      )
    }.update.apply()

    TicketmasterEventVenue(
      eventId = eventId,
      venueId = venueId)
  }

  def merge(eventId: Long, venueId: Long)(implicit session: DBSession = autoSession): Unit = {
    val query = s"select merge_ticketmaster_event_venue(" +
      s"'$eventId', '$venueId')"
    SQL(query).execute().apply()
  }

  def batchInsert(entities: Seq[TicketmasterEventVenue])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'eventId -> entity.eventId,
        'venueId -> entity.venueId))
        SQL("""insert into ticketmaster_event_venue(
        event_id,
        venue_id
      ) values (
        {eventId},
        {venueId}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TicketmasterEventVenue)(implicit session: DBSession = autoSession): TicketmasterEventVenue = {
    withSQL {
      update(TicketmasterEventVenue).set(
        column.eventId -> entity.eventId,
        column.venueId -> entity.venueId
      ).where.eq(column.eventId, entity.eventId).and.eq(column.venueId, entity.venueId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterEventVenue)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterEventVenue).where.eq(column.eventId, entity.eventId).and.eq(column.venueId, entity.venueId) }.update.apply()
  }

}
