# 1. Build stage
FROM maven:3.8.7-jdk-17 AS builder
WORKDIR /app

# copy and build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
