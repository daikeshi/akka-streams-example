package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class TicketmasterArtist(
  artistId: Long,
  ticketmasterArtistId: Long,
  name: String,
  url: Option[String] = None,
  imageUrl: Option[String] = None,
  category: Option[String] = None,
  categoryId: Option[Int] = None,
  parentCategory: Option[String] = None,
  parentCategoryId: Option[Int] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = TicketmasterArtist.autoSession): TicketmasterArtist = TicketmasterArtist.save(this)(session)

  def destroy()(implicit session: DBSession = TicketmasterArtist.autoSession): Unit = TicketmasterArtist.destroy(this)(session)

}


object TicketmasterArtist extends SQLSyntaxSupport[TicketmasterArtist] {

  override val schemaName = Some("public")

  override val tableName = "ticketmaster_artist"

  override val columns = Seq("artist_id", "ticketmaster_artist_id", "name", "url", "image_url", "category", "category_id", "parent_category", "parent_category_id", "created_at", "updated_at")

  def apply(ta: SyntaxProvider[TicketmasterArtist])(rs: WrappedResultSet): TicketmasterArtist = apply(ta.resultName)(rs)
  def apply(ta: ResultName[TicketmasterArtist])(rs: WrappedResultSet): TicketmasterArtist = new TicketmasterArtist(
    artistId = rs.get(ta.artistId),
    ticketmasterArtistId = rs.get(ta.ticketmasterArtistId),
    name = rs.get(ta.name),
    url = rs.get(ta.url),
    imageUrl = rs.get(ta.imageUrl),
    category = rs.get(ta.category),
    categoryId = rs.get(ta.categoryId),
    parentCategory = rs.get(ta.parentCategory),
    parentCategoryId = rs.get(ta.parentCategoryId),
    createdAt = rs.get(ta.createdAt),
    updatedAt = rs.get(ta.updatedAt)
  )

  val ta = TicketmasterArtist.syntax("ta")

  override val autoSession = AutoSession

  def find(artistId: Long)(implicit session: DBSession = autoSession): Option[TicketmasterArtist] = {
    withSQL {
      select.from(TicketmasterArtist as ta).where.eq(ta.artistId, artistId)
    }.map(TicketmasterArtist(ta.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TicketmasterArtist] = {
    withSQL(select.from(TicketmasterArtist as ta)).map(TicketmasterArtist(ta.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TicketmasterArtist as ta)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TicketmasterArtist] = {
    withSQL {
      select.from(TicketmasterArtist as ta).where.append(where)
    }.map(TicketmasterArtist(ta.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TicketmasterArtist] = {
    withSQL {
      select.from(TicketmasterArtist as ta).where.append(where)
    }.map(TicketmasterArtist(ta.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TicketmasterArtist as ta).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    artistId: Long,
    ticketmasterArtistId: Long,
    name: String,
    url: Option[String] = None,
    imageUrl: Option[String] = None,
    category: Option[String] = None,
    categoryId: Option[Int] = None,
    parentCategory: Option[String] = None,
    parentCategoryId: Option[Int] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): TicketmasterArtist = {
    withSQL {
      insert.into(TicketmasterArtist).columns(
        column.artistId,
        column.ticketmasterArtistId,
        column.name,
        column.url,
        column.imageUrl,
        column.category,
        column.categoryId,
        column.parentCategory,
        column.parentCategoryId,
        column.createdAt,
        column.updatedAt
      ).values(
        artistId,
        ticketmasterArtistId,
        name,
        url,
        imageUrl,
        category,
        categoryId,
        parentCategory,
        parentCategoryId,
        createdAt,
        updatedAt
      )
    }.update.apply()

    TicketmasterArtist(
      artistId = artistId,
      ticketmasterArtistId = ticketmasterArtistId,
      name = name,
      url = url,
      imageUrl = imageUrl,
      category = category,
      categoryId = categoryId,
      parentCategory = parentCategory,
      parentCategoryId = parentCategoryId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[TicketmasterArtist])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity => 
      Seq(
        'artistId -> entity.artistId,
        'ticketmasterArtistId -> entity.ticketmasterArtistId,
        'name -> entity.name,
        'url -> entity.url,
        'imageUrl -> entity.imageUrl,
        'category -> entity.category,
        'categoryId -> entity.categoryId,
        'parentCategory -> entity.parentCategory,
        'parentCategoryId -> entity.parentCategoryId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
        SQL("""insert into ticketmaster_artist(
        artist_id,
        ticketmaster_artist_id,
        name,
        url,
        image_url,
        category,
        category_id,
        parent_category,
        parent_category_id,
        created_at,
        updated_at
      ) values (
        {artistId},
        {ticketmasterArtistId},
        {name},
        {url},
        {imageUrl},
        {category},
        {categoryId},
        {parentCategory},
        {parentCategoryId},
        {createdAt},
        {updatedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: TicketmasterArtist)(implicit session: DBSession = autoSession): TicketmasterArtist = {
    withSQL {
      update(TicketmasterArtist).set(
        column.artistId -> entity.artistId,
        column.ticketmasterArtistId -> entity.ticketmasterArtistId,
        column.name -> entity.name,
        column.url -> entity.url,
        column.imageUrl -> entity.imageUrl,
        column.category -> entity.category,
        column.categoryId -> entity.categoryId,
        column.parentCategory -> entity.parentCategory,
        column.parentCategoryId -> entity.parentCategoryId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.artistId, entity.artistId)
    }.update.apply()
    entity
  }

  def destroy(entity: TicketmasterArtist)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TicketmasterArtist).where.eq(column.artistId, entity.artistId) }.update.apply()
  }

}
