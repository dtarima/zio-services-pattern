package zioservicespattern

import zio.URLayer
import zio.test.Assertion._
import zio.test._
import zio.test.environment.TestEnvironment
import zioservicespattern.base.Base
import zioservicespattern.middle.{Middle, MiddleLive}
import zioservicespattern.program.ProgramLive


object ProgramSpec extends DefaultRunnableSpec {

  val layerMiddleLive: URLayer[Base, Middle] = MiddleLive.layer

  val layerMiddleTest: URLayer[Base, Middle] = MiddleLive.layer ++ ModderTest.layer

  def spec: ZSpec[TestEnvironment, Any] =
    suite("Program")(
      TestUtils.provide(layerMiddleLive >>> ProgramLive.layer,
        testM("a live service works") {
          (program.get >>= (_.execute(10))) map (r => assert(r)(equalTo(3L)))
        }),
      TestUtils.provide(layerMiddleTest >>> ProgramLive.layer,
        testM("a live service can be replaced by a test instance") {
          (program.get >>= (_.execute(10))) map (r => assert(r)(equalTo(4L)))
        })
    )
}
