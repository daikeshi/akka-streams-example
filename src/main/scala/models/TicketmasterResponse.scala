package models

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
