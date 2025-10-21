package com.reciclacontigo.service;

import com.reciclacontigo.model.Report;
import com.reciclacontigo.model.ReportCategory;
import com.reciclacontigo.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAllOrderByCreatedAtDesc();
    }

    public List<Report> getReportsByCategory(ReportCategory category) {
        return reportRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public List<Report> getReportsByUser(String userId) {
        return reportRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Report createReport(Report report) {
        // Calcular puntos según categoría y si tiene foto
        int points = calculateEcoPoints(report.getCategory(), report.getPhotoPath() != null);
        report.setEcoPoints(points);
        
        return reportRepository.save(report);
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report updateReport(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public Long getReportCount() {
        return reportRepository.count();
    }

    public Long getReportCountByCategory(ReportCategory category) {
        return reportRepository.countByCategory(category);
    }

    public Long getReportCountByUser(String userId) {
        return reportRepository.countByUserId(userId);
    }

    public Integer getUserTotalPoints(String userId) {
        Integer total = reportRepository.sumEcoPointsByUserId(userId);
        return total != null ? total : 0;
    }

    private int calculateEcoPoints(ReportCategory category, boolean hasPhoto) {
        int basePoints = category.getEcoPoints();
        int photoBonus = hasPhoto ? 5 : 0;
        return basePoints + photoBonus;
    }
}
