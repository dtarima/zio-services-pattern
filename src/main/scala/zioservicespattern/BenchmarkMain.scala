package zioservicespattern

import zio.{Runtime, URLayer, ZIO}
import zioservicespattern.base.{Base, BaseLive}
import zioservicespattern.core.{Core, CoreLive}
import zioservicespattern.program.{Program, ProgramLive}

object BenchmarkMain {

  val layer: URLayer[Base, Program with Core] =
    BaseLive.layer >>> CoreLive.layer >+> ProgramLive.layer

  val methodCallCount = 100_000

  def main(args: Array[String]): Unit = {
    println("  [ one call time in nanos ]")
    println(" direct | provide | dependency")
    (1 to 10).foreach { _ =>
      println(f"${exec(_ executeDirect _) / methodCallCount}%6d" +
        f"  | ${exec(_ executeProvide _) / methodCallCount}%6d" +
        f"  | ${exec(_ executeDependency _) / methodCallCount}%6d")
    }
  }

  private def exec(computation: (Program.Service, Int) => ZIO[Core, program.Error, Long]): Long = {
    val executeInEnvResult = runTest((for {
      service <- program.get
      result <- ZIO.foldLeft(1 to methodCallCount)(0L) { case (acc, i) =>
        computation(service, i) map (_ + acc)
      }
    } yield result).provideSomeLayer(layer))
    executeInEnvResult
  }

  def runTest(prog: ZIO[Base, program.Error, Long]): Long = {
    val runCount = 10
    (1 to runCount * 2).map { _ =>
      val t = System.nanoTime()
      Runtime.default.unsafeRun(prog)
      System.nanoTime() - t
    }.drop(runCount).sum / runCount
  }
}

