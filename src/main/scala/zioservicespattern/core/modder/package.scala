package zioservicespattern.core

import zio.{Has, IO, ZIO}
import zioservicespattern.core

package object modder {
  type Modder = Has[Modder.Service]

  final case class Error(msg: String) extends core.Error

  object Modder {

    trait Service extends Serializable {
      def mod(value: Int, d: Int): IO[Error, Int]
    }

  }

  trait ModderService {
    protected val env: Core
    protected lazy val modderService: Modder.Service = env.get[Modder.Service]

    def mod(value: Int, d: Int): IO[Error, Int] = modderService.mod(value, d)
  }

  def mod(value: Int, d: Int): ZIO[Modder, Error, Int] =
    ZIO.accessM(_.get.mod(value, d))
}
