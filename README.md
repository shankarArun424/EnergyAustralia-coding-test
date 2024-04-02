# Spring Boot Maven Project for EnergyAustralia
Springboot project for digitalizing the music industry data.

## Prerequisites
Make sure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Apache Maven
- Docker installed and running on your system

## Getting Started

1. Clone this repository:

2. Navigate to the project directory:
```bash
cd ea
```

3. Build the project using Maven:
```bash
mvn clean install
```

4. Run the application
```bash
java -jar target/energyAustralia-coding-test-0.0.1-SNAPSHOT.jar
```

5. To run the application in development mode
```bash
mvn spring-boot:run
```
6. To run tests, execute
```bash
mvn test
```
7. Create as Spring boot package
```bash
mvn clean install spring-boot:repackage
```
8. Build with Docker
```bash
docker build --build-arg JAR_FILE=target/*.jar -t springBoot/energyaustralia-api-coding-test .
```
9. Run with Docker
```bash
docker run -p 8086:8086 energyaustralia-api-coding-test
```

# Digitalizing the api result
From the URL(https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals), The result has been based on the festivals based music concept and the attentive band names.
We modernized the result from the api to the content list out in the music industry based listing.

# Installation and running
Take a git clone of this repo and trigger the project.
Once you hit the URL (http://localhost:8086/api/v1/musiclabel) in postman as GET method or browser, It returns the listing of the music based band list and the attented festivals for the individual bands.

# Spring boot Maven project
Designed this API using the Apache Maven plugin, It allows you to package executable jar or war archives.
