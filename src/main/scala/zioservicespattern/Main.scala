package zioservicespattern

import zio.{Runtime, ULayer, ZIO}

object Main {
  val layer: ULayer[services.Env] = base.Live.layer >>> services.Live.layer(true)

  def main(args: Array[String]): Unit = {
    val program: ZIO[zio.ZEnv, services.Error, String] = Program.execute.provideLayer(layer)
    println(Runtime.default.unsafeRun(program.catchAll(err => ZIO.succeed(s"$err"))))
  }
}
