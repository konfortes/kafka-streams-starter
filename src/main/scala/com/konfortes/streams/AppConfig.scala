package com.konfortes.streams

import java.util.Properties

case class KafkaStreamsConfig(props: Map[String, String]) {
  def toProps: Properties = {
    val p = new Properties()
    props.foreach {
      case (k, v) => p.put(k, v)
    }
    p
  }
}
case class TopologyConfig(sourceTopic: String, sinkTopic: String)
case class AppConfig(kafkaStreams: KafkaStreamsConfig, topology: TopologyConfig)
