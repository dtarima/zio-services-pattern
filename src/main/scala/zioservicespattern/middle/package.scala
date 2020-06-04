package zioservicespattern

import zioservicespattern.middle.modder.Modder

package object middle {
  type Middle = Modder

  trait Error {
    val msg: String
  }

}
