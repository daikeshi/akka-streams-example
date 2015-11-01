package models.ticketmaster

import org.joda.time.DateTime

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
  eventId: Long,
  ticketmasterEventId: String,
  status: String,
  name: String,
  url: String,
  eventDate: Option[DateTime],
  onsaleDate: Option[DateTime],
  presaleDate: Option[DateTime],
  category: String,
  categoryId: Int,
  parentCategory: String,
  parentCategoryId: Int,
  minPrice: String,
  maxPrice: String,
  currency: String,
  description: String,
  artists: List[TicketmasterArtist],
  venue: TicketmasterVenue
) {
  def event = TicketmasterEvent(
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
    parentCategoryId,
    minPrice,
    maxPrice,
    currency,
    description)
}
