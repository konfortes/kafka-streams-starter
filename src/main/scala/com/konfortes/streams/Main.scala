package com.konfortes.streams

import java.time.Duration
import java.util.Properties
import java.util.concurrent.TimeUnit

import pureconfig._
import pureconfig.generic.auto._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.KStream
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.LoggerFactory
import com.konfortes.streams.types.User
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import com.goyeau.kafka.streams.circe.CirceSerdes._
import io.circe.generic.auto._
import org.apache.kafka.streams.scala.kstream.Repartitioned
import org.apache.kafka.streams.processor.StreamPartitioner
import org.apache.kafka.clients.producer.internals.DefaultPartitioner

object Main extends App {
  val Logger = LoggerFactory.getLogger(getClass.getSimpleName)
  Logger.info("Starting...")

  val conf = pureconfig.loadConfigOrThrow[AppConfig]("streams-starter")

  val builder = new StreamsBuilder

  val stream = builder
    .stream[String, User](conf.topology.sourceTopic)
    .filter((_, user) => user.gender == "MALE")
    .mapValues(user => user.copy(regionid = user.regionid.split("_")(1)))
    .peek((k, v) => println(s"key: $k value: $v"))
    .to(
      conf.topology.sinkTopic
    )

  val topology = builder.build
  Logger.debug(topology.describe.toString)

  val streams = new KafkaStreams(topology, conf.kafkaStreams.toProps)

  streams.start

  sys.ShutdownHookThread {
    Logger.info("Shutting down...")
    streams.close(Duration.ofSeconds(10))
  }

}
