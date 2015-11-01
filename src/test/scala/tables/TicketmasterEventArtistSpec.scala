package tables

import models.ticketmaster.TicketmasterEventArtist
import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class TicketmasterEventArtistSpec extends Specification {

  "TicketmasterEventArtist" should {

    val tea = TicketmasterEventArtist.syntax("tea")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TicketmasterEventArtist.find(null, null)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TicketmasterEventArtist.findBy(sqls.eq(tea.eventId, null))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TicketmasterEventArtist.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TicketmasterEventArtist.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TicketmasterEventArtist.findAllBy(sqls.eq(tea.eventId, null))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TicketmasterEventArtist.countBy(sqls.eq(tea.eventId, null))
      count should be_>(0L)
    }
    "save a record" in new AutoRollback {
      val entity = TicketmasterEventArtist.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TicketmasterEventArtist.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TicketmasterEventArtist.findAll().head
      TicketmasterEventArtist.destroy(entity)
      val shouldBeNone = TicketmasterEventArtist.find(null, null)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TicketmasterEventArtist.findAll()
      entities.foreach(e => TicketmasterEventArtist.destroy(e))
      val batchInserted = TicketmasterEventArtist.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
