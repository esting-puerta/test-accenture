# Instrucciones para ejecutar el Backend en local

## Requisitos previos
Antes de ejecutar el backend, asegúrate de tener instalado:

- Java JRE (Java Runtime Environment) versión 11 o superior
- Java JDK (Java Development Kit) versión 11 o superior
- Gradle (incluido en el proyecto)
- Docker Desktop
- Kubernetes (kubectl)

## Ejecutar en local
Para ejecutar el backend en tu máquina local, sigue estos pasos:

1. Navega a la carpeta del backend:
```bash
cd test.accenture.backend
```

2. Ejecuta la aplicación usando Gradle:
```bash
gradlew bootRun
```

La aplicación se iniciará y estará disponible en `http://localhost:5000` por defecto.
```url
http://localhost:5000
```

## Documentación de APIs
La documentación completa de las APIs está disponible en Swagger UI. Puedes acceder a ella en:
```url
http://localhost:5000/swagger-ui/index.html
```

Aquí encontrarás:
- Lista de todos los endpoints disponibles.
- Detalles de los parámetros requeridos.
- Ejemplos de peticiones y respuestas.
- Capacidad para probar las APIs directamente desde la interfaz.

## Configuración de Docker
El proyecto incluye un `Dockerfile` que configura la construcción y ejecución de la aplicación en un contenedor Docker. Este archivo implementa un enfoque multi-etapa para optimizar el tamaño de la imagen final y mejorar la seguridad:

### Dockerfile
El archivo utiliza un enfoque multi-etapa para optimizar el tamaño de la imagen final:

#### Etapa de construcción:
- Utiliza la imagen base `gradle:8.4-jdk17` que proporciona el entorno de compilación
- Copia el código fuente al contenedor manteniendo la estructura del proyecto
- Compila la aplicación usando Gradle con la opción `--no-daemon` para optimizar el proceso
- Genera el archivo JAR ejecutable en la carpeta `build/libs`

#### Etapa de ejecución:
- Utiliza la imagen ligera `amazoncorretto:17-alpine` como base para la ejecución
- Copia únicamente el JAR compilado desde la etapa de construcción, reduciendo el tamaño final
- Configura variables de entorno críticas:
  - `JAVA_OPTS`: Optimiza el rendimiento con límites de memoria (512MB máximo, 256MB inicial)
  - `SPRING_PROFILES_ACTIVE`: Activa el perfil de producción para optimizar el rendimiento
- Expone el puerto 5000 para permitir la comunicación con la aplicación
- Configura el comando de inicio de la aplicación con los parámetros optimizados

## Configuración de Kubernetes

El proyecto incluye archivos de configuración de Kubernetes en el directorio `k8s/` que definen la infraestructura para ejecutar la aplicación en un cluster de Kubernetes. Estos archivos implementan las mejores prácticas de Kubernetes para alta disponibilidad y escalabilidad:

### deployment.yaml
- Define la configuración del despliegue de la aplicación con estrategias de actualización
- Configura 3 réplicas del pod por defecto para alta disponibilidad
- Establece límites de recursos (CPU y memoria) para garantizar el rendimiento
- Configura health checks (readiness y liveness probes) para monitoreo de salud
- Define variables de entorno para el perfil de producción
- Configura la política de recursos Java para optimizar el rendimiento

### service.yaml
- Define un servicio de tipo LoadBalancer para distribuir el tráfico
- Expone el puerto 80 externamente para acceso HTTP estándar
- Redirige el tráfico al puerto 5000 de los pods internamente
- Permite el balanceo de carga entre las réplicas para alta disponibilidad
- Facilita el descubrimiento de servicios dentro del cluster

### hpa.yaml (Horizontal Pod Autoscaler)
- Configura el escalado automático horizontal para manejar cargas variables
- Mantiene entre 3 y 10 réplicas según la demanda de recursos
- Escala basado en el uso de CPU (70% de utilización) para optimizar recursos
- Escala basado en el uso de memoria (80% de utilización) para prevenir problemas
- Proporciona alta disponibilidad y capacidad de respuesta automática

## Ejecutar con Docker y Kubernetes
Para ejecutar la aplicación backend usando contenedores Docker y Kubernetes debe ejecutar el archivo run.sh
```bash
run.sh
```

```bash
==================================================================================================================================
```

# Instrucciones para ejecutar la Aplicación Ionic

## Requisitos previos
Antes de ejecutar la aplicación Ionic, asegúrate de tener instalado:

- Node.js (versión 18 o superior)
- npm (incluido con Node.js)
- Angular CLI (`npm install -g @angular/cli`)
- Ionic CLI (`npm install -g @ionic/cli`)
- Android Studio (para generar el APK de Android)
  - Android SDK
  - Android SDK Platform-Tools
  - Android SDK Build-Tools
  - Android Emulator (opcional, para pruebas)

## Ejecutar en local

Para ejecutar la aplicación Ionic en tu máquina local, sigue estos pasos:

1. Navega a la carpeta del proyecto Ionic:
```bash
cd test.accenture.ionic
```

2. Instala las dependencias del proyecto:
```bash
npm install
```

3. Inicia la aplicación en modo desarrollo:
```bash
npm start
```

La aplicación se iniciará y estará disponible en `http://localhost:4200` por defecto.
```url
http://localhost:4200
```

## Generar APK para Android
Para generar el APK de Android, sigue estos pasos:

1. Asegúrate de tener Android Studio instalado y configurado correctamente
2. Verifica que las variables de entorno ANDROID_HOME y JAVA_HOME estén configuradas
3. Ejecuta el siguiente comando para construir la aplicación:
```bash
npm run build-dev
```

El APK se generará en la carpeta `platforms/android/app/build/outputs/apk/debug/`

## Características de la aplicación
- Desarrollada con Ionic Framework y Angular
- Interfaz de usuario moderna y responsive
- Soporte para plataformas móviles (Android e iOS)

## Comandos útiles
- `npm start`: Inicia la aplicación en modo desarrollo
- `npm run build`: Compila la aplicación para producción
- `npm run test`: Ejecuta las pruebas unitarias
- `npm run lint`: Ejecuta el linter para verificar el código
- `npm run build-dev`: Compila y sincroniza la aplicación para Android

## Estructura del proyecto
- `src/`: Contiene el código fuente de la aplicación
- `www/`: Directorio de compilación para la web
- `platforms/`: Contiene las plataformas nativas (Android/iOS)
- `resources/`: Recursos para iconos y splash screens
- `config.xml`: Configuración de Cordova
- `capacitor.config.ts`: Configuración de Capacitor


