package com.konfortes.streams.types

import java.time.Instant

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.streams.processor.TimestampExtractor

case class Order(
    items: List[String],
    totalAmount: Long,
    userId: String,
    // status: OrderStatus,
    status: String,
    createdAt: String
)

object Order{
  val timestampExtractor = new TimestampExtractor {
    def extract(
                 record: ConsumerRecord[Object, Object],
                 previousTimestamp: Long
               ): Long = {
      val createdAt = record.value.asInstanceOf[Order].createdAt
      Instant.parse(createdAt).toEpochMilli
    }
  }
}

// sealed trait OrderStatus
// case object StatusCreated extends OrderStatus
// case object StatusConfirmed extends OrderStatus
// case object StatusCanceled extends OrderStatus
