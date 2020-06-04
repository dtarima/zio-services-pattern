package zioservicespattern

import zio._
import zioservicespattern.middle.modder.{Error, Modder}

object ModderTest {
  val layer: ULayer[Modder] = ZLayer.succeed(new Service())

  class Service extends Modder.Service {
    def mod(value: Long, det: Int): IO[Error, Long] = ZIO.succeed((value + 1) % det)
  }

}
