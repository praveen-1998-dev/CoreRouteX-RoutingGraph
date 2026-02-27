# Use a lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built jar into the container
COPY target/core-routex-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]