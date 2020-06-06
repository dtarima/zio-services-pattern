package zioservicespattern

import zio._
import zioservicespattern.core.modder.{Error, Modder}

object ModderTest {
  val layer: ULayer[Modder] = ZLayer.succeed(new Service())

  class Service extends Modder.Service {
    def mod(value: Int, d: Int): IO[Error, Int] = ZIO.succeed((value + 1) % d)
  }

}
