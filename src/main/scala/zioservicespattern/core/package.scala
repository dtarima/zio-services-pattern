package zioservicespattern

import zioservicespattern.core.modder.{Modder, ModderService}

package object core {
  type Core = Modder

  trait Error {
    val msg: String
  }

  trait CoreServices extends ModderService

}
