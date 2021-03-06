package com.themillhousegroup.gasket.test

import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.google.gdata.data.spreadsheet._
import java.net.URL
import com.google.gdata.data.{ Link, Source, IFeed, PlainTextConstruct }
import com.themillhousegroup.gasket.{ Account, Spreadsheet }
import org.specs2.mock.Mockito
import org.specs2.specification.Scope

trait TestFixtures {
  this: Mockito =>

  def mockCellEntry(row: Int, col: Int, cellValue: Option[String] = None, cellNumericValue: Option[Int] = None) = {
    val ce = mock[CellEntry]
    ce.getTitle returns new PlainTextConstruct(s"row$row, col$col")
    val c = mock[Cell]
    ce.getCell returns c
    c.getCol returns col
    c.getRow returns row
    c.getValue returns cellValue.getOrElse(s"R${row}C${col}")
    c.getNumericValue returns cellNumericValue.getOrElse[Int](0)
    ce
  }

  val fakeCellFeedUrl = new URL("http://localhost/cell")
  val fakeBlockFeedUrl = "http://localhost/cell?min-row="
  val fakeListFeedUrl = new URL("http://localhost/list")
  val fakeWorksheetFeedUrl = new URL("http://localhost/worksheet")

  abstract trait MockScope extends Scope {

    val s1 = mock[SpreadsheetEntry]
    val s2 = mock[SpreadsheetEntry]
    s1.getTitle returns new PlainTextConstruct("one")
    s2.getTitle returns new PlainTextConstruct("two")

    val mockSpreadsheetEntry = mock[SpreadsheetEntry]
    mockSpreadsheetEntry.getWorksheetFeedUrl returns fakeWorksheetFeedUrl

    val mockWorksheetFeed = mock[WorksheetFeed]
    val w1 = mock[WorksheetEntry]
    val w2 = mock[WorksheetEntry]
    w1.getTitle returns new PlainTextConstruct("one")
    w2.getTitle returns new PlainTextConstruct("two")

    mockWorksheetFeed.getEntries returns com.google.common.collect.Lists.newArrayList(w1, w2)

    val mockWorksheetEntry = mock[WorksheetEntry]
    mockWorksheetEntry.getCellFeedUrl returns fakeCellFeedUrl
    mockWorksheetEntry.getListFeedUrl returns fakeListFeedUrl
    mockWorksheetEntry.getTitle returns new PlainTextConstruct("mockWorksheetEntry")
    mockWorksheetEntry.update returns mockWorksheetEntry

    val mockCellFeed = mock[CellFeed]
    val mockEmptyCellFeed = mock[CellFeed]

    val c1 = mockCellEntry(1, 1)
    val c2 = mockCellEntry(1, 2)
    val c3 = mockCellEntry(2, 1)
    val c4 = mockCellEntry(2, 2)

    val mockLink = mock[Link]
    mockLink.getHref returns "http://www.google.com"

    mockCellFeed.getEntries returns com.google.common.collect.Lists.newArrayList(c1, c2, c3, c4)
    mockCellFeed.getLink(anyString, anyString) returns mockLink
    mockWorksheetEntry.getRowCount returns 2
    mockWorksheetEntry.getColCount returns 2

    val mockSpreadsheetFeed = mock[SpreadsheetFeed]
    val mockSpreadsheet = mock[Spreadsheet]
    val mockService: SpreadsheetService // Must be instantiated by subclasses
  }

  trait MockSpreadsheetScope extends MockScope {
    val mockService = mock[SpreadsheetService]
  }
}
