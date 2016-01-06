name := "shirokaneHack"

version := "0.0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.1.3" % "test",
  "fr.iscpif.gridscale" % "sge_2.11" % "1.86",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)
