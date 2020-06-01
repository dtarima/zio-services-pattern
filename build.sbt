name := "zio-services-pattern"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.0-RC20",
  "dev.zio" %% "zio-test" % "1.0.0-RC20",
  "dev.zio" %% "zio-test-sbt" % "1.0.0-RC20" % "test")

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
