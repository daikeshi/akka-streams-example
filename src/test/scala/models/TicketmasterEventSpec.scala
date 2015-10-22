package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import org.joda.time.{DateTime}


class TicketmasterEventSpec extends Specification {

  "TicketmasterEvent" should {

    val te = TicketmasterEvent.syntax("te")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TicketmasterEvent.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TicketmasterEvent.findBy(sqls.eq(te.eventId, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TicketmasterEvent.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TicketmasterEvent.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TicketmasterEvent.findAllBy(sqls.eq(te.eventId, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TicketmasterEvent.countBy(sqls.eq(te.eventId, 1L))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = TicketmasterEvent.create(eventId = 1L, ticketmasterEventId = "MyString", name = "MyString", eventDate = DateTime.now)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = TicketmasterEvent.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TicketmasterEvent.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TicketmasterEvent.findAll().head
      TicketmasterEvent.destroy(entity)
      val shouldBeNone = TicketmasterEvent.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TicketmasterEvent.findAll()
      entities.foreach(e => TicketmasterEvent.destroy(e))
      val batchInserted = TicketmasterEvent.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
