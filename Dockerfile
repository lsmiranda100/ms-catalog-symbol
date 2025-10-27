# Dockerfile para MS Catalog Symbol
# Multi-stage build para optimizar el tamaño de la imagen

# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear usuario no root para seguridad
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

# Copiar JAR desde stage de build
COPY --from=build /app/target/ms-catalog-symbol-1.0.0.jar app.jar

# Cambiar a usuario no root
USER spring:spring

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


