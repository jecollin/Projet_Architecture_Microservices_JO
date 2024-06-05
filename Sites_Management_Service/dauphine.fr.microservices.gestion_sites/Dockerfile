# Use a smaller, more secure base image like Alpine if compatible
FROM openjdk:8-jdk-alpine

# Set a working directory inside the container for better structure and management
WORKDIR /app

# Add a non-root user for running the application (improves security)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the artifact we need from the local machine to the container
COPY target/gestion_sites.jar gestion_sites.jar

# Expose the port the application runs on
EXPOSE 8301

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "gestion_sites.jar"]
