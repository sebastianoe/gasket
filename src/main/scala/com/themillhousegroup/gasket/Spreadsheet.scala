package com.themillhousegroup.gasket

import com.google.gdata.data.spreadsheet.{ WorksheetFeed, SpreadsheetEntry }
import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.themillhousegroup.gasket.traits.{ Timing, ScalaEntry }
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Spreadsheet(private val service: SpreadsheetService, val googleEntry: SpreadsheetEntry) extends ScalaEntry[SpreadsheetEntry] with Timing {

  private[this] lazy val worksheetFeed = service.getFeed(googleEntry.getWorksheetFeedUrl(), classOf[WorksheetFeed])

  def worksheets: Future[Map[String, Worksheet]] = Future {
    import scala.collection.JavaConverters._
    time("worksheets fetch", worksheetFeed.getEntries).asScala.map(Worksheet(service, this, _)).map(w => w.title -> w).toMap
  }

}
