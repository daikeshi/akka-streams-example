package api

import dispatch._

import scala.concurrent.ExecutionContext

object EventbriteApi extends EventSourceApi {
  override def baseUrl: String = s"https://www.eventbriteapi.com/v3/events/search/?token=KKT6OBWW7U4KRUA5Q2FH"

  val commonReqParaMap = Map(
    "token" -> "KKT6OBWW7U4KRUA5Q2FH",
    "venue.country" -> "US"
  )

  def buildRequest(state: String, curPage: Int): Req = buildRequest(
    Map("page" -> curPage.toString, "venue.region" → state),
    header
  )

  def queryTicketmaster(request: Req)(implicit ec: ExecutionContext): Future[EventBriteResponse] = {
    Http(request OK as.json4s.Json).map(json ⇒ json.extract[EventBriteResponse])
  }
}
