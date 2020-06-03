package zioservicespattern.program

import zio.console.Console
import zio.{IO, URLayer, ZLayer}
import zioservicespattern.middle
import zioservicespattern.middle.Middle
import zioservicespattern.middle.modder.Modder

object ProgramLive {
  val layer: URLayer[Middle with Console, Program] =
    ZLayer.fromServices[Modder.Service, Console.Service, Service] {
      case (modderService, consoleService) => new ServiceImpl(modderService, consoleService)
    }

  class ServiceImpl(modder: Modder.Service, console: Console.Service) extends Service {
    def execute(value: Long): IO[middle.Error, Long] = for {
      result <- modder.mod(value, 7)
      _ <- console.putStrLn(s"result: $result")
    } yield result
  }

}
