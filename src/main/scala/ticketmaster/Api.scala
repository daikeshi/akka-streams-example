package ticketmaster

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Api extends App {
  val userAgent= Map("User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36")
  val commonReqParaMap = Map(
    "apiKey" -> "17b528e219698770b8e4ab2713c74df6",
    "country" -> "US",
    "filter.venue.state" -> "NY",
    "resultsPerPage" -> "1"
  )

  import dispatch._
  import Implicits._

  def requestUrl =
    url("http://ticketmaster.productserve.com/v3/event").GET <<? commonReqParaMap ++ Map("currentPage" -> "1") <:< userAgent

  def listNewYorkEvent(implicit ec: ExecutionContext) = {
    val response = Http(requestUrl OK as.json4s.Json).map{ json =>
      json.extract[TicketMasterResponse]
    }
    response onComplete {
      case Success(res) => println(res)
      case Failure(e) => println("An error has occured: " + e.getMessage)
    }

//    println(page.url)
//    Http(page OK dispatch.as.xml.Elem).map { xml =>
//      println(xml)
//
//      val seq = for {
//        elem <- xml \\ "data"
//        attr <- elem.attribute("url")
//      } yield attr.toString
//      seq
//    }
  }


  implicit val ec = ExecutionContext.Implicits.global
  listNewYorkEvent
}


//def ticketmasterTest():
//print "***Starting Ticketmaster API Test***\n"
//ticketmaster_req_url = "http://ticketmaster.productserve.com/v3/event?apiKey=17b528e219698770b8e4ab2713c74df6&country=US&filter.venue.state=NY&resultsPerPage=100&currentPage=1"
//ticketmaster_r = requests.get(ticketmaster_req_url).json
//try:
//ticketmaster_num_of_events = len(ticketmaster_r['results'])
//print "CLEAR Ticketmaster has " + str(ticketmaster_num_of_events) + " events on the first request"
//except KeyError:
//  print "ERROR: N0 'results' KEY FOUND"
//print "Check the following URL for more information:\n" + ticketmaster_req_url
//print "done with Ticketmaster\n"