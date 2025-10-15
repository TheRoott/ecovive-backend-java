package com.ecovive.repository;

import com.ecovive.model.Report;
import com.ecovive.model.ReportCategory;
import com.ecovive.model.ReportStatus;
import com.ecovive.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Report
 * 
 * Proporciona métodos de acceso a datos para reportes ambientales con consultas
 * optimizadas para el sistema EcoVive Perú.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * Busca reportes por usuario
     */
    List<Report> findByUserOrderByCreatedAtDesc(User user);

    /**
     * Busca reportes por categoría
     */
    List<Report> findByCategoryOrderByCreatedAtDesc(ReportCategory category);

    /**
     * Busca reportes por estado
     */
    List<Report> findByStatusOrderByCreatedAtDesc(ReportStatus status);

    /**
     * Busca reportes públicos
     */
    List<Report> findByIsPublicTrueOrderByCreatedAtDesc();

    /**
     * Busca reportes por ubicación (radio)
     */
    @Query("SELECT r FROM Report r WHERE " +
           "ST_Distance_Sphere(ST_Point(r.longitude, r.latitude), ST_Point(:longitude, :latitude)) <= :radiusInMeters " +
           "AND r.isPublic = true " +
           "ORDER BY r.createdAt DESC")
    List<Report> findReportsNearLocation(@Param("latitude") Double latitude, 
                                       @Param("longitude") Double longitude, 
                                       @Param("radiusInMeters") Double radiusInMeters);

    /**
     * Busca reportes recientes
     */
    @Query("SELECT r FROM Report r WHERE r.createdAt >= :since AND r.isPublic = true ORDER BY r.createdAt DESC")
    List<Report> findRecentReports(@Param("since") LocalDateTime since);

    /**
     * Busca reportes por usuario con paginación
     */
    Page<Report> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    /**
     * Busca reportes públicos con paginación
     */
    Page<Report> findByIsPublicTrueOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Busca reportes por categoría y estado
     */
    List<Report> findByCategoryAndStatusOrderByCreatedAtDesc(ReportCategory category, ReportStatus status);

    /**
     * Busca reportes verificados
     */
    List<Report> findByVerifiedTrueOrderByVerifiedAtDesc();

    /**
     * Cuenta reportes por categoría
     */
    @Query("SELECT r.category, COUNT(r) FROM Report r GROUP BY r.category")
    List<Object[]> countReportsByCategory();

    /**
     * Cuenta reportes por estado
     */
    @Query("SELECT r.status, COUNT(r) FROM Report r GROUP BY r.status")
    List<Object[]> countReportsByStatus();

    /**
     * Cuenta reportes por usuario
     */
    long countByUser(User user);

    /**
     * Cuenta reportes por usuario en un período
     */
    @Query("SELECT COUNT(r) FROM Report r WHERE r.user = :user AND r.createdAt >= :since")
    long countReportsByUserSince(@Param("user") User user, @Param("since") LocalDateTime since);

    /**
     * Busca reportes por prioridad
     */
    List<Report> findByPriorityOrderByCreatedAtDesc(Integer priority);

    /**
     * Busca reportes críticos (prioridad alta)
     */
    @Query("SELECT r FROM Report r WHERE r.priority >= 3 ORDER BY r.priority DESC, r.createdAt DESC")
    List<Report> findCriticalReports();

    /**
     * Estadísticas de reportes por fecha
     */
    @Query("SELECT DATE(r.createdAt), COUNT(r) FROM Report r WHERE r.createdAt >= :since GROUP BY DATE(r.createdAt) ORDER BY DATE(r.createdAt)")
    List<Object[]> getReportStatsByDate(@Param("since") LocalDateTime since);

    /**
     * Estadísticas de reportes por ubicación
     */
    @Query("SELECT r.address, COUNT(r) FROM Report r WHERE r.address IS NOT NULL GROUP BY r.address ORDER BY COUNT(r) DESC")
    List<Object[]> getReportStatsByLocation();

    /**
     * Busca reportes duplicados (misma ubicación y categoría)
     */
    @Query("SELECT r FROM Report r WHERE r.category = :category AND " +
           "ST_Distance_Sphere(ST_Point(r.longitude, r.latitude), ST_Point(:longitude, :latitude)) <= 100 " +
           "AND r.createdAt >= :since")
    List<Report> findDuplicateReports(@Param("category") ReportCategory category,
                                    @Param("latitude") Double latitude,
                                    @Param("longitude") Double longitude,
                                    @Param("since") LocalDateTime since);

    /**
     * Busca reportes con búsqueda de texto
     */
    @Query("SELECT r FROM Report r WHERE " +
           "(LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(r.address) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND r.isPublic = true " +
           "ORDER BY r.createdAt DESC")
    List<Report> searchReports(@Param("search") String search);

    /**
     * Busca reportes por rango de fechas
     */
    @Query("SELECT r FROM Report r WHERE r.createdAt BETWEEN :startDate AND :endDate AND r.isPublic = true ORDER BY r.createdAt DESC")
    List<Report> findReportsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Busca reportes resueltos recientemente
     */
    @Query("SELECT r FROM Report r WHERE r.status = 'RESOLVED' AND r.resolvedAt >= :since ORDER BY r.resolvedAt DESC")
    List<Report> findRecentlyResolvedReports(@Param("since") LocalDateTime since);
}
