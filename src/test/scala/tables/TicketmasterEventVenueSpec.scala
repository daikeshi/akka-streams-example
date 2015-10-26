package tables

import models.TicketmasterEventVenue
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class TicketmasterEventVenueSpec extends Specification {

  "TicketmasterEventVenue" should {

    val tev = TicketmasterEventVenue.syntax("tev")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TicketmasterEventVenue.find(null, null)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TicketmasterEventVenue.findBy(sqls.eq(tev.eventId, null))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TicketmasterEventVenue.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TicketmasterEventVenue.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TicketmasterEventVenue.findAllBy(sqls.eq(tev.eventId, null))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TicketmasterEventVenue.countBy(sqls.eq(tev.eventId, null))
      count should be_>(0L)
    }
    "save a record" in new AutoRollback {
      val entity = TicketmasterEventVenue.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TicketmasterEventVenue.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TicketmasterEventVenue.findAll().head
      TicketmasterEventVenue.destroy(entity)
      val shouldBeNone = TicketmasterEventVenue.find(null, null)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TicketmasterEventVenue.findAll()
      entities.foreach(e => TicketmasterEventVenue.destroy(e))
      val batchInserted = TicketmasterEventVenue.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
