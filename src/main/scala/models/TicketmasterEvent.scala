package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class TicketmasterEvent(
  eventId: Long,
  ticketmasterEventId: String,
  status: Option[String] = None,
  name: String,
  url: Option[String] = None,
  eventDate: DateTime,
  onsaleDate: Option[DateTime] = None,
  presaleDate: Option[DateTime] = None,
  category: Option[String] = None,
  categoryId: Option[Int] = None,
  parentCategory: Option[String] = None,
  parentCategoryid: Option[Int] = None,
  minPrice: Option[BigDecimal] = None,
  maxPrice: Option[BigDecimal] = None,
  currency: Option[String] = None,
  description: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = TicketmasterEvent.autoSession): TicketmasterEvent = TicketmasterEvent.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterEvent.autoSession): Unit = TicketmasterEvent.destroy(this)(session)

}


object TicketmasterEvent extends SQLSyntaxSupport[TicketmasterEvent] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_event"

  override val columns = Seq("event_id", "ticketmaster_event_id", "status", "name", "url", "event_date", "onsale_date", "presale_date", "category", "category_id", "parent_category", "parent_categoryid", "min_price", "max_price", "currency", "description", "created_at", "updated_at")

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
    parentCategoryid = rs.get(te.parentCategoryid),
    minPrice = rs.get(te.minPrice),
    maxPrice = rs.get(te.maxPrice),
    currency = rs.get(te.currency),
    description = rs.get(te.description),
    createdAt = rs.get(te.createdAt),
    updatedAt = rs.get(te.updatedAt)
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

  def create(
    eventId: Long,
    ticketmasterEventId: String,
    status: Option[String] = None,
    name: String,
    url: Option[String] = None,
    eventDate: DateTime,
    onsaleDate: Option[DateTime] = None,
    presaleDate: Option[DateTime] = None,
    category: Option[String] = None,
    categoryId: Option[Int] = None,
    parentCategory: Option[String] = None,
    parentCategoryid: Option[Int] = None,
    minPrice: Option[BigDecimal] = None,
    maxPrice: Option[BigDecimal] = None,
    currency: Option[String] = None,
    description: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): TicketmasterEvent = {
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
        column.parentCategoryid,
        column.minPrice,
        column.maxPrice,
        column.currency,
        column.description,
        column.createdAt,
        column.updatedAt
      ).values(
        eventId,
        ticketmasterEventId,
        status,
        name,
        url,
        eventDate,
        onsaleDate,
        presaleDate,
        category,
        categoryId,
        parentCategory,
        parentCategoryid,
        minPrice,
        maxPrice,
        currency,
        description,
        createdAt,
        updatedAt
      )
    }.update.apply()

    TicketmasterEvent(
      eventId = eventId,
      ticketmasterEventId = ticketmasterEventId,
      status = status,
      name = name,
      url = url,
      eventDate = eventDate,
      onsaleDate = onsaleDate,
      presaleDate = presaleDate,
      category = category,
      categoryId = categoryId,
      parentCategory = parentCategory,
      parentCategoryid = parentCategoryid,
      minPrice = minPrice,
      maxPrice = maxPrice,
      currency = currency,
      description = description,
      createdAt = createdAt,
      updatedAt = updatedAt)
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
        'parentCategoryid -> entity.parentCategoryid,
        'minPrice -> entity.minPrice,
        'maxPrice -> entity.maxPrice,
        'currency -> entity.currency,
        'description -> entity.description,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
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
        parent_categoryid,
        min_price,
        max_price,
        currency,
        description,
        created_at,
        updated_at
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
        {parentCategoryid},
        {minPrice},
        {maxPrice},
        {currency},
        {description},
        {createdAt},
        {updatedAt}
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
        column.parentCategoryid -> entity.parentCategoryid,
        column.minPrice -> entity.minPrice,
        column.maxPrice -> entity.maxPrice,
        column.currency -> entity.currency,
        column.description -> entity.description,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterEvent)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterEvent).where.eq(column.eventId, entity.eventId) }.update.apply()
  }

}
