#FROM jelastic/maven:3.9.5-openjdk-21 AS build
FROM openjdk:17-jdk-slim
LABEL authors="VlaSai"

WORKDIR /app

COPY file-service/pom.xml .
COPY file-service/src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar /app/*.jar

CMD ["java", "-jar", "/app/*.jar"]