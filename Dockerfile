# Usamos una imagen base con Java 17
FROM openjdk:17-jdk-slim

# Directorio dentro del contenedor
WORKDIR /app

# Copia el jar compilado al contenedor
COPY target/usuarios-service-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
