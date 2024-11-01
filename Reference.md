## Practices Reference

### Configuration

https://quarkus.io/guides/config-reference

https://quarkus.io/guides/config-yaml

https://quarkus.io/guides/config-reference#property-expressions

https://quarkus.io/guides/config-reference#secret-keys-expressions

#### Conversion rules

https://github.com/eclipse/microprofile-config/blob/master/spec/src/main/asciidoc/configsources.asciidoc#default-configsources

- System properties via -D flag
- Environment variables via export
- Application configuration file
- Vault https://github.com/quarkiverse/quarkus-vault
- Consul https://github.com/quarkiverse/quarkus-config-extensions

#### Profiles

```yaml

%dev.quarkus
%test.quarkus
%uat.quarkus
%staging.quarkus
%prod.quarkus

```

Profile aware files

- application.yaml
- application-dev.yaml
- application-test.yaml
- application-staging.yaml
- application-prod.yaml

Parent Profile

- application-common.yaml

Environment Vars

application.host=${ENV_HOST:${remote.host}}


### ORM

https://quarkus.io/guides/hibernate-orm

https://quarkus.io/guides/hibernate-orm#hibernate-configuration-properties

https://quarkus.io/guides/datasource

https://quarkus.io/guides/hibernate-orm-panache

https://docs.jboss.org/hibernate/orm/7.0/userguide/html_single/Hibernate_User_Guide.html#PhysicalNamingStrategy

### Yaml

https://quarkus.io/guides/config-yaml

### Logging

https://quarkus.io/guides/logging

https://quarkus.io/guides/logging#loggingConfigurationReference


### Open API

https://quarkus.io/guides/openapi-swaggerui

### Transaction

https://quarkus.io/guides/hibernate-orm-panache#transactions

### Common Error Response

RFC7807 Problem schema

### Messaging

https://quarkus.io/guides/messaging

https://quarkus.io/guides/kafka-getting-started

https://quarkus.io/guides/kafka

https://quarkus.io/guides/kafka#incoming-channel-configuration-polling-from-kafka


### OTEL

https://quarkus.io/guides/opentelemetry

https://quarkus.io/guides/opentelemetry-tracing

https://quarkus.io/guides/telemetry-micrometer

https://quarkus.io/guides/telemetry-micrometer#create-your-own-metrics

https://quarkus.io/guides/smallrye-health

### Cache

https://quarkus.io/guides/cache-redis-reference

https://quarkus.io/guides/cache

https://quarkus.io/guides/redis-reference

https://quarkus.io/guides/redis-reference#use-the-vert-x-redis-client

https://quarkus.io/guides/redis-reference#default-and-named-clients

### Security

https://quarkus.io/guides/security-overview

https://quarkus.io/guides/security-authentication-mechanisms

https://quarkus.io/guides/security-authentication-mechanisms#mutual-tls

https://quarkus.io/guides/security-jwt

https://quarkus.io/guides/images/security-architecture-overview.png

### Testing

https://www.baeldung.com/rest-assured-header-cookie-parameter

### Events and Async

https://quarkus.io/guides/reactive-event-bus

### Scheduler

https://quarkus.io/guides/scheduler
https://quarkus.io/guides/quartz

### Long Running Task

https://quarkus.io/blog/using-lra/

ManagedExecutor managedExecutor;


### Context

https://quarkus.io/guides/context-propagation

### Reactive

https://quarkus.io/guides/quarkus-reactive-architecture#unification-of-imperative-and-reactive

https://smallrye.io/smallrye-mutiny/latest/




