#FROM maven:3.9.4-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=/app/target/*.jar
#COPY --from=build ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/ticket-app-0.0.1-SNAPSHOT.jar /ticket-app.jar
ENTRYPOINT ["java", "-jar", "/ticket-app.jar"]

EXPOSE 8081
