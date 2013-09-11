name := "$name$"

organization := "$organization$"

// pick the version of scala you want to use
scalaVersion := "$scalaVersion$"

run <<= run in pc in Compile

