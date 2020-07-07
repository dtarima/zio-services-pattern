package zioservicespattern.program

import zio._
import zioservicespattern.core.{Core, CoreServices, modder}

object ProgramLive {
  val layer: URLayer[Core, Program] =
    ZLayer.identity[Core] map (env => Has(new ServiceImpl(env)))

  class ServiceImpl(protected val env: Core) extends Program.Service with CoreServices {

    def executeDirect(value: Int): IO[Error, Int] =
      repeatAndGetLast(value)(mod(value, 7))

    def executeProvide(value: Int): IO[Error, Int] =
      repeatAndGetLast(value)(modder.mod(value, 7)).provide(env)

    def executeDependency(value: Int): ZIO[Core, Error, Int] =
      repeatAndGetLast(value)(modder.mod(value, 7))

    private def repeatAndGetLast[R](value: Int)(f: ZIO[R, modder.Error, Int]) = {
      ZIO.foldLeft(1 to value)(0)((acc, _) => f.map(_ + acc)).bimap(err => Error(err.msg), _ / value)
    }
  }

}
