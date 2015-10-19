package ticketmaster

import java.util.Date

import org.joda.money.CurrencyUnit

case class Event(
  eventId: Long,
  ticketmasterEventId: String,
  status: String,
  name: String,
  url: String,
  eventDate: Date,
  onSaleDate: Date,
  preSaleDate: Date,
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
