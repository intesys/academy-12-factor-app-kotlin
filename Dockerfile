# Use a base image with Java 17 installed
FROM eclipse-temurin:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/twelve-factor-demo.jar my-app.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Set the command to run your application when the container starts
CMD ["java", "-jar", "my-app.jar"]
