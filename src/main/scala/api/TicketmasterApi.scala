package api

import dispatch._
import models.ticketmaster.{TicketmasterEvent, TicketmasterEventRecord, TicketmasterResponse}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object TicketmasterApi extends EventSourceApi {
  import utils.Implicits._

  val commonReqParaMap = Map(
    "apiKey" -> "17b528e219698770b8e4ab2713c74df6",
    "country" -> "US"
  )

  final val baseUrl = "http://ticketmaster.productserve.com/v3/event"

  def buildRequest(state: String, curPage: Int, resultsPerPage: Int): Req = buildRequest(
    Map("currentPage" -> curPage.toString, "resultsPerPage" -> resultsPerPage.toString, "filter.venue.state" → state),
    header
  )

  def queryTicketmaster(request: Req)(implicit ec: ExecutionContext): Future[TicketmasterResponse] = {
    Http(request OK as.json4s.Json).map(json ⇒ json.extract[TicketmasterResponse])
  }

  def insertResults(results: List[TicketmasterEventRecord]) = {
    var failureCounter = 0
    results.foreach { record =>
      try {
        TicketmasterEvent.addTicketmasterEvent(record.event, record.artists, record.venue)
      } catch {
        case e: Exception ⇒
          failureCounter = failureCounter + 1
          logger.error(s"${e.getMessage}\n${e.getStackTrace.map(_.toString).mkString("\n")}")
      }
    }
    logger.error(s"There are $failureCounter events failed to insert into database")
  }

  def main(args: Array[String]) = {
    implicit val ec = ExecutionContext.Implicits.global
    val states = List("NY", "NJ", "CT")
    states.foreach { state ⇒
      var response = queryTicketmaster(buildRequest(state, 1, 1))
      val resultDetails = response().details
      val eventNum = resultDetails.totalResults
      logger.info(s"total of $eventNum events to retrieve")
      val pages = math.ceil(eventNum / 100.0).toInt
      Range(1, pages+1).foreach { curPage =>
        response = queryTicketmaster(buildRequest(state, curPage, 100))
        response onComplete {
          case Success(res) => {
            val results = res.results
            logger.info(s"retrieved ${results.length} events from ticketmaster")
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
