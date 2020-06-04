package zioservicespattern.program

import zio._
import zioservicespattern.core.modder.Modder
import zioservicespattern.core.{Core, modder}

object ProgramLive {
  val layer: URLayer[Core, Program] =
    ZLayer.identity[Core] map (deps => Has(new ServiceImpl(deps)))

  class ServiceImpl(env: Core) extends Program.Service {
    private lazy val modderService = env.get[Modder.Service]

    def executeDirect(value: Long): IO[Error, Long] =
      modderService.mod(value, 7).mapError(err => Error(err.msg))

    def executeProvide(value: Long): IO[Error, Long] =
      modder.mod(value, 7).mapError(err => Error(err.msg)).provide(env)

    def executeDependency(value: Long): ZIO[Core, Error, Long] =
      modder.mod(value, 7).mapError(err => Error(err.msg))
  }

}
