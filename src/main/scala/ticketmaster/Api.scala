package ticketmaster

import java.io.{File, PrintWriter}
import java.sql.SQLException

import dispatch._
import models.{TicketmasterEvent, TicketmasterEventRecord, TicketmasterResponse}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
import scalikejdbc._
import scalikejdbc.config._

object TicketmasterConstant {
  final val header= Map(
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36",
    "Accept" -> "application/json"
  )

  final val commonReqParaMap = Map(
    "apiKey" -> "17b528e219698770b8e4ab2713c74df6",
    "country" -> "US",
    "filter.venue.state" -> "CT"
//    "filter.venue.city" -> "New York"
  )

  final val baseUrl = "http://ticketmaster.productserve.com/v3/event"
}

object Api {
  import ticketmaster.Implicits._
  import ticketmaster.TicketmasterConstant._

  val logger = LoggerFactory.getLogger(getClass)
  val h = new Http
  DBs.setupAll()

  def buildParameterMap(parameters: Map[String, String]) = commonReqParaMap ++ parameters

  def buildRequest(parameters: Map[String, String], header: Map[String, String]): Req = {
    url(baseUrl).GET <<? buildParameterMap(parameters) <:< header
  }

  def buildRequest(curPage: Int, resultsPerPage: Int): Req = buildRequest(
    Map("currentPage" -> curPage.toString, "resultsPerPage" -> resultsPerPage.toString),
    header
  )

  def queryTicketmaster(request: Req)(implicit ec: ExecutionContext): Future[TicketmasterResponse] = {
    Http(request OK as.json4s.Json).map(json ⇒ json.extract[TicketmasterResponse])
  }

  def writeResults(fileName: String, results: List[TicketmasterEventRecord]) = {
    val pw = new PrintWriter(new File(fileName))
    pw.write(results.mkString("\n"))
    pw.close()
  }

  def insertResults(results: List[TicketmasterEventRecord]) = {
//    val events = results.map(_.event)
//    try {
//      TicketmasterEvent.batchMerge(events)
//    } catch {
//      case e: SQLException ⇒
//        logger.error(s"${e.getNextException.getStackTraceString}\n${e.getNextException.getMessage}\n${e.getNextException.getErrorCode}")
//    }

    results.foreach { record =>
      val event = record.event
//      val artists = record.artists
//      val venue = record.venue
      try {
        TicketmasterEvent.merge(event)
      } catch {
        case e: Exception ⇒
          logger.error(s"${e.getMessage}\n${e.getStackTraceString}")
      }
    }
  }

  def main(args: Array[String]) = {
    implicit val ec = ExecutionContext.Implicits.global

    var response = queryTicketmaster(buildRequest(1, 1))
    val resultDetails = response().details
    val eventNum = resultDetails.totalResults
    logger.info(s"total of $eventNum events to retrieve")
    val pages = math.ceil(eventNum / 100.0).toInt
    Range(1, pages+1).foreach { curPage =>
      response = queryTicketmaster(buildRequest(curPage, 100))
      response onComplete {
        case Success(res) => {
          val results = res.results
          logger.info(s"retrieved ${results.length} events from ticketmaster")
//          writeResults(s"events/$curPage", results)
          insertResults(results)
        }
        case Failure(e) => {
          logger.error(s"${e.getStackTrace.mkString("\n")}\n${e.getMessage}")
        }
      }
    }
  }
}
