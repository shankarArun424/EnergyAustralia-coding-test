FROM openjdk:17-jdk-alpine
VOLUME /tmp

# Expose the port the application runs on 8086
EXPOSE 8086
ARG JAR_FILE=target/energyAustralia-coding-test-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

# Copy JAR file to stage
COPY target/*.jar /app.jar

# Set the entry point of the container to run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]