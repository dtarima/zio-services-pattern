package zioservicespattern

import zioservicespattern.core.modder.{Modder, ModderDep}

package object core {
  type Core = Modder

  trait Error {
    val msg: String
  }

  trait CoreDep extends ModderDep

}
