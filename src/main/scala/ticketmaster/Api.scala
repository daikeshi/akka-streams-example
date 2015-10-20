package ticketmaster

import dispatch._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object TicketmasterConstant {
  final val header= Map(
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36",
    "Content-Type" -> "application/json"
  )

  final val commonReqParaMap = Map(
    "apiKey" -> "17b528e219698770b8e4ab2713c74df6",
    "country" -> "US"
  )

  final val baseUrl = "http://ticketmaster.productserve.com/v3/event"
}

object Api {
  import ticketmaster.Implicits._
  import ticketmaster.TicketmasterConstant._

  def buildParameterMap(parameters: Map[String, String]) = commonReqParaMap ++ parameters

  def buildRequest(parameters: Map[String, String], userAgent: Map[String, String]): Req = {
    url(baseUrl).GET <<? buildParameterMap(parameters) <:< userAgent
  }

  val requestUrl = buildRequest(
    Map("currentPage" -> "1", "resultsPerPage" -> "1", "filter.venue.state" -> "NY"),
    header
  )

  println(requestUrl.url)

  def getNewYorkEvents(implicit ec: ExecutionContext) = {
    val response = Http(requestUrl OK as.json4s.Json).map { json ⇒ json.extract[TicketmasterResponse] }

    response onComplete {
      case Success(res) => {
        val results = res.results
        println(results)
      }
      case Failure(e) => {
        println("An error has occurred: " + e.getMessage)
      }
    }
  }

  def main(args: Array[String]) = {
    implicit val ec = ExecutionContext.Implicits.global
    getNewYorkEvents
  }
}
