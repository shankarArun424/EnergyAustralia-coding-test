# Spring Boot Maven Project for EnergyAustralia
Springboot project for digitalizing the music industry data.

## Prerequisites
Make sure you have the following installed:

- Java Development Kit (JDK) 17 or later
- Apache Maven
- Docker installed and running on your system

## Getting Started

1. Clone this repository:

```bash
git clone https://github.com/shankarArun424/ea
```

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

# Digitalizing the api result
From the URL(https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals), The result has been based on the festivals based music concept and the attentive band names.
We modernized the result from the api to the content list out in the music industry based listing.

# Installation and running
Take a git clone of this repo and trigger the project.
Once you hit the URL (http://localhost:8086/api/v1/musiclabel) in postman or browser, It returns the listing of the music based band list and the attented festivals for the individual bands.

# Spring boot Maven project
Designed this API using the Apache Maven plugin, It allows you to package executable jar or war archives.
