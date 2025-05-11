# Use the official Maven image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.9.8-amazoncorretto-21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the project dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Ensure the encoding is set correctly
ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF-8

# Package the application
RUN mvn package -DskipTests

# Use the official OpenJDK image to run the application
# https://hub.docker.com/_/openjdk
FROM openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
