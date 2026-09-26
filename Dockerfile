# Etapa 1: construir el .jar con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY src/main/java/com/devSenior/campusFlow .
RUN mvn clean package -DskipTests

# Etapa 2: imagen liviana solo para correr el .jar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]