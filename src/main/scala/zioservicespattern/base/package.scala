package zioservicespattern

import zio.clock.Clock
import zio.console.Console

package object base {
  type Base = Clock with Console

  trait BaseServices {
    protected val env: Base
    protected lazy val clockService: Clock.Service = env.get[Clock.Service]
    protected lazy val consoleService: Console.Service = env.get[Console.Service]
  }

}
