FROM openjdk:17-jdk-alpine

# Expose the port the application runs on
EXPOSE 8086
ARG JAR_FILE=target/energyAustralia-coding-test-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

#COPY target/energyAustralia-coding-test-0.0.1-SNAPSHOT.jar /src/app.jar
COPY target/*.jar /app.jar

# Set the entry point of the container to run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]