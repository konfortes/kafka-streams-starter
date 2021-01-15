package com.konfortes.streams

import java.util.Properties

case class TopologyConfig(sourceTopic: String, sinkTopic: String, malesByRegionSink: String)
case class KafkaStreamsConfig(props: Map[String, String]) {
  def toProps: Properties = {
    val p = new Properties()
    props.foreach { case (k, v) =>
      p.put(k, v)
    }
    p
  }
}

case class AppConfig(kafkaStreams: KafkaStreamsConfig, topology: TopologyConfig)
