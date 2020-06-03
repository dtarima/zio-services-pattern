package zioservicespattern.middle.printer

import zio.console.Console
import zio.{IO, URLayer, ZLayer}
import zioservicespattern.middle.printer

object PrinterLive {
  val layer: URLayer[Console, Printer] =
    ZLayer.fromService[Console.Service, Printer.Service](Service)

  case class Service(console: Console.Service) extends Printer.Service {
    def print(message: String): IO[printer.Error, Unit] = console.putStrLn(message)
  }

}
