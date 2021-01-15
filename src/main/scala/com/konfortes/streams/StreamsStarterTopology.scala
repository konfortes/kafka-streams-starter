package com.konfortes.streams

import com.goyeau.kafka.streams.circe.CirceSerdes._
import com.konfortes.streams.types.User
import io.circe.generic.auto._
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.scala.StreamsBuilder

object StreamsStarterTopology {
  def build(conf: TopologyConfig): Topology = {
    val builder = new StreamsBuilder

    builder
      .stream[String, User](conf.sourceTopic)
      .filter((_, user) => user.gender == "MALE")
      .mapValues(user => user.copy(regionid = user.regionid.split("_")(1)))
      .peek((k, v) => println(s"key: $k value: $v"))
      .to(
        conf.sinkTopic
      )

    builder.build
  }
}
