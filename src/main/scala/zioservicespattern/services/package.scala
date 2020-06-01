package zioservicespattern

package object services {
  type Env = Printer.Env

  trait Error

  case class UnexpectedError(e: Throwable) extends Error

}
