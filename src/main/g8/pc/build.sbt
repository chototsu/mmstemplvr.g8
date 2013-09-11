name := "$name$PC"

version := "0.1-SNAPSHOT"

organization := "$organization$"

scalaVersion := "$scalaVersion$"

javacOptions in compile ++= Seq("-source", "1.6",  "-target", "1.6")

javacOptions in doc ++= Seq("-source", "1.6")

fork in run := true

resolvers += Resolver.url("mmstestrepo",url("https://raw.github.com/chototsu/testrepo/master/ivy2/")) ( Patterns(false,"[organisation]/[module]/[revision]/[type]s/[artifact].[ext]") )

libraryDependencies += "info.projectkyoto" % "mikumikustudio-lib" % "0.1-SNAPSHOT"

libraryDependencies += "info.projectkyoto" % "mmstestdata" % "0.1-SNAPSHOT"

assemblySettings

