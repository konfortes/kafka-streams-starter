package com.konfortes.streams

import java.time.Duration

import com.goyeau.kafka.streams.circe.CirceSerdes._
import com.konfortes.streams.types.Order
import io.circe.generic.auto._
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Suppressed.BufferConfig
import org.apache.kafka.streams.kstream.{Suppressed, TimeWindows, Windowed, WindowedSerdes}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.{Consumed, KTable, Produced}
import org.apache.kafka.streams.scala.serialization.Serdes._

object StreamsStarterTopology {
  def build(conf: TopologyConfig): Topology = {
    val builder = new StreamsBuilder

    implicit val consumed: Consumed[String, Order] =
      Consumed.`with`(Order.timestampExtractor)

    val windowedOrdersSumPerUser: KTable[Windowed[String], Long] = builder
      .stream[String, Order](conf.sourceTopic)
      .filter((_, order) => order.totalAmount > 0)
      .peek((userId, order) => println(s"userId: $userId | order: $order"))
      .groupByKey
      .windowedBy(
        TimeWindows.of(Duration.ofMinutes(1)).grace(Duration.ofSeconds(10))
      )
      .count
      .suppress(
        Suppressed.untilWindowCloses(BufferConfig.unbounded())
      )

    // TODO: for some reason it is using implicitly session window instead of timed window
    implicit val produced: Produced[Windowed[String], Long] =
      Produced.`with`[Windowed[String], Long](
        new WindowedSerdes.TimeWindowedSerde(
          stringSerde
        ),
        longSerde
      )

    windowedOrdersSumPerUser.toStream
      .peek(printWindow)
      .to(conf.sinkTopic)

    builder.build
  }

  private def printWindow[K, V](windowedKey: Windowed[K], value: V): Unit = {
    println(
      s"window key: ${windowedKey.key} window start: ${windowedKey.window.start} window end: ${windowedKey.window.end} sum: $value"
    )
  }
}
