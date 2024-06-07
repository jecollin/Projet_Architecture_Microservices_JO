# Use a multi-stage build to minimize the final image size
# Stage 1: Build the application
FROM maven:3.6.3-openjdk-8 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/gestion_events.jar gestion_events.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "gestion_events.jar"]
