package com.konfortes.streams

import java.time.Duration

import org.apache.kafka.streams.KafkaStreams
import org.slf4j.LoggerFactory
import pureconfig._
import pureconfig.generic.auto._

object Main extends App {
  val Logger = LoggerFactory.getLogger(getClass.getSimpleName)
  Logger.info("Starting...")

  val conf = ConfigSource.default.at("streams-starter").loadOrThrow[AppConfig]
  val topology = StreamsStarterTopology.build(conf.topology)
  Logger.debug(topology.describe.toString)

  val streams = new KafkaStreams(topology, conf.kafkaStreams.toProps)

  streams.start

  sys.ShutdownHookThread {
    Logger.info("Shutting down...")
    streams.close(Duration.ofSeconds(10))
  }

}

// TODO: handle errors (it currently crashes on errors)
// org.apache.kafka.streams.errors.StreamsException: Deserialization exception handler is set to fail upon a deserialization error. If you would rather have the streaming pipeline continue after a deserialization error, please set the default.deserialization.exception.handler appropriately.

// TODO: topics naming convention
// TODO: metrics
