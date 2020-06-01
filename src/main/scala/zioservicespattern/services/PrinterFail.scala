package zioservicespattern.services

import zio._
import zio.console.Console
import zioservicespattern.base

object PrinterFail {
  val errorMsg = "fail"
  val consoleOutput = "printer fail: call"

  val layer: URLayer[base.Env, Printer.Env] =
    ZLayer.identity[base.Env] map (deps => Has(new Service(ZLayer.succeedMany(deps))))

  class Service(layer: ULayer[base.Env]) extends Printer.Service {
    def call(): IO[Printer.Error, String] = PrinterFail.call.provideLayer(layer)
  }

  private val call: ZIO[Console, Printer.Error, String] =
    zio.console.putStrLn(consoleOutput) *> ZIO.fail(Printer.Error(errorMsg))
}
