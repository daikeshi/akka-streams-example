package ticketmaster

import org.joda.time.DateTime

case class Event(
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

case class ResultSetDetails(
  totalResults: Int,
  totalPages: Int,
  currentPage: Int,
  resultsPerPage: Int
)

case class TicketMasterResponse(
  details: ResultSetDetails,
  results: List[Event]
)
