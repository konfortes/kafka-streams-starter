# data-gen

## Avro Schema

[https://toolslick.com/generation/metadata/avro-schema-from-json](https://toolslick.com/generation/metadata/avro-schema-from-json) can be used to generate Avro schema from a JSON.

See [https://github.com/confluentinc/avro-random-generator](https://github.com/confluentinc/avro-random-generator) for `arg.properties` documentation.


## Produce

This will produce 100000 auto generated random JSON messages (based on the Avro schema) in a 5 messages per second rate into ordercheckout topic.

```bash
ksql-datagen schema=./data-gen/schemas/order-checkout.avro value-format=json topic=ordercheckout key=id key-format=avro msgRate=5 iterations=10000 printRows=false
```
