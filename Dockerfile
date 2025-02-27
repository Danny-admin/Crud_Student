# Creamos la imagen modelo (de dockerhub jdk23)
FROM eclipse-temurin:23.0.1_11-jdk
# Definir directorio principal de nuestro contenedor
WORKDIR/directory
# Copia el JAR de tu aplicación
COPY target/SpringBoot_DB-0.0.1-SNAPSHOT.jar directory.jar
# Expone el puerto (si tu aplicación lo usa)
EXPOSE 8081
