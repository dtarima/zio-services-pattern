package zioservicespattern.services

import zio._
import zio.console.Console
import zioservicespattern.base

object PrinterLive {
  val returnValue = "printer live"
  val consoleOutput = "printer live: call"

  val layer: URLayer[base.Env, Printer.Env] =
    ZLayer.identity[base.Env] map (deps => Has(new Service(ZLayer.succeedMany(deps))))

  class Service(layer: ULayer[base.Env]) extends Printer.Service {
    def call(): IO[Printer.Error, String] = PrinterLive.call.provideLayer(layer)
  }

  private val call: ZIO[Console, Printer.Error, String] =
    zio.console.putStrLn(consoleOutput) *> ZIO.succeed(returnValue)
}
