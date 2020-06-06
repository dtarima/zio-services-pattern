### The goal
Develop recommendations for organizing code using ZIO library. Components' interfaces should not have any dependencies to avoid leaking implementation details and to be able to have multiple implementations switchable without affecting clients' code.
### The approach
ZIO's environment `R` is implemented as a map of service instances so environmental accessors (`ZIO.access`) are not free: on each call the service needs to be fetched from the map in runtime. Due to the overhead the common approach to constructing service instances is to use `ZIO.fromServices` passing dependent service instances directly to service constructors. It guarantees the best performance, but when there is a need to call another environmental method the required context would have to be re-created or the dependency specified in the method signature.

The approach presented here passes the environment to service constructors and initializes required services from the map as `lazy vals` in the class achieving both, performance and flexibility. The ability to provide environment might come handy when there is a need to call an external library or a utility method which requires it.

Here is a simplified implementation of a service with a direct call to a dependent service `modderService` and a call to the same service using its environmental accessor `modder.mod`:

~~~Scala
object ProgramLive {
  val layer: URLayer[Core, Program] =
    ZLayer.identity[Core] map (env => Has(new ServiceImpl(env)))

  class ServiceImpl(protected val env: Core) extends Program.Service
      with CoreDep {
    def executeDirect(value: Int): IO[Error, Int] =
      modderSvc.mod(value, 7)

    def executeProvide(value: Int): IO[Error, Int] =
      modder.mod(value, 7).provide(env)
  }
}
~~~

where `CoreDep` is a helper initializing required service instances:

~~~Scala
  trait CoreDep extends ModderDep

  trait ModderDep {
    protected val env: Core
    protected lazy val modderSvc: Modder.Service = env.get[Modder.Service]
  }
~~~

Creating the context using `ZIO.provide` method requires pushing, and later popping, the environment to/from a stack which in my tests takes around 1 microsecond. It's a substantial overhead for a tiny call, but in real program it's not critical. In rare cases we can always choose a non-environmental approach as an optimization.
