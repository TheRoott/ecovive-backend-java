package com.reciclacontigo.controller;

import com.reciclacontigo.model.Report;
import com.reciclacontigo.model.ReportCategory;
import com.reciclacontigo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/category/{category}")
    public ResponseEntity<List<Report>> getReportsByCategory(@PathVariable String category) {
        try {
            ReportCategory reportCategory = ReportCategory.valueOf(category.toUpperCase());
            List<Report> reports = reportService.getReportsByCategory(reportCategory);
            return ResponseEntity.ok(reports);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reports/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByUser(@PathVariable String userId) {
        List<Report> reports = reportService.getReportsByUser(userId);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/reports")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.createReport(report);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/reports/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        report.setId(id);
        Report updatedReport = reportService.updateReport(report);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (!reportService.getReportById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats() {
        return ResponseEntity.ok(new Object() {
            public final Long totalReports = reportService.getReportCount();
            public final Long trashReports = reportService.getReportCountByCategory(ReportCategory.TRASH);
            public final Long pollutionReports = reportService.getReportCountByCategory(ReportCategory.POLLUTION);
            public final Long deforestationReports = reportService.getReportCountByCategory(ReportCategory.DEFORESTATION);
        });
    }

    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(new Object() {
            public final String status = "UP";
            public final String service = "Recicla Contigo API";
            public final String version = "1.0.0";
        });
    }
}
