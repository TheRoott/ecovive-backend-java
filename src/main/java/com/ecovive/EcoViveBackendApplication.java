package com.ecovive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicación principal de EcoVive Perú Backend
 * 
 * Sistema de gestión de reportes ambientales con capacidad para 200+ usuarios
 * 
 * Características principales:
 * - Autenticación JWT
 * - Gestión de usuarios
 * - Sistema de reportes ambientales
 * - Almacenamiento de fotos (AWS S3)
 * - Base de datos PostgreSQL
 * - API REST documentada
 * 
 * @author EcoVive Perú Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class EcoViveBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoViveBackendApplication.class, args);
        System.out.println("🌱 EcoVive Perú Backend iniciado correctamente!");
        System.out.println("📱 API disponible en: http://localhost:8080/api");
        System.out.println("📚 Documentación: http://localhost:8080/api/swagger-ui.html");
        System.out.println("💚 Listo para recibir reportes ambientales!");
    }
}
