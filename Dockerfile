# Etapa 1: Construcción con Maven + Java 21
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app

# Copiar el archivo pom.xml y la carpeta fuente completa
COPY pom.xml .
COPY src ./src

# Compilar omitiendo pruebas
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución liviana con Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar el ejecutable .jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]