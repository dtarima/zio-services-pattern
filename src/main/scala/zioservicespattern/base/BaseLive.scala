package zioservicespattern.base

import zio.ULayer
import zio.clock.Clock
import zio.console.Console

object BaseLive {
  val layer: ULayer[Base] = Console.live ++ Clock.live
}
