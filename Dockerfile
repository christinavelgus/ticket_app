FROM maven:3.8.1-openjdk-17-slim AS builder

WORKDIR /app
COPY pom.xml .
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/ticket-app-0.0.1-SNAPSHOT.jar /ticket-app.jar
CMD ["java", "-jar", "/ticket-app.jar"]


