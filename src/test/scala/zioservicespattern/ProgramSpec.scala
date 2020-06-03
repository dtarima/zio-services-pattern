package zioservicespattern

import zio.console.Console
import zio.test.Assertion._
import zio.test._
import zio.test.environment.{TestConsole, TestEnvironment}
import zio.{URLayer, ZLayer}
import zioservicespattern.base.Base
import zioservicespattern.middle.printer.PrinterLive
import zioservicespattern.middle.{Middle, MiddleLive}
import zioservicespattern.program.ProgramLive


object ProgramSpec extends DefaultRunnableSpec {

  val layerMiddleLiveWithConsole: URLayer[Base, Middle with Console] =
    MiddleLive.layer ++ ZLayer.identity[Console]

  val layerMiddleWithModderTest: URLayer[Base, Middle] =
    PrinterLive.layer ++ ModderTest.layer

  val layerMiddleTestWithConsole: URLayer[Base, Middle with Console] =
    layerMiddleWithModderTest ++ ZLayer.identity[Console]

  def spec: ZSpec[TestEnvironment, Any] =
    suite("Program")(
      Utils.provide(layerMiddleLiveWithConsole >>> ProgramLive.layer,
        testM("a live service works") {
          for {
            result <- program.service >>= (_.execute(10))
            output <- TestConsole.output
          } yield {
            assert(result)(equalTo(3L)) &&
              assert(output)(equalTo(Vector("result: 3" + f"%n")))
          }
        }),
      Utils.provide(layerMiddleTestWithConsole >>> ProgramLive.layer,
        testM("a live service can be replaced by a test instance") {
          for {
            result <- program.service >>= (_.execute(10))
            output <- TestConsole.output
          } yield {
            assert(result)(equalTo(3L)) &&
              assert(output)(equalTo(Vector("test modder" + f"%n", "result: 3" + f"%n")))
          }
        })
    )
}
