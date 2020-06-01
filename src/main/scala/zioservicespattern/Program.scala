package zioservicespattern

import zio.ZIO
import zioservicespattern.services.Printer

object Program {
  def execute: ZIO[services.Env, services.Error, String] = Printer.call()
}
