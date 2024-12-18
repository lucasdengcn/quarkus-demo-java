# quarkus-demo

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Tech Stack

- JDK 21
- GraalVM 21
- Quarkus 3.15
- Kafka
- Redis
- JUnit5
- JWT
- JaCoCo
- Hibernate
- Panache ORM
- Postgresql
- MapStruct
- lombok
- Open API
- Yaml
- OTEL
- Micrometer
- Health

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Timing

2024-10-24 18:26:21,742 INFO  [io.quarkus] (main) demo-app 1.0.0-SNAPSHOT native (powered by Quarkus 3.15.1) started in __0.392s__. Listening on: http://0.0.0.0:8080. Management interface listening on http://0.0.0.0:9000.


## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


### Management interface

http://0.0.0.0:9000/q/metrics

http://0.0.0.0:9000/q/metrics/prometheus

http://0.0.0.0:9000/q/metrics/json

### Health check

http://0.0.0.0:9000/q/health/live - The application is up and running.

http://0.0.0.0:9000/q/health/ready - The application is ready to serve requests.

http://0.0.0.0:9000/q/health/started - The application is started.

http://0.0.0.0:9000/q/health - Accumulating all health check procedures in the application.

http://localhost:8080/q/health-ui/

### Open API

http://localhost:8080/q/swagger-ui/

### OTEL on Components

- JDBC
- Redis
- Kafka
