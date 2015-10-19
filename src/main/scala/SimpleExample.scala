import com.pkinsky.RedditAPI

import scala.concurrent.{Future, ExecutionContext}

object SimpleExample extends App {
  import RedditAPI._
  import ExecutionContext.Implicits.global

  def run =
    for {
      subreddits <- popularSubreddits
      linklistings <- Future.sequence(subreddits.map(popularLinks))
      links = linklistings.flatMap(_.links)
      commentListings <- Future.sequence(links.map(popularComments))
      comments = commentListings.flatMap(_.comments)
    } yield comments

  run
}
