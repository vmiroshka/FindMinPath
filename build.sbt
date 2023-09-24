ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "ShortPath",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.1",
    libraryDependencies += "co.fs2" %% "fs2-core" % "3.9.2",
    libraryDependencies += "co.fs2" %% "fs2-io" % "3.9.2"
  )
