package zioservicespattern.base

import zio.ULayer
import zio.clock.Clock
import zio.console.Console

object Live {
  val layer: ULayer[Env] = Console.live ++ Clock.live
}
