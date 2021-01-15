package com.konfortes.streams

import com.konfortes.streams.types.User
import com.goyeau.kafka.streams.circe.CirceSerdes._
import io.circe.generic.auto._
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.KTable

object StreamsStarterTopology {
  def build(conf: TopologyConfig): Topology = {
    val builder = new StreamsBuilder

    val malesStreams = builder
      .stream[String, User](conf.sourceTopic)
      .filter((_, user) => user.gender == "MALE")
      // explicitly repartition
      .map((key, user) =>
        (
          user.regionid.split("_")(1),
          user.copy(regionid = user.regionid.split("_")(1))
        )
      )

    val malesByRegionTable: KTable[String, Long] = malesStreams
      .peek((k, v) => println(s"key: $k value: $v"))
      .groupByKey // records are already repartitioned correctly
      .count()

    malesStreams
      .to(
        conf.sinkTopic
      )

    malesByRegionTable.toStream.to(conf.malesByRegionSink)

    builder.build
  }
}
