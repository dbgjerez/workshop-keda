# book-crud

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

You can then execute your native executable with: `./target/book-crud-1.0.0-SNAPSHOT-runner`

### Build a native container

```shell script
podman build --no-cache -f src/main/docker/Dockerfile.native-micro -t quay.io/dborrego/book-crud:0.1 .
```