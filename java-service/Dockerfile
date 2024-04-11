# Use the official OpenJDK 11 image as the base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file and the application.properties file from the project directory
COPY target/*.jar app.jar
RUN ls

COPY src/main/resources/application.properties application.properties

# Expose the port your application listens on
EXPOSE 8080

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]