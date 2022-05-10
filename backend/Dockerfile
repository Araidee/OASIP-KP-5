FROM maven:3.8.1-jdk-11 AS build
WORKDIR /build/
COPY /backend221/pom.xml /build/
COPY /backend221/src /build/src/
RUN mvn clean package

FROM openjdk:11
WORKDIR /app
COPY --from=build /build/target/classicmodels-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "classicmodels-service-0.0.1-SNAPSHOT.jar"]
