# parking-crud

## Local development

### MariaDB

```bash
podman run \
    -d  \
    --name mariadb-fares  \
    --env MARIADB_USER=parking \
    --env MARIADB_PASSWORD=parking123 \
    --env MARIADB_ROOT_PASSWORD=parking123 \
    --env MARIADB_DATABASE=fares \
    -p 3306:3306 \
    mariadb:latest
```

### API

#### Create an entrance
```bash
curl -X POST \
    --data '{"vehicleType": "3", "minutePrice": 0.02}' \
    -H 'Content-Type: application/json' \
    localhost:8080/fares
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
mvn quarkus:dev
```

## Native compilation

> Warning: https://github.com/dbgjerez/workshop-keda/issues/3

### Creating a native executable

You can create a native executable using: 

```shell script
mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/fares-crud-1.0.0-SNAPSHOT-runner`

### Build a native container

```shell script
podman build --no-cache -f src/main/docker/Dockerfile.native-micro -t quay.io/dborrego/keda-fares-crud:0.1 .
```