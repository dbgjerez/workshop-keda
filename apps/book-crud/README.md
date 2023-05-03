# book-crud

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
mvn compile quarkus:dev
```

## Creating a native executable

You can create a native executable using: 

```shell script
mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/book-crud-1.0.0-SNAPSHOT-runner`

## Build a native container

