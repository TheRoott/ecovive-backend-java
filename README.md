# EcoVive Perú - Backend Server

## 🚀 Descripción
Backend completo para la aplicación EcoVive Perú con capacidad para 200+ usuarios concurrentes.

## 🏗️ Arquitectura
- **Framework**: Spring Boot 3.2
- **Base de Datos**: PostgreSQL 15
- **Almacenamiento**: AWS S3 / Local Storage
- **Seguridad**: JWT Authentication
- **Documentación**: Swagger/OpenAPI

## 📊 Capacidad
- **Usuarios**: 200+ concurrentes
- **Reportes**: 1000+ reportes diarios
- **Fotos**: 10GB+ almacenamiento
- **Performance**: < 200ms respuesta promedio

## 🛠️ Tecnologías
- Java 17
- Spring Boot 3.2
- Spring Security
- Spring Data JPA
- PostgreSQL
- AWS SDK
- JWT
- Swagger UI

## 📁 Estructura del Proyecto
```
backend/
├── src/main/java/com/ecovive/
│   ├── EcoViveBackendApplication.java
│   ├── config/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── util/
├── src/main/resources/
│   ├── application.yml
│   └── static/
└── pom.xml
```

## 🚀 Instalación y Configuración
1. Instalar Java 17+
2. Instalar PostgreSQL 15
3. Configurar variables de entorno
4. Ejecutar `mvn spring-boot:run`

## 📱 Endpoints Principales
- `/api/auth/*` - Autenticación
- `/api/users/*` - Gestión de usuarios
- `/api/reports/*` - Sistema de reportes
- `/api/files/*` - Manejo de archivos
- `/api/stats/*` - Estadísticas

## 🔐 Seguridad
- JWT tokens
- Encriptación de contraseñas
- Validación de datos
- CORS configurado
- Rate limiting

## 📈 Monitoreo
- Logs estructurados
- Métricas de performance
- Health checks
- Database monitoring
