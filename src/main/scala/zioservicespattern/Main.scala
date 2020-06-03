package zioservicespattern

import zio.{Runtime, ZIO}
import zioservicespattern.base.{Base, BaseLive}
import zioservicespattern.middle.MiddleLive
import zioservicespattern.program.ProgramLive

object Main {
  val layer = (MiddleLive.layer ++ BaseLive.layer) >>> ProgramLive.layer

  def main(args: Array[String]): Unit = {
    val prog: ZIO[Base, middle.Error, Long] =
      program.service.provideLayer(layer) >>= (_.execute(10))
    Runtime.default.unsafeRun(prog)
  }
}
