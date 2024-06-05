# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/gestion_sports.jar ./gestion_sports.jar

# Make port 8302 available to the world outside this container
EXPOSE 8302

# Run the JAR file
ENTRYPOINT ["java", "-jar", "gestion_sports.jar"]
