ARG BUILD_IMAGE=maven:3.8-amazoncorretto-17
ARG RUNTIME_IMAGE=amazoncorretto:17-al2-full

### STEP 1 build executable binary
FROM ${BUILD_IMAGE} as builder

ENV TEMP_DIR /tmp
# PREPARE SRC TO BUILD
COPY pom.xml $TEMP_DIR
COPY src /tmp/src/
WORKDIR $TEMP_DIR

# build
RUN mvn package

## STEP 2 build final image (small image)
FROM ${RUNTIME_IMAGE}
COPY --from=builder /tmp/target/*.jar /app/application.jar

WORKDIR /app

# open port for app
EXPOSE 8080

ENTRYPOINT [ "java", "-Xmx512m", "-Xms512m", "-jar", "/app/application.jar" ]

## What does Docker dod:
## Application
## DB
## Kafka (event-based)
## Redis (cache)
## ElasticSearch (full text search)
## Logstash (index)
## Kibana (logs)

