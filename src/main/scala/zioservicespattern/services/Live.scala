package zioservicespattern.services

import zio.URLayer
import zioservicespattern.base

object Live {
  def layer(live: Boolean): URLayer[base.Env, Env] =
    if (live) PrinterLive.layer else PrinterFail.layer
}
