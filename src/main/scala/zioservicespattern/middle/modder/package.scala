package zioservicespattern.middle

import zio.{Has, IO, ZIO}
import zioservicespattern.middle

package object modder {
  type Modder = Has[Modder.Service]

  final case class Error(msg: String) extends middle.Error

  object Modder {

    trait Service extends Serializable {
      def mod(value: Long, det: Int): IO[Error, Long]
    }

  }

  def mod(value: Long, det: Int): ZIO[Modder, Error, Long] =
    ZIO.accessM(_.get.mod(value, det))
}
