name := "untitled2"

version := "0.1"

scalaVersion := "2.12.7"

val circeVersion = "0.9.3"
val lolhttpVersion = "0.11.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "com.criteo.lolhttp" %% "lolhttp" % lolhttpVersion
libraryDependencies += "com.criteo.lolhttp" %% "loljson" % lolhttpVersion