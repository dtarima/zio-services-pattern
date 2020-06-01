package zioservicespattern.services

import zio.{Has, IO, ZIO}
import zioservicespattern.services

object Printer {
  type Env = Has[Printer.Service]

  case class Error(msg: String) extends services.Error

  trait Service {
    def call(): IO[Error, String]
  }

  def call(): ZIO[Env, Error, String] = ZIO.accessM(_.get.call())
}
