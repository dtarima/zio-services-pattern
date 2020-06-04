package zioservicespattern

import zioservicespattern.core.modder.Modder

package object core {
  type Core = Modder

  trait Error {
    val msg: String
  }

}
