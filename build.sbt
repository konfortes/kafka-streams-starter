scalaVersion := "2.13.3"
name := "streams-starter"
version := "1.0"

libraryDependencies ++= Seq(
  // logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // configuration
  "com.typesafe" % "config" % "1.4.1"
  // kafka-streams
//   "org.apache.kafka" % "kafka-streams" % "2.7.0",
//   "org.apache.kafka" %% "kafka-streams-scala" % "2.7.0"
)
