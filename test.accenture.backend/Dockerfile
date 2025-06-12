# Etapa de construcción
FROM gradle:8.4-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Etapa de ejecución
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/test.accenture.backend-0.0.1-SNAPSHOT.jar app.jar

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="prod"

# Exponer el puerto
EXPOSE 5000

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 
