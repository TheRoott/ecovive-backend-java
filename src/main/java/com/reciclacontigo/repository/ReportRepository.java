package com.reciclacontigo.repository;

import com.reciclacontigo.model.Report;
import com.reciclacontigo.model.ReportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    List<Report> findByCategoryOrderByCreatedAtDesc(ReportCategory category);
    
    List<Report> findByUserIdOrderByCreatedAtDesc(String userId);
    
    @Query("SELECT r FROM Report r ORDER BY r.createdAt DESC")
    List<Report> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(r) FROM Report r WHERE r.category = :category")
    Long countByCategory(@Param("category") ReportCategory category);
    
    @Query("SELECT COUNT(r) FROM Report r WHERE r.userId = :userId")
    Long countByUserId(@Param("userId") String userId);
    
    @Query("SELECT SUM(r.ecoPoints) FROM Report r WHERE r.userId = :userId")
    Integer sumEcoPointsByUserId(@Param("userId") String userId);
}
