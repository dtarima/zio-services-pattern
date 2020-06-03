package zioservicespattern

import zio.{Has, IO, URIO, ZIO}

package object program {
  type Program = Has[Service]

  trait Service {
    def execute(value: Long): IO[middle.Error, Long]
  }

  def service: URIO[Program, Service] = ZIO.access[Program](_.get)
}
