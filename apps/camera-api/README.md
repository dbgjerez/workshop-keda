[![Go Report Card](https://goreportcard.com/badge/github.com/dbgjerez/workshop-keda/apps/camera-api/src)](https://goreportcard.com/report/github.com/dbgjerez/workshop-keda/apps/camera-api/src)

# Configuration
| Variable | Default value | Description |
| ------ | ------ | ------ |
| SERVER_PORT | 8080 | Application port |
| GIN_MODE | debug | Gin gonic mode. (release for production mode) |
| SERVICE_VERSION | none | Service version, should be setted in compile time |
| SERVICE_NAME | camera-api | Service name |
| SERVICE_BUILD_TIME | none | Date when the service version was built |
| KAFKA_BROKER | none | Kafka url |
| KAFKA_CAMERA_NEW_PICTURE_TOPIC | none | Kafka topic name |
| CLIENT_CER_PEM_FILE | none | Kafka client certificate secret |
| CLIENT_KEY_PEM_FILE | none | Kafka client key secret |
| SERVER_CER_PEM_FILE | none | Kafka server certificate |

# API
| Endopint | Http verb | Description |
| ------ | ------ | ------ |
| /api/v1/health | GET | Heathcheck |
| /api/v1/info | GET | Application information |
| /api/v1/camera/read | POST | Create a new reading |

# Local testing

## Insert new reading

```bash
curl -X POST \
    --data '{"plate":"0000GGG", "type": "car", "date": 1688396251}'  \
    http://localhost:8080/api/v1/camera/read
```

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

Create the topic:

```bash
podman exec -it kafka-zkless bash

bin/kafka-topics.sh --create --replication-factor 1 --partitions 1 --bootstrap-server localhost:9092 --topic camera-new-picture
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
SERVICE_NAME=keda-camera-api
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
