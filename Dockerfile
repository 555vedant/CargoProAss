# Stage 1: Build the application
# This stage uses a Maven image to build the Spring Boot application.
# It copies the source code, resolves dependencies, and packages the application into a JAR file.
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
# This layer is cached by Docker, speeding up subsequent builds if dependencies haven't changed.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the application's source code
COPY src ./src

# Build the application, skipping tests for a faster build.
# The resulting JAR will be in the 'target' directory.
RUN mvn package -DskipTests


# Stage 2: Create the final, smaller runtime image
# This stage uses a slim OpenJDK image which is much smaller than the Maven image,
# resulting in a more efficient final container.
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the Spring Boot application will run on.
# 8080 is the default for Spring Boot, but you can change it if needed.
EXPOSE 8080

# The command to run the application when the container starts.
# 'java -jar app.jar' executes the Spring Boot application.
ENTRYPOINT ["java","-jar","app.jar"]