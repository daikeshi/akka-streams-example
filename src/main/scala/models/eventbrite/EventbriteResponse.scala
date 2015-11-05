package models.eventbrite

import org.joda.time.{DateTime, DateTimeZone}

case class EventbriteResponse(
  pagination: EventbritePagination,
  results: List[EventbriteEvent]
)

case class EventbritePagination(objectCount: Int, pageNumber: Int, pageSize: Int, pageCount: Int)

case class EventbriteText(text: String, html: String)

case class EventbriteDateTime(timezone: DateTimeZone, local: DateTime, utc: DateTime)

case class EventbriteLogo(id: Long, url: String, aspectRatio: Float, edgeColor: String, edgeColorSet: Boolean)

object EventbriteEvent {
  def addEvent(e: EventbriteEvent) = ???
}

case class EventbriteEvent(
  name: EventbriteText,
  description: EventbriteText,
  id: Long,
  url: String,
  start: EventbriteDateTime,
  end: EventbriteDateTime,
  created: DateTime,
  changed: DateTime,
  capability: Int,
  status: String,
  currency: String,
  listed: Boolean,
  shareable: Boolean,
  onlineEvent: Boolean,
  txTimeLimit: Int,
  hideStartDate: Boolean,
  locale: String,
  logoId: Long,
  organizerId: Long,
  venueId: Long,
  categoryId: Int,
  subcategoryId: Int,
  formatId: Int,
  resourceUri: String,
  logo: EventbriteLogo
)

case class EventbriteOrganizer(
  description: EventbriteText,
  logo: EventbriteLogo,
  resourceUri: String,
  id: Long,
  name: String,
  url: String,
  numPastEvents: Int,
  numFutureEvents: Int
)

case class EventbriteAddress(
  address1: String,
  address2: String,
  city: String,
  region: String,
  postalCode: String,
  country: String,
  latitude: String,
  longitude: String
)

case class EventbriteVenue(
  address: EventbriteAddress,
  resourceUri: String,
  id: Long,
  name: String,
  latitude: String,
  longitude: String
)
