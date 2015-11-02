package api

import dispatch.{Req, url}
import org.slf4j.LoggerFactory
import scalikejdbc.config.DBs

trait EventSourceApi {
  DBs.setupAll()

  val logger = LoggerFactory.getLogger(getClass)

  val header= Map(
    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36",
    "Accept" -> "application/json"
  )

  def baseUrl: String

  def commonReqParaMap: Map[String, String]

  def buildParameterMap(parameters: Map[String, String]) = commonReqParaMap ++ parameters

  def buildRequest(parameters: Map[String, String], header: Map[String, String]): Req = {
    url(baseUrl).GET <<? buildParameterMap(parameters) <:< header
  }
}
