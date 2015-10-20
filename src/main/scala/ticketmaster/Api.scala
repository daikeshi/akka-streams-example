package ticketmaster

import dispatch._

import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}

object TicketmasterConstant {
  final val header= Map(
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36",
    "Accept" -> "application/json"
  )

  final val commonReqParaMap = Map(
    "apiKey" -> "17b528e219698770b8e4ab2713c74df6",
    "country" -> "US",
    "filter.venue.state" -> "NY",
    "filter.venue.city" -> "New York"
  )

  final val baseUrl = "http://ticketmaster.productserve.com/v3/event"
}

object Api {
  import ticketmaster.Implicits._
  import ticketmaster.TicketmasterConstant._

  def buildParameterMap(parameters: Map[String, String]) = commonReqParaMap ++ parameters

  def buildRequest(parameters: Map[String, String], header: Map[String, String]): Req = {
    url(baseUrl).GET <<? buildParameterMap(parameters) <:< header
  }

  def buildRequest(curPage: Int, resultsPerPage: Int): Req = buildRequest(
    Map("currentPage" -> curPage.toString, "resultsPerPage" -> resultsPerPage.toString),
    header
  )

  def queryTicketmaster(): Unit = {
    implicit val ec = ExecutionContext.Implicits.global

    val request = buildRequest(1, 10)
    val response = Http(request OK as.json4s.Json).map(json ⇒ json.extract[TicketmasterResponse])

    response onComplete {
      case Success(res) => {
        val results = res.results
        //logging to
        println(results)
      }
      case Failure(e) => {
        println("An error has occurred: " + e.getMessage)
      }
    }
//    val results = response().results
//    println(results)
  }

  def main(args: Array[String]) = {
    queryTicketmaster()
  }
}
