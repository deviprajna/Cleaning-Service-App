# Tahap 1: Kompilasi kode Java Menggunakan Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Tahap 2: Menjalankan file .jar hasil kompilasi
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=9999"]