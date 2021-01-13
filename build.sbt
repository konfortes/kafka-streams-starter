scalaVersion := "2.13.3"
name := "streams-starter"
version := "1.0"

libraryDependencies ++= Seq(
  // logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // configuration
  "com.github.pureconfig" %% "pureconfig" % "0.14.0",
  // kafka-streams
  "org.apache.kafka" % "kafka-streams" % "2.7.0",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.7.0",
  // serdes
  // "io.circe" %% "circe-core" % "0.12.3"
  "com.goyeau" %% "kafka-streams-circe" % "0.6.3"
)
