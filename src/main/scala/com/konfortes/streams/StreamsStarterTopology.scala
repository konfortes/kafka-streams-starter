package com.konfortes.streams

import java.time.Duration

import com.goyeau.kafka.streams.circe.CirceSerdes._
import com.konfortes.streams.types.Order
import io.circe.generic.auto._
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.Suppressed.BufferConfig
import org.apache.kafka.streams.kstream.{
  Suppressed,
  TimeWindows,
  Windowed,
  WindowedSerdes
}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.{ByteArrayWindowStore, StreamsBuilder}
import org.apache.kafka.streams.scala.kstream.{Consumed, KTable, Produced}
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.scala.kstream.Materialized

object StreamsStarterTopology {
  val sumPerUserMaterialized = Materialized
    .as[String, Long, ByteArrayWindowStore]("sum-per-user")
    .withRetention(
      Duration.ofHours(25)
    ) // store retention must be greater than the window time + grace time

  // TODO: for some reason it is using implicitly session window instead of timed window
  val outputProduced: Produced[Windowed[String], Long] =
    Produced.`with`[Windowed[String], Long](
      new WindowedSerdes.TimeWindowedSerde(
        stringSerde
      ),
      longSerde
    )

  val aggregateFn = (_: String, order: Order, agg: Long) => {
    // TODO: match on sealed trait
    val current: Long = order.status match {
      case "created"  => order.totalAmount
      case "canceled" => order.totalAmount * -1
    }

    current + agg
  }

  def build(conf: TopologyConfig): Topology = {
    val builder = new StreamsBuilder

    val windowedOrdersSumPerUser: KTable[Windowed[String], Long] = builder
      .stream[String, Order](conf.sourceTopic)(
        Consumed.`with`(Order.timestampExtractor)
      )
      .filter((_, order) => order.userId.forall(_.isDigit))
      .peek((orderId, order) => println(s"orderId: $orderId | order: $order"))
      .groupBy((_, order) => order.userId)
      .windowedBy(
        TimeWindows.of(Duration.ofDays(1)).grace(Duration.ofSeconds(10))
      )
      .aggregate[Long](0L)(aggregateFn)(sumPerUserMaterialized)
      .suppress(
        Suppressed.untilWindowCloses(BufferConfig.unbounded())
      )

    windowedOrdersSumPerUser.toStream
      .peek(printWindow)
      .to(conf.sinkTopic)(outputProduced)

    builder.build
  }

  private def printWindow[K, V](windowedKey: Windowed[K], value: V): Unit = {
    println(
      s"window key: ${windowedKey.key} window start: ${windowedKey.window.start} window end: ${windowedKey.window.end} sum: $value"
    )
  }
}
