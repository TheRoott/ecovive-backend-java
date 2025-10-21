package com.reciclacontigo.model;

public enum ReportStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En Progreso"),
    RESOLVED("Resuelto"),
    VERIFIED("Verificado");

    private final String title;

    ReportStatus(String title) {
        this.title = title;
    }

    public String getTitle() { return title; }
}
