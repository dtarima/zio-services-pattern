package zioservicespattern

import zio.clock.Clock
import zio.console.Console

package object base {
  type Base = Clock with Console

  trait BaseDep {
    protected val env: Base
    protected lazy val clockSvc: Clock.Service = env.get[Clock.Service]
    protected lazy val consoleSvc: Console.Service = env.get[Console.Service]
  }

}
