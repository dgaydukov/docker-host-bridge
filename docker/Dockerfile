# Use Amazon Corretto 21 on Alpine Linux
FROM amazoncorretto:21-alpine-jdk

# Set working directory
WORKDIR /app

# Copy your Spring Boot jar
COPY target/docker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app uses
EXPOSE 4444

# Run the Spring Boot jar
ENTRYPOINT ["java", "-jar", "app.jar"]
