package zioservicespattern.core

import zio.{Has, IO, ZIO}
import zioservicespattern.core

package object modder {
  type Modder = Has[Modder.Service]

  final case class Error(msg: String) extends core.Error

  object Modder {

    trait Service extends Serializable {
      def mod(value: Long, det: Int): IO[Error, Long]
    }

  }

  def mod(value: Long, det: Int): ZIO[Modder, Error, Long] =
    ZIO.accessM(_.get.mod(value, det))
}
