package zioservicespattern.middle

import zio.URLayer
import zioservicespattern.base.Base
import zioservicespattern.middle.modder.ModderLive

object MiddleLive {
  def layer: URLayer[Base, Middle] = ModderLive.layer
}
