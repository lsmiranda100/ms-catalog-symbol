#!/bin/bash

# Script para ejecutar MS Catalog Symbol fácilmente
# Uso: ./run.sh [dev|prod|docker|stop]

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Banner
echo -e "${BLUE}"
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║           MS CATALOG SYMBOL - Script de Ejecución             ║"
echo "║                 Spring Boot 3.3.4 | Java 17                   ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Verificar Java
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Java no está instalado. Por favor instala Java 17 o superior.${NC}"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}❌ Se requiere Java 17 o superior. Versión actual: $JAVA_VERSION${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Java versión: $(java -version 2>&1 | head -n 1)${NC}"

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}❌ Maven no está instalado. Por favor instala Maven 3.6+${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Maven versión: $(mvn -version | head -n 1)${NC}"
echo ""

# Función para modo desarrollo (H2)
run_dev() {
    echo -e "${YELLOW}🚀 Iniciando en modo DESARROLLO (H2 en memoria)...${NC}"
    echo ""
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
}

# Función para modo producción (PostgreSQL)
run_prod() {
    echo -e "${YELLOW}🚀 Iniciando en modo PRODUCCIÓN (PostgreSQL)...${NC}"
    echo -e "${YELLOW}⚠️  Asegúrate de que PostgreSQL esté ejecutándose en localhost:5432${NC}"
    echo ""
    mvn spring-boot:run
}

# Función para modo Docker
run_docker() {
    if ! command -v docker &> /dev/null; then
        echo -e "${RED}❌ Docker no está instalado.${NC}"
        exit 1
    fi

    echo -e "${YELLOW}🐳 Construyendo y levantando servicios con Docker Compose...${NC}"
    echo ""
    
    # Compilar primero
    echo -e "${BLUE}📦 Compilando proyecto...${NC}"
    mvn clean package -DskipTests
    
    # Levantar servicios
    docker-compose up --build -d
    
    echo ""
    echo -e "${GREEN}✅ Servicios levantados exitosamente${NC}"
    echo -e "${BLUE}📊 Ver logs: docker-compose logs -f${NC}"
    echo -e "${BLUE}🛑 Detener: docker-compose down${NC}"
    
    # Esperar a que la app esté lista
    echo ""
    echo -e "${YELLOW}⏳ Esperando a que el servicio esté listo...${NC}"
    sleep 10
    
    # Mostrar estado
    docker-compose ps
}

# Función para detener Docker
stop_docker() {
    echo -e "${YELLOW}🛑 Deteniendo servicios Docker...${NC}"
    docker-compose down
    echo -e "${GREEN}✅ Servicios detenidos${NC}"
}

# Función para compilar y ejecutar tests
run_tests() {
    echo -e "${YELLOW}🧪 Ejecutando tests...${NC}"
    mvn test
}

# Función para mostrar ayuda
show_help() {
    echo "Uso: ./run.sh [OPCIÓN]"
    echo ""
    echo "Opciones:"
    echo "  dev      - Ejecutar en modo desarrollo con H2 (por defecto)"
    echo "  prod     - Ejecutar en modo producción con PostgreSQL"
    echo "  docker   - Construir y ejecutar con Docker Compose"
    echo "  stop     - Detener servicios Docker"
    echo "  test     - Ejecutar tests"
    echo "  help     - Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  ./run.sh dev"
    echo "  ./run.sh docker"
    echo "  ./run.sh stop"
}

# Mostrar URLs útiles
show_urls() {
    echo ""
    echo -e "${GREEN}╔════════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${GREEN}║                    URLs del Servicio                           ║${NC}"
    echo -e "${GREEN}╠════════════════════════════════════════════════════════════════╣${NC}"
    echo -e "${GREEN}║ Swagger UI:  ${BLUE}http://localhost:8080/swagger-ui.html${GREEN}           ║${NC}"
    echo -e "${GREEN}║ API Docs:    ${BLUE}http://localhost:8080/api-docs${GREEN}                  ║${NC}"
    echo -e "${GREEN}║ Health:      ${BLUE}http://localhost:8080/health${GREEN}                    ║${NC}"
    echo -e "${GREEN}║ Actuator:    ${BLUE}http://localhost:8080/actuator/health${GREEN}           ║${NC}"
    echo -e "${GREEN}║ H2 Console:  ${BLUE}http://localhost:8080/h2-console${GREEN} (solo dev)     ║${NC}"
    echo -e "${GREEN}╚════════════════════════════════════════════════════════════════╝${NC}"
    echo ""
}

# Main
case "${1:-dev}" in
    dev)
        run_dev
        show_urls
        ;;
    prod)
        run_prod
        show_urls
        ;;
    docker)
        run_docker
        show_urls
        ;;
    stop)
        stop_docker
        ;;
    test)
        run_tests
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        echo -e "${RED}❌ Opción no válida: $1${NC}"
        echo ""
        show_help
        exit 1
        ;;
esac


