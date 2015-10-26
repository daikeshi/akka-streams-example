package models

import scalikejdbc._

case class TicketmasterEventArtist(eventId: Long, artistId: Long) {

  def save()(implicit session: DBSession = TicketmasterEventArtist.autoSession): TicketmasterEventArtist = TicketmasterEventArtist.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterEventArtist.autoSession): Unit = TicketmasterEventArtist.destroy(this)(session)

}


object TicketmasterEventArtist extends SQLSyntaxSupport[TicketmasterEventArtist] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_event_artist"

  override val columns = Seq("event_id", "artist_id")

  def apply(tea: SyntaxProvider[TicketmasterEventArtist])(rs: WrappedResultSet): TicketmasterEventArtist = apply(tea.resultName)(rs)
  def apply(tea: ResultName[TicketmasterEventArtist])(rs: WrappedResultSet): TicketmasterEventArtist = new TicketmasterEventArtist(
    eventId = rs.get(tea.eventId),
    artistId = rs.get(tea.artistId)
  )

  val tea = TicketmasterEventArtist.syntax("tea")

  override val autoSession = AutoSession

  def find(eventId: Option[Long], artistId: Option[Long])(implicit session: DBSession = autoSession): Option[TicketmasterEventArtist] = {
    withSQL {
      select.from(TicketmasterEventArtist as tea).where.eq(tea.eventId, eventId).and.eq(tea.artistId, artistId)
    }.map(TicketmasterEventArtist(tea.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TicketmasterEventArtist] = {
    withSQL(select.from(TicketmasterEventArtist as tea)).map(TicketmasterEventArtist(tea.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TicketmasterEventArtist as tea)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TicketmasterEventArtist] = {
    withSQL {
      select.from(TicketmasterEventArtist as tea).where.append(where)
    }.map(TicketmasterEventArtist(tea.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TicketmasterEventArtist] = {
    withSQL {
      select.from(TicketmasterEventArtist as tea).where.append(where)
    }.map(TicketmasterEventArtist(tea.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TicketmasterEventArtist as tea).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(eventId: Long, artistId: Long)(implicit session: DBSession = autoSession): TicketmasterEventArtist = {
    withSQL {
      insert.into(TicketmasterEventArtist).columns(
        column.eventId,
        column.artistId
      ).values(
        eventId,
        artistId
      )
    }.update.apply()

    TicketmasterEventArtist(
      eventId = eventId,
      artistId = artistId)
  }

  def merge(eventId: Long, artistId: Long)(implicit session: DBSession = autoSession): Unit = {
    val query = s"select merge_ticketmaster_event_artist(" +
      s"'$eventId', '$artistId')"
    SQL(query).execute().apply()
  }

  def batchInsert(entities: Seq[TicketmasterEventArtist])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'eventId -> entity.eventId,
        'artistId -> entity.artistId))
        SQL("""insert into ticketmaster_event_artist(
        event_id,
        artist_id
      ) values (
        {eventId},
        {artistId}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TicketmasterEventArtist)(implicit session: DBSession = autoSession): TicketmasterEventArtist = {
    withSQL {
      update(TicketmasterEventArtist).set(
        column.eventId -> entity.eventId,
        column.artistId -> entity.artistId
      ).where.eq(column.eventId, entity.eventId).and.eq(column.artistId, entity.artistId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterEventArtist)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterEventArtist).where.eq(column.eventId, entity.eventId).and.eq(column.artistId, entity.artistId) }.update.apply()
  }

}
