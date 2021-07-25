# Simple Kafka Producer

I've created this project to train how to test kafka applications with SpringBoot. 

## Prerequisites

* Java 11
* Maven 3.6+

## Running manually

I prefer to test automaticaly, but if you want to do some tests mannualy using Postman, for example, you need to download Kafka and run commands below:

```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
```

Open another terminal and type:

```shell
bin/kafka-server-start.sh config/server.properties
```

Now, you just need to start the application:

```shell
mvn spring-boot:run
```

Done. We can send requests to `localhost:8080`.

## Running automatically

To run the automatic test, just run:

```shell
mvn test
```

## TO DO

I plan to create more tests using Testcontainers.


