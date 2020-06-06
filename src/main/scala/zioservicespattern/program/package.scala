package zioservicespattern

import zio.{Has, IO, URIO, ZIO}
import zioservicespattern.core.Core
import zioservicespattern.program.Program.Service

package object program {
  type Program = Has[Program.Service]

  final case class Error(msg: String)

  object Program {

    trait Service {
      def executeDirect(value: Int): IO[Error, Int]

      def executeProvide(value: Int): IO[Error, Int]

      def executeDependency(value: Int): ZIO[Core, Error, Int]
    }

  }

  def get: URIO[Program, Service] = ZIO.access[Program](_.get)
}
