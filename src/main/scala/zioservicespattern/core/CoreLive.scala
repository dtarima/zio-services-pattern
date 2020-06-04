package zioservicespattern.core

import zio.URLayer
import zioservicespattern.base.Base
import zioservicespattern.core.modder.ModderLive

object CoreLive {
  def layer: URLayer[Base, Core] = ModderLive.layer
}
