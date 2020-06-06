package zioservicespattern

import izumi.reflect.Tag
import zio.duration._
import zio.test.TestAspect.timeout
import zio.test.environment.TestEnvironment
import zio.test.{Spec, TestFailure, TestSuccess, ZSpec}
import zio.{Has, ZLayer}

object TestUtils {

  def provide[R, R1 <: Has[_], E](
    layer: ZLayer[TestEnvironment, E, R1],
    spec: Spec[R, TestFailure[E], TestSuccess])(
    implicit ev: TestEnvironment with R1 <:< R,
    tag: Tag[R1]): ZSpec[TestEnvironment, Any] =
    spec
      .provideCustomLayer(layer)
      .mapError {
        case failure: TestFailure.Assertion => failure
        case x => TestFailure.fail(x)
      } @@ timeout(10.minutes)
}
