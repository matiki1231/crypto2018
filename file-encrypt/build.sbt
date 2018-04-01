name := "file-encrypt"

version := "0.1"

scalaVersion := "2.12.5"

scalacOptions += "-Ypartial-unification"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.12.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "0.10"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.7.0"
libraryDependencies += "com.lihaoyi" %% "fastparse" % "1.0.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"