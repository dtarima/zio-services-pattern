package zioservicespattern

import zio.{IO, Runtime, URLayer, ZIO}
import zioservicespattern.base.{Base, BaseLive}
import zioservicespattern.middle.MiddleLive
import zioservicespattern.program.{Program, ProgramLive}

object BenchmarkMain {

  val layer: URLayer[Base, Program] =
    (MiddleLive.layer ++ BaseLive.layer) >>> ProgramLive.layer

  val count = 100_000

  def main(args: Array[String]): Unit = {
    println(f"env: ${test(_ executeInEnv _) / count}%6d ns")
    println(f"svc: ${test(_ execute _) / count}%6d ns")
  }

  private def test(computation: (Program.Service, Long) => IO[program.Error, Long]) = {
    val prog: ZIO[Base, program.Error, Long] =
      program.get.provideLayer(layer) >>= { service =>
        ZIO.foldLeft(1 to count)(0L) { case (acc, i) =>
          computation(service, i) map (_ + acc)
        }
      }

    (1 to 20).map { _ =>
      val t = System.nanoTime()
      Runtime.default.unsafeRun(prog)
      System.nanoTime() - t
    }.drop(10).sum / 10
  }
}

