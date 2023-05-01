
# Local testing

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