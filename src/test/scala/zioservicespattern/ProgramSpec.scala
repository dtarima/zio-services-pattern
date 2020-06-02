package zioservicespattern

import zio.test.Assertion._
import zio.test._
import zio.test.environment.{TestConsole, TestEnvironment}
import zioservicespattern.services.{Printer, PrinterFail, PrinterLive}


object ProgramSpec extends DefaultRunnableSpec {

  def spec: ZSpec[TestEnvironment, Any] =
    suite("Program")(
      ServiceLayerUtils.provide(services.Live.layer(true),
        testM("a live service works") {
          for {
            result <- Program.execute
            output <- TestConsole.output
          } yield {
            assert(result)(equalTo(PrinterLive.returnValue)) &&
              assert(output)(equalTo(Vector(PrinterLive.consoleOutput + f"%n")))
          }
        }),

      ServiceLayerUtils.provide(services.Live.layer(false),
        testM("configurable production environment") {
          for {
            result <- Program.execute.either
            output <- TestConsole.output
          } yield {
            assert(result.left.exists(_ == Printer.Error(PrinterFail.errorMsg)))(equalTo(true)) &&
              assert(output)(equalTo(Vector(PrinterFail.consoleOutput + f"%n")))
          }
        }),

      ServiceLayerUtils.provide(services.Live.layer(false) ++ PrinterTest.layer,
        testM("a live service can be replaced by a test instance") {
          for {
            result <- Program.execute
            output <- TestConsole.output
          } yield {
            assert(result)(equalTo(PrinterTest.returnValue)) &&
              assert(output)(equalTo(Vector(PrinterTest.consoleOutput + f"%n")))
          }
        }
      ))
}
