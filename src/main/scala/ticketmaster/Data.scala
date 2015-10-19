package ticketmaster

import org.joda.money.CurrencyUnit
import org.joda.time.DateTime

case class Event(
  eventId: Long,
  ticketMasterEventId: String,
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
  minPrice: Double,
  maxPrice: Double,
  currency: CurrencyUnit,
  description: String,
  artists: List[Artist],
  venue: Venue
)

case class Artist(
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

case class Venue(
  venueId: Long,
  ticketmasterArtistId: Long,
  name: String,
  street: String,
  city: String,
  country: String,
  postcode: String,
  url: String,
  imageUrl: String,
  state: String,
  longitude: Double,
  latitude: Double
)

case class ResultSetDetails(
  totalResults: Int,
  totalPage: Int,
  currentPage: Int,
  resultPerPage: Int
)

case class TicketMasterResponse(
  resultSetDetails: ResultSetDetails,
  results: List[Event]
)
