# kafka-streams-starter

This is a starter project for Kafka Streams.
It contains a simple processing example on a ksql-datagen quickstart data (see [data-gen/README.md](data-gen/README.md))

## How to Run

1. use [https://github.com/konfortes/kafka-ecosystem](https://github.com/konfortes/kafka-ecosystem) to spawn all required docker containers (Zookeeper, Kafka, KSqlDB, etc.)

2. Create the source and sink topics:

    ```bash
    kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic users

    kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic users_output
    ```

3. run the app: `sbt run`

4. Produce data into the `users` topic:

    ```bash
    ksql-datagen quickstart='users' topic='users' msgRate=5 iterations=10000 printRows=false
    ```

5. See processed data output in the `users_output` topic
