scalaVersion := "2.13.3"
name := "streams-starter"
version := "1.0"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.pureconfig" %% "pureconfig" % "0.14.0",
  "org.apache.kafka" % "kafka-streams" % "2.7.0",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.7.0",
  "com.goyeau" %% "kafka-streams-circe" % "0.6.3"
)
