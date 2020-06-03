package zioservicespattern.middle

import zio.{Has, IO, URIO, ZIO}
import zioservicespattern.middle

package object printer {
  type Printer = Has[Printer.Service]

  final case class Error(msg: String) extends middle.Error

  object Printer {

    trait Service extends Serializable {
      def print(message: String): IO[Error, Unit]
    }

  }

  def service: URIO[Printer, Printer.Service] = ZIO.access[Printer](_.get)
}
