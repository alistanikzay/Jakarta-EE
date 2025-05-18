# 1. Bygg fas: Använder Maven för att bygga applikationen
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Kör fas: Använder Payara Micro för att köra WAR-filen
FROM payara/micro:6.2024.2-jdk21
COPY --from=build /app/target/bookapi.war $DEPLOY_DIR
