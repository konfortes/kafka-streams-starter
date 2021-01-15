package com.konfortes.streams

import java.time.Duration

import pureconfig._
import pureconfig.generic.auto._
import org.apache.kafka.streams.KafkaStreams
import org.slf4j.LoggerFactory

object Main extends App {
  val Logger = LoggerFactory.getLogger(getClass.getSimpleName)
  Logger.info("Starting...")

  val conf = pureconfig.loadConfigOrThrow[AppConfig]("streams-starter")
  val topology = StreamsStarterTopology.build(conf.topology)
  Logger.debug(topology.describe.toString)

  val streams = new KafkaStreams(topology, conf.kafkaStreams.toProps)

  streams.start

  sys.ShutdownHookThread {
    Logger.info("Shutting down...")
    streams.close(Duration.ofSeconds(10))
  }

}
