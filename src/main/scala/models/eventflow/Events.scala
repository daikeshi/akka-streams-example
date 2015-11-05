package models.eventflow

import org.joda.time.DateTime
import scalikejdbc._

case class Events(
  id: Int,
  title: String,
  description: String,
  url: String,
  imgUrl: String,
  `type`: String,
  timeStart: Option[DateTime] = None,
  timeEnd: Option[DateTime] = None,
  onSaleDate: Option[DateTime] = None,
  active: Boolean,
  sourceId: String,
  sourceName: String,
  sourceImgUrl: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  deletedAt: DateTime) {

  def save()(implicit session: DBSession = Events.autoSession): Events = Events.save(this)(session)

  def destroy()(implicit session: DBSession = Events.autoSession): Unit = Events.destroy(this)(session)
}

object Events extends SQLSyntaxSupport[Events] {

  override val schemaName = Some("public")

  override val tableName = "events"

  override val columns = Seq("id", "title", "description", "url", "img_url", "type", "time_start", "time_end", "on_sale_date", "active", "source_id", "source_name", "source_img_url", "created_at", "updated_at", "deleted_at")

  def apply(e: SyntaxProvider[Events])(rs: WrappedResultSet): Events = apply(e.resultName)(rs)
  def apply(e: ResultName[Events])(rs: WrappedResultSet): Events = new Events(
    id = rs.get(e.id),
    title = rs.get(e.title),
    description = rs.get(e.description),
    url = rs.get(e.url),
    imgUrl = rs.get(e.imgUrl),
    `type` = rs.get(e.`type`),
    timeStart = rs.get(e.timeStart),
    timeEnd = rs.get(e.timeEnd),
    onSaleDate = rs.get(e.onSaleDate),
    active = rs.get(e.active),
    sourceId = rs.get(e.sourceId),
    sourceName = rs.get(e.sourceName),
    sourceImgUrl = rs.get(e.sourceImgUrl),
    createdAt = rs.get(e.createdAt),
    updatedAt = rs.get(e.updatedAt),
    deletedAt = rs.get(e.deletedAt)
  )

  val e = Events.syntax("e")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Events] = {
    withSQL {
      select.from(Events as e).where.eq(e.id, id)
    }.map(Events(e.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Events] = {
    withSQL(select.from(Events as e)).map(Events(e.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Events as e)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Events] = {
    withSQL {
      select.from(Events as e).where.append(where)
    }.map(Events(e.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Events] = {
    withSQL {
      select.from(Events as e).where.append(where)
    }.map(Events(e.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Events as e).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    description: String,
    url: String,
    imgUrl: String,
    `type`: String,
    timeStart: Option[DateTime] = None,
    timeEnd: Option[DateTime] = None,
    onSaleDate: Option[DateTime] = None,
    active: Boolean,
    sourceId: String,
    sourceName: String,
    sourceImgUrl: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    deletedAt: DateTime)(implicit session: DBSession = autoSession): Events = {
    val generatedKey = withSQL {
      insert.into(Events).columns(
        column.title,
        column.description,
        column.url,
        column.imgUrl,
        column.`type`,
        column.timeStart,
        column.timeEnd,
        column.onSaleDate,
        column.active,
        column.sourceId,
        column.sourceName,
        column.sourceImgUrl,
        column.createdAt,
        column.updatedAt,
        column.deletedAt
      ).values(
        title,
        description,
        url,
        imgUrl,
        `type`,
        timeStart,
        timeEnd,
        onSaleDate,
        active,
        sourceId,
        sourceName,
        sourceImgUrl,
        createdAt,
        updatedAt,
        deletedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Events(
      id = generatedKey.toInt,
      title = title,
      description = description,
      url = url,
      imgUrl = imgUrl,
      `type` = `type`,
      timeStart = timeStart,
      timeEnd = timeEnd,
      onSaleDate = onSaleDate,
      active = active,
      sourceId = sourceId,
      sourceName = sourceName,
      sourceImgUrl = sourceImgUrl,
      createdAt = createdAt,
      updatedAt = updatedAt,
      deletedAt = deletedAt)
  }

  def merge(entity: Events)(implicit session: DBSession = autoSession): Events = {
    val query = s"select merge_events(" +
      s"'${entity.title}', '${entity.description}', '${entity.url}', '${entity.imgUrl}', '${entity.`type`}', " +
      s"'${entity.timeStart}', '${entity.timeEnd}', '${entity.onSaleDate}', '${entity.active}', '${entity.sourceId}', " +
      s"'${entity.sourceName}', '${entity.sourceImgUrl}')"
    SQL(query).execute().apply()
    entity
  }

  def batchInsert(entities: Seq[Events])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'title -> entity.title,
        'description -> entity.description,
        'url -> entity.url,
        'imgUrl -> entity.imgUrl,
        'type -> entity.`type`,
        'timeStart -> entity.timeStart,
        'timeEnd -> entity.timeEnd,
        'onSaleDate -> entity.onSaleDate,
        'active -> entity.active,
        'sourceId -> entity.sourceId,
        'sourceName -> entity.sourceName,
        'sourceImgUrl -> entity.sourceImgUrl,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'deletedAt -> entity.deletedAt))
        SQL("""insert into events(
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
        updated_at,
        deleted_at
      ) values (
        {title},
        {description},
        {url},
        {imgUrl},
        {type},
        {timeStart},
        {timeEnd},
        {onSaleDate},
        {active},
        {sourceId},
        {sourceName},
        {sourceImgUrl},
        {createdAt},
        {updatedAt},
        {deletedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: Events)(implicit session: DBSession = autoSession): Events = {
    withSQL {
      update(Events).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.description -> entity.description,
        column.url -> entity.url,
        column.imgUrl -> entity.imgUrl,
        column.`type` -> entity.`type`,
        column.timeStart -> entity.timeStart,
        column.timeEnd -> entity.timeEnd,
        column.onSaleDate -> entity.onSaleDate,
        column.active -> entity.active,
        column.sourceId -> entity.sourceId,
        column.sourceName -> entity.sourceName,
        column.sourceImgUrl -> entity.sourceImgUrl,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.deletedAt -> entity.deletedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Events)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Events).where.eq(column.id, entity.id) }.update.apply()
  }

}
