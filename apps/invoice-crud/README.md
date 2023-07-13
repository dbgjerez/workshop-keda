# parking-crud

## Local development

### MariaDB

```bash
podman run \
    -d  \
    --name mariadb  \
    --env MARIADB_USER=parking \
    --env MARIADB_PASSWORD=parking123 \
    --env MARIADB_ROOT_PASSWORD=parking123 \
    --env MARIADB_DATABASE=parking \
    -p 3306:3306 \
    mariadb:latest
```

### API

#### Create an entrance

```bash
curl -X POST \
    --data '{"plate": "0000GGG"}' \
    -H 'Content-Type: application/json' \
    localhost:8080/parking

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

You can then execute your native executable with: `./target/parking-crud-1.0.0-SNAPSHOT-runner`

### Build a native container

```shell script
podman build --no-cache -f src/main/docker/Dockerfile.native-micro -t quay.io/dborrego/keda-parking-crud:0.1 .
```