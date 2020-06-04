package zioservicespattern.program

import zio._
import zioservicespattern.middle.modder.Modder
import zioservicespattern.middle.{Middle, modder}

object ProgramLive {
  val layer: URLayer[Middle, Program] =
    ZLayer.identity[Middle] map (deps => Has(new ServiceImpl(deps)))

  class ServiceImpl(env: Middle) extends Program.Service {
    private lazy val modderService = env.get[Modder.Service]

    def execute(value: Long): IO[Error, Long] =
      modderService.mod(value, 7).mapError(err => Error(err.msg))

    def executeInEnv(value: Long): IO[Error, Long] =
      modder.mod(value, 7).mapError(err => Error(err.msg)).provide(env)

  }

}
