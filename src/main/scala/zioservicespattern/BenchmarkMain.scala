package zioservicespattern

import zio.{Runtime, URLayer, ZIO}
import zioservicespattern.base.{Base, BaseLive}
import zioservicespattern.core.{Core, CoreLive}
import zioservicespattern.program.{Program, ProgramLive}

object BenchmarkMain {

  val layer: URLayer[Base, Program with Core] =
    BaseLive.layer >>> CoreLive.layer >+> ProgramLive.layer

  val methodCallCount = 10_000

  def main(args: Array[String]): Unit = {
    println(" [ average call time in nanos ]")
    println(" direct | provide | dependency")
    (1 to 10).foreach { _ =>
      println(f"${makeCalls(_ executeDirect _) / methodCallCount}%6d" +
        f"  | ${makeCalls(_ executeProvide _) / methodCallCount}%6d" +
        f"  | ${makeCalls(_ executeDependency _) / methodCallCount}%6d")
    }
  }

  private def makeCalls(f: (Program.Service, Int) => ZIO[Core, program.Error, Int]): Long =
    runTest((program.get >>= (svc => ZIO.foreach(1 to methodCallCount)(_ => f(svc, 10))))
      .provideSomeLayer(layer))

  def runTest(prog: ZIO[Base, program.Error, Any]): Long = {
    val runCount = 10
    (1 to runCount).map { _ =>
      val t = System.nanoTime()
      Runtime.default.unsafeRun(prog)
      System.nanoTime() - t
    }.sorted.apply(runCount / 2)
  }
}
