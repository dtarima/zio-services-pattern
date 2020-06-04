package zioservicespattern

import zio.{Has, IO, URIO, ZIO}
import zioservicespattern.core.Core
import zioservicespattern.program.Program.Service

package object program {
  type Program = Has[Program.Service]

  final case class Error(msg: String)

  object Program {

    trait Service {
      def executeDirect(value: Long): IO[Error, Long]

      def executeProvide(value: Long): IO[Error, Long]

      def executeDependency(value: Long): ZIO[Core, Error, Long]
    }

  }

  def get: URIO[Program, Service] = ZIO.access[Program](_.get)
}
