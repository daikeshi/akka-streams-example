package ticketmaster

import org.joda.time.DateTime
import scalikejdbc._

case class TicketmasterResponse(
  details: TicketmasterResultDetails,
  results: List[TicketmasterEventRecord]
)

case class TicketmasterResultDetails(
  totalResults: Int,
  totalPages: Int,
  currentPage: Int,
  resultsPerPage: Int
)

case class TicketmasterEventRecord(
  event: TicketmasterEvent,
  artists: List[TicketmasterArtist],
  venue: TicketmasterVenue
)

case class TicketmasterEvent(
  eventId: Long,
  ticketmasterEventId: String,
  status: String,
  name: String,
  url: String,
  eventDate: DateTime,
  onSaleDate: DateTime,
  preSaleDate: DateTime,
  category: String,
  categoryId: Int,
  parentCategory: String,
  parentCategoryId: Int,
  minPrice: String,
  maxPrice: String,
  currency: String,
  description: String
)

object TicketmasterEvent extends SQLSyntaxSupport[TicketmasterEvent] {
  override val schemaName = Some("public")
  override val tableName = "ticketmaster_event"
  override val columns = Seq("event_id", "ticketmaster_event_id", "status", "name", "url", "event_date",
    "onsale_date", "presale_date", "category", "category_id", "parent_category", "parent_category_id",
    "min_price", "max_price", "currency", "description", "created_at", "updated_at")
  def apply(r: ResultName[TicketmasterEvent])(rs: WrappedResultSet) =
    TicketmasterEvent(
      rs.long(r.eventId),
      rs.string(r.ticketmasterEventId),
      rs.string(r.status),
      rs.string(r.name),
      rs.string(r.url),
      rs.get(r.eventDate),
      rs.get(r.onSaleDate),
      rs.get(r.preSaleDate),
      rs.string(r.category),
      rs.int(r.categoryId),
      rs.string(r.parentCategory),
      rs.int(r.parentCategoryId),
      rs.string(r.minPrice),
      rs.string(r.maxPrice),
      rs.string(r.currency),
      rs.string(r.description)
    )
}

case class TicketmasterArtist(
  artistId: Long,
  ticketmasterArtistId: Long,
  name: String,
  url: String,
  imageUrl: String,
  category: String,
  categoryId: Int,
  parentCategory: String,
  parentCategoryId: Int
)

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
  latitude: String
)
