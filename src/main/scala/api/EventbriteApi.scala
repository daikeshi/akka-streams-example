package api

import dispatch._
import models.eventbrite.{EventbriteEvent, EventbriteResponse}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object EventbriteApi extends SourceApi {
  final val baseUrl = "https://www.eventbriteapi.com/v3"

  final val eventSearchUrl = s"$baseUrl/events/search/"

  def organizerSearchUrl(id: Long) = s"$baseUrl/organizers/$id/"

  def venueSearchUrl(id: Long) = s"$baseUrl/venues/$id/"

  val commonReqParaMap = Map(
    "token" -> "KKT6OBWW7U4KRUA5Q2FH",
    "venue.country" -> "US"
  )

  def buildRequest(state: String, curPage: Int): Req = buildRequest(
    Map("page" -> curPage.toString, "venue.region" → state),
    header
  )

  def insertResults(results: List[EventbriteEvent]) = {
    var failureCounter = 0
    results.foreach { event =>
      try {
        EventbriteEvent.addEvent(event)
      } catch {
        case e: Exception ⇒
          failureCounter = failureCounter + 1
          logger.error(s"${e.getMessage}\n${e.getStackTrace.map(_.toString).mkString("\n")}")
      }
    }
    logger.error(s"There are $failureCounter events failed to insert into database")
  }

  def queryEventbrite(request: Req)(implicit ec: ExecutionContext): Future[EventbriteResponse] = {
    Http(request OK as.json4s.Json).map(json ⇒ json.extract[EventbriteResponse])
  }

  def main(args: Array[String]) = {
    implicit val ec = ExecutionContext.Implicits.global
    val states = List("NY", "NJ", "CT")
    states.foreach { state ⇒
      var response = queryEventbrite(buildRequest(state, 1))
      val resultDetails = response().pagination
      val eventNum = resultDetails.objectCount
      logger.info(s"total of $eventNum events to retrieve")
      val pages = math.ceil(eventNum / resultDetails.pageSize.toDouble).toInt
      Range(1, pages+1).foreach { curPage =>
        response = queryEventbrite(buildRequest(state, curPage))
        response onComplete {
          case Success(res) => {
            val results = res.results
            logger.info(s"retrieved ${results.length} events from eventbrite")
            insertResults(results)
            Thread.sleep(2000)
          }
          case Failure(e) => {
            logger.error(s"${e.getStackTrace.mkString("\n")}\n${e.getMessage}")
          }
        }
      }
    }
  }
}
