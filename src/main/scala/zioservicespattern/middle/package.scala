package zioservicespattern

import zioservicespattern.middle.modder.Modder
import zioservicespattern.middle.printer.Printer

package object middle {
  type Middle = Printer with Modder

  trait Error

  case class UnexpectedError(e: Throwable) extends Error

}
