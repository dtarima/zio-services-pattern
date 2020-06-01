package zioservicespattern

import zio.clock.Clock
import zio.console.Console

package object base {
  type Env = Console with Clock
}
