package zioservicespattern

import zio.clock.Clock
import zio.console.Console

package object base {
  type Base = Clock with Console
}
