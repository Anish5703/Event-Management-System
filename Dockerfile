# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the project JAR file (Assuming it's located in the build/libs folder)
COPY build/libs/*.jar app.jar

# Expose the port on which the Spring Boot app runs
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
