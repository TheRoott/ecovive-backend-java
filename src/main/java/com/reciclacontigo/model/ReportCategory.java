package com.reciclacontigo.model;

public enum ReportCategory {
    TRASH("Basura", "🗑️", 20),
    POLLUTION("Contaminación", "💨", 30),
    DEFORESTATION("Tala de Árboles", "🌳", 35),
    WATER_POLLUTION("Contaminación del Agua", "💧", 40),
    AIR_POLLUTION("Contaminación del Aire", "🌫️", 30),
    WILDLIFE("Vida Silvestre", "🦋", 40),
    OTHER("Otros", "📝", 15);

    private final String title;
    private final String icon;
    private final Integer ecoPoints;

    ReportCategory(String title, String icon, Integer ecoPoints) {
        this.title = title;
        this.icon = icon;
        this.ecoPoints = ecoPoints;
    }

    public String getTitle() { return title; }
    public String getIcon() { return icon; }
    public Integer getEcoPoints() { return ecoPoints; }
}
