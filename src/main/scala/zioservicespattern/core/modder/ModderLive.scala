package zioservicespattern.core.modder

import zio.{IO, ULayer, ZIO, ZLayer}

object ModderLive {
  val layer: ULayer[Modder] = ZLayer.succeed(new Service())

  class Service extends Modder.Service {
    def mod(value: Int, d: Int): IO[Error, Int] = ZIO.succeed(value % d)
  }

}
