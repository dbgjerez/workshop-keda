
# Local testing

## Local Kafka

Deploy a Kafka without Zookeeper for testing

```bash
podman run \
    -it \
    -d \
    --name kafka-zkless \
    -p 9092:9092 \
    -e LOG_DIR=/tmp/logs \
    quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 \
    /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

To see the topic data we need a consumer. You can use whatever you want, in my case, I'm using ```kcat```

```bash
kcat -C -b localhost:9092 -t stock
```

Also, you can see the Kafka logs in the container:

```bash
podman logs kafka-zkless
```

## Application

To run the application we can use the container o directly the binary source code. As I'm developing in a local environment, I prefer to use go:

```bash
go run main.go
```

Finally, we can call the application API:

```bash
curl -X POST --data '{"type":"book", "quantity":15, "price": 5.60, "id": "1"}' localhost:8080/api/v1/stock
```

# Lifecycle

To build the application with a Container:

```bash
SERVICE_NAME=keda-fares-api
VERSION=$(semver info v)
SERVICE_BUILD_TIME=$(date '+%Y/%m/%d %H:%M:%S')
podman build \
    --no-cache \
    --build-arg version=$VERSION \
    --build-arg serviceName=$SERVICE_NAME \
    --build-arg buildTime=$SERVICE_BUILD_TIME \
    -t quay.io/dborrego/$SERVICE_NAME:$VERSION \
    -f Containerfile
```

> NOTE: I've used a tool to manage the application version: [semver](https://github.com/dbgjerez/semantic-versioning-cli)