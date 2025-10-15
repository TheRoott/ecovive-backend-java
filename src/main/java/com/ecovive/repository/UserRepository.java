package com.ecovive.repository;

import com.ecovive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad User
 * 
 * Proporciona métodos de acceso a datos para usuarios con consultas optimizadas
 * para el sistema EcoVive Perú.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por email
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuarios activos
     */
    List<User> findByIsActiveTrue();

    /**
     * Busca usuarios por ubicación
     */
    List<User> findByLocation(String location);

    /**
     * Busca usuarios con más puntos ecológicos
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.ecoPoints DESC")
    List<User> findTopEcoUsers();

    /**
     * Busca usuarios por rango de puntos
     */
    @Query("SELECT u FROM User u WHERE u.ecoPoints BETWEEN :minPoints AND :maxPoints AND u.isActive = true")
    List<User> findByEcoPointsRange(@Param("minPoints") Integer minPoints, @Param("maxPoints") Integer maxPoints);

    /**
     * Busca usuarios que han hecho login recientemente
     */
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :since AND u.isActive = true")
    List<User> findRecentlyActiveUsers(@Param("since") LocalDateTime since);

    /**
     * Cuenta usuarios activos
     */
    long countByIsActiveTrue();

    /**
     * Cuenta usuarios por ubicación
     */
    long countByLocationAndIsActiveTrue(String location);

    /**
     * Busca usuarios con email verificado
     */
    List<User> findByEmailVerifiedTrueAndIsActiveTrue();

    /**
     * Busca usuario por token de verificación
     */
    Optional<User> findByVerificationToken(String token);

    /**
     * Busca usuarios con más reportes
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.reportsCount DESC")
    List<User> findTopReporters();

    /**
     * Busca usuarios por nivel
     */
    List<User> findByLevelAndIsActiveTrue(String level);

    /**
     * Estadísticas de usuarios por nivel
     */
    @Query("SELECT u.level, COUNT(u) FROM User u WHERE u.isActive = true GROUP BY u.level")
    List<Object[]> getUserStatsByLevel();

    /**
     * Estadísticas de usuarios por ubicación
     */
    @Query("SELECT u.location, COUNT(u) FROM User u WHERE u.isActive = true GROUP BY u.location")
    List<Object[]> getUserStatsByLocation();

    /**
     * Busca usuarios con patrones de búsqueda
     */
    @Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND u.isActive = true")
    List<User> searchUsers(@Param("search") String search);
}
