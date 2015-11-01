package models

import models.ticketmaster.TicketmasterArtist
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}


class TicketmasterArtistSpec extends Specification {

  "TicketmasterArtist" should {

    val ta = TicketmasterArtist.syntax("ta")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TicketmasterArtist.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TicketmasterArtist.findBy(sqls.eq(ta.artistId, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TicketmasterArtist.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TicketmasterArtist.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TicketmasterArtist.findAllBy(sqls.eq(ta.artistId, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TicketmasterArtist.countBy(sqls.eq(ta.artistId, 1L))
      count should be_>(0L)
    }
//    "create new record" in new AutoRollback {
//      val created = TicketmasterArtist.create(artistId = 1L, ticketmasterArtistId = 1L, name = "MyString")
//      created should not beNull
//    }
    "save a record" in new AutoRollback {
      val entity = TicketmasterArtist.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TicketmasterArtist.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TicketmasterArtist.findAll().head
      TicketmasterArtist.destroy(entity)
      val shouldBeNone = TicketmasterArtist.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TicketmasterArtist.findAll()
      entities.foreach(e => TicketmasterArtist.destroy(e))
      val batchInserted = TicketmasterArtist.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
