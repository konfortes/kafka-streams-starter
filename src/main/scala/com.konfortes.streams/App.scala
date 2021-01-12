package com.konfortes.streams

// import java.util.Properties
// import java.util.concurrent.TimeUnit

// import com.typesafe.config.ConfigFactory
// import org.apache.kafka.common.serialization.Serde
// import org.apache.kafka.streams.scala.StreamsBuilder
// import org.apache.kafka.streams.scala.kstream.KStream
// import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.LoggerFactory

object ExampleApp extends App {
  val Logger = LoggerFactory.getLogger(getClass.getSimpleName)
  Logger.info("starting!")

  // import org.apache.kafka.streams.scala.ImplicitConversions._
  // import org.apache.kafka.streams.scala.Serdes._

  // def main {
  //   val builder = new StreamsBuilder

  //   val stream = builder
  //     .stream[String, String](
  //       conf.getString("transactions.application.source.topic")
  //     )
  //     .mapValues(t => t.toUpperCase)
  //   stream.to(
  //     conf.getString("transactions.application.sink.topic")
  //   )

  //   val streams = new KafkaStreams(builder.build(), props)

  //   streams.start

  //   sys.ShutdownHookThread {
  //     streams.close(10, TimeUnit.SECONDS)
  //   }
  // }

  // private def loadConfig: Props {}
  // val conf = ConfigFactory.load
  // println(conf)

  // val props = new Properties
  // props.setProperty(
  //   StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
  //   conf.getString("streams-starter.bootstrap.servers")
  // )
  // props.setProperty(
  //   StreamsConfig.APPLICATION_ID_CONFIG,
  //   conf.getString("streams-starter.application.id")
  // )

  // props
}
