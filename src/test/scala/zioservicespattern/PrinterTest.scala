package zioservicespattern

import zio._
import zio.console.{Console, putStrLn}
import zioservicespattern.services.Printer

object PrinterTest {
  val returnValue = "printer test"
  val consoleOutput = "printer test: call"

  val layer: URLayer[base.Env, Printer.Env] =
    ZLayer.identity[base.Env] map (deps => Has(new Service(ZLayer.succeedMany(deps))))

  private val call: ZIO[Console, Printer.Error, String] =
    putStrLn(consoleOutput) *> ZIO.succeed(returnValue)

  class Service(layer: ULayer[base.Env]) extends Printer.Service {
    def call(): IO[Printer.Error, String] = PrinterTest.call.provideLayer(layer)
  }

}
