package zioservicespattern

import zio.URLayer
import zio.test.Assertion._
import zio.test._
import zio.test.environment.TestEnvironment
import zioservicespattern.base.Base
import zioservicespattern.core.{Core, CoreLive}
import zioservicespattern.program.ProgramLive


object ProgramSpec extends DefaultRunnableSpec {

  val layerCoreLive: URLayer[Base, Core] = CoreLive.layer

  val layerCoreTest: URLayer[Base, Core] = CoreLive.layer ++ ModderTest.layer

  def spec: ZSpec[TestEnvironment, Any] =
    suite("Program")(
      TestUtils.provide(layerCoreLive >>> ProgramLive.layer,
        testM("a live service works") {
          (program.get >>= (_.executeDirect(10))) map (r => assert(r)(equalTo(3)))
        }),
      TestUtils.provide(layerCoreTest >>> ProgramLive.layer,
        testM("a live service can be replaced by a test instance") {
          (program.get >>= (_.executeDirect(10))) map (r => assert(r)(equalTo(4)))
        })
    )
}
