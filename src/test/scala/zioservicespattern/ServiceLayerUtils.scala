package zioservicespattern

import izumi.reflect.Tag
import zio.ZLayer
import zio.duration._
import zio.test.TestAspect.timeout
import zio.test.environment.TestEnvironment
import zio.test.{Spec, TestFailure, TestSuccess, ZSpec}

object ServiceLayerUtils {

  def provide[R, E](
    layer: ZLayer[base.Env, E, services.Env],
    spec: Spec[R, TestFailure[services.Error], TestSuccess])(
    implicit ev: TestEnvironment with services.Env <:< R,
    tag: Tag[services.Env]): ZSpec[TestEnvironment, Any] =
    spec
      .provideCustomLayer(layer)
      .mapError {
        case failure: TestFailure.Assertion => failure
        case x => TestFailure.fail(x)
      } @@ timeout(10.minutes)
}
