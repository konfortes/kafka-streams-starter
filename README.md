# kafka-streams-starter

This is a starter project for Kafka Streams.
It contains a simple processing example on a ksql-datagen quickstart data (see [data-gen/README.md](data-gen/README.md))

## How to Run

1. use [https://github.com/konfortes/kafka-ecosystem](https://github.com/konfortes/kafka-ecosystem) to spawn all required docker containers (Zookeeper, Kafka, KSqlDB, etc.)

2. Create the source and sink topics:

    ```bash

    foreach topic (users users-output)
      kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $topic
    end

    kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --config cleanup.policy=compact --topic males-by-region-output
    ```

    (Cleanup)

    ```bash
    foreach topic (users users-output males-by-region-output)
        kafka-topics --delete --bootstrap-server localhost:9092 --topic $topic
    end
    ```

3. Run the app: `sbt run`

4. Produce data into the `users` topic:

    ```bash
    ksql-datagen quickstart='users' topic='users' msgRate=5 iterations=1000 printRows=false
    ```

5. See processed data output

    ```bash
    # users-output
    kafkacat -C -b localhost:9092 -t users-output -f 'Key: %k\nValue: %s\n'

    # males-by-region-output
    kafkacat -C -b localhost:9092 -s key=c -s value=q -t males-by-region-output -f 'Key: %k\nValue: %s\n'
    ```

## Topology

<img src="./topology.png" alt="drawing" width="230"></img>
