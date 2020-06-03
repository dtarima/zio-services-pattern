package zioservicespattern.middle

import zio.URLayer
import zioservicespattern.base.Base
import zioservicespattern.middle.modder.ModderLive
import zioservicespattern.middle.printer.PrinterLive

object MiddleLive {
  def layer: URLayer[Base, Middle] = PrinterLive.layer ++ ModderLive.layer
}
