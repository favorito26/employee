# 1. Build stage
FROM maven:3.9.4-eclipse-temurin-17-focal AS builder
WORKDIR /app

# Copy and build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Runtime stage
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
