package com.themillhousegroup.gasket

import com.google.gdata.data.spreadsheet.ListEntry
import com.themillhousegroup.gasket.traits.ScalaEntry

case class Row(val parent: Worksheet, protected val entry: ListEntry) extends ScalaEntry[ListEntry] {

}
