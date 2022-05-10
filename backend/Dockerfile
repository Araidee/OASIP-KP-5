FROM maven:3.8.5-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml .
COPY src/ ./src/

RUN mvn -Dmaven.test.skip package

FROM openjdk:17-jdk-alpine

ARG JAR_FILE=/app/target/*.jar

COPY --from=build ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT exec java -jar app.jar
