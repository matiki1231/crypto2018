name := "Krypto2"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.12.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "0.10"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"