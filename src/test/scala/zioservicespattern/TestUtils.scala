package zioservicespattern

import izumi.reflect.Tag
import zio.ZLayer
import zio.duration._
import zio.test.TestAspect.timeout
import zio.test.environment.TestEnvironment
import zio.test.{Spec, TestFailure, TestSuccess, ZSpec}
import zioservicespattern.base.Base
import zioservicespattern.program.Program

object TestUtils {

  def provide[R, E](
    layer: ZLayer[Base, E, Program],
    spec: Spec[R, TestFailure[program.Error], TestSuccess])(
    implicit ev: TestEnvironment with Program <:< R,
    tag: Tag[middle.Middle]): ZSpec[TestEnvironment, Any] =
    spec
      .provideCustomLayer(layer)
      .mapError {
        case failure: TestFailure.Assertion => failure
        case x => TestFailure.fail(x)
      } @@ timeout(10.minutes)
}
