package models

import models.ticketmaster.TicketmasterVenue
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}


class TicketmasterVenueSpec extends Specification {

  "TicketmasterVenue" should {

    val tv = TicketmasterVenue.syntax("tv")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TicketmasterVenue.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TicketmasterVenue.findBy(sqls.eq(tv.venueId, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TicketmasterVenue.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TicketmasterVenue.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TicketmasterVenue.findAllBy(sqls.eq(tv.venueId, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TicketmasterVenue.countBy(sqls.eq(tv.venueId, 1L))
      count should be_>(0L)
    }
//    "create new record" in new AutoRollback {
//      val created = TicketmasterVenue.create(venueId = 1L, ticketmasterVenueId = 1L, name = "MyString")
//      created should not beNull
//    }
    "save a record" in new AutoRollback {
      val entity = TicketmasterVenue.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TicketmasterVenue.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TicketmasterVenue.findAll().head
      TicketmasterVenue.destroy(entity)
      val shouldBeNone = TicketmasterVenue.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TicketmasterVenue.findAll()
      entities.foreach(e => TicketmasterVenue.destroy(e))
      val batchInserted = TicketmasterVenue.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
