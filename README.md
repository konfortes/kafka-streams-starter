# kafka-streams-starter

This is a starter project for Kafka Streams.  
It contains a simple stream processing example of orders (items: List[String], totalAmount: Long, userId: String, status: String, createdAt: String).  
It outputs the sum per user on a 1d window.

## How to Run

1. use [https://github.com/konfortes/kafka-ecosystem](https://github.com/konfortes/kafka-ecosystem) to spawn all required docker containers (Zookeeper, Kafka, KSqlDB, etc.)

2. Create the source and sink topics:

    ```bash
    foreach topic (order-events orders-sum-by-user-1d-window)
      kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $topic
    end
    ```

    (Cleanup)

    ```bash
    foreach topic (order-events orders-sum-by-user-1d-window 'streams-starter-.*')
      kafka-topics --delete --bootstrap-server localhost:9092 --topic $topic
    end

    kafka-consumer-groups --bootstrap-server localhost:9092 --group streams-starter --reset-offsets --to-earliest --all-topics --execute

    rm -rf /tmp/kafka-streams/streams-starter
    ```

3. Run the app: `sbt run`

4. Produce order events into the topic:

    ```bash
    cat produce.json | kafka-console-producer --broker-list localhost:9092 --topic order-events --property "parse.key=true" --property "key.separator=:"
    ```

5. See processed data output

    ```bash
    kafkacat -C -b localhost:9092 -t orders-sum-by-user-1d-window -s key=s -s value=q -f 'Key: %k\nHeaders: %h\nValue: %s\n' -o beginning
    ```

## Topology

<img src="./topology.png" alt="drawing" width="230"></img>
