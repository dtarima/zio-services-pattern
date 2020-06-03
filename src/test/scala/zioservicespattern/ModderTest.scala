package zioservicespattern

import zio._
import zio.console.Console
import zioservicespattern.middle.modder.{Error, Modder}

object ModderTest {
  val layer: URLayer[Console, Modder] = ZLayer.fromService(Service)

  case class Service(console: Console.Service) extends Modder.Service {
    def mod(value: Long, det: Int): IO[Error, Long] =
      console.putStrLn("test modder") *> ZIO.succeed(value % det)
  }

}
