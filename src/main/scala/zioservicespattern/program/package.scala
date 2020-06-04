package zioservicespattern

import zio.{Has, IO, URIO, ZIO}
import zioservicespattern.program.Program.Service

package object program {
  type Program = Has[Program.Service]

  final case class Error(msg: String)

  object Program {

    trait Service {
      def execute(value: Long): IO[Error, Long]

      def executeInEnv(value: Long): IO[Error, Long]
    }

  }

  def get: URIO[Program, Service] = ZIO.access[Program](_.get)
}
