# syntax=docker/dockerfile:1

FROM maven:3.8.2-jdk-11 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
COPY settings.properties ./settings.properties
RUN mvn clean package -Dmaven.test.skip

FROM openjdk:11
RUN addgroup --system dins_intern && adduser --system dins_intern --ingroup dins_intern
USER dins_intern:dins_intern
COPY --from=builder /app/target/*.jar /build/app.jar
ENTRYPOINT ["java", "-jar", "/build/app.jar", "--app_settings=/app/settings.properties"]