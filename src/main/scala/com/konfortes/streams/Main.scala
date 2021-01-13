package com.konfortes.streams

import java.time.Duration
import java.util.Properties
import java.util.concurrent.TimeUnit

import pureconfig._
import pureconfig.generic.auto._
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.KStream
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.LoggerFactory

object Main extends App {
  import org.apache.kafka.streams.scala.ImplicitConversions._
  import org.apache.kafka.streams.scala.Serdes._

  val Logger = LoggerFactory.getLogger(getClass.getSimpleName)
  Logger.info("Starting!")

  val conf = pureconfig.loadConfigOrThrow[AppConfig]("streams-starter")
  println(conf)

  val builder = new StreamsBuilder

  val stream = builder
    .stream[String, String](
      conf.topology.sourceTopic
    )
    .mapValues(t => t.toUpperCase)
  stream.to(
    conf.topology.sinkTopic
  )

  val topology = builder.build
  Logger.info(topology.describe.toString)

  val streams = new KafkaStreams(topology, conf.kafkaStreams.toProps)

  streams.start

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
