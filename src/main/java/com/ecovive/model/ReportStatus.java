package com.ecovive.model;

/**
 * Enum para los estados de los reportes ambientales
 * 
 * Define los diferentes estados que puede tener un reporte durante su ciclo de vida
 * en el sistema EcoVive Perú.
 */
public enum ReportStatus {
    
    PENDING("Pendiente", "⏳", "El reporte está esperando revisión"),
    IN_PROGRESS("En Progreso", "🔄", "El reporte está siendo procesado"),
    RESOLVED("Resuelto", "✅", "El problema ha sido solucionado"),
    VERIFIED("Verificado", "🔍", "El reporte ha sido verificado por autoridades"),
    REJECTED("Rechazado", "❌", "El reporte fue rechazado por no cumplir criterios"),
    DUPLICATE("Duplicado", "📋", "Ya existe un reporte similar para esta ubicación");

    private final String title;
    private final String icon;
    private final String description;

    ReportStatus(String title, String icon, String description) {
        this.title = title;
        this.icon = icon;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Obtiene el color asociado al estado para la UI
     */
    public String getColor() {
        switch (this) {
            case PENDING:
                return "#FF9800"; // Naranja
            case IN_PROGRESS:
                return "#2196F3"; // Azul
            case RESOLVED:
                return "#4CAF50"; // Verde
            case VERIFIED:
                return "#9C27B0"; // Púrpura
            case REJECTED:
                return "#F44336"; // Rojo
            case DUPLICATE:
                return "#9E9E9E"; // Gris
            default:
                return "#757575"; // Gris medio
        }
    }

    /**
     * Verifica si el estado permite transiciones
     */
    public boolean canTransitionTo(ReportStatus newStatus) {
        switch (this) {
            case PENDING:
                return newStatus == IN_PROGRESS || newStatus == REJECTED || newStatus == DUPLICATE;
            case IN_PROGRESS:
                return newStatus == RESOLVED || newStatus == VERIFIED || newStatus == REJECTED;
            case RESOLVED:
                return newStatus == VERIFIED;
            case VERIFIED:
                return false; // Estado final
            case REJECTED:
                return newStatus == PENDING; // Puede ser reconsiderado
            case DUPLICATE:
                return newStatus == PENDING; // Puede ser reconsiderado
            default:
                return false;
        }
    }

    /**
     * Obtiene el mensaje de estado para el usuario
     */
    public String getUserMessage() {
        switch (this) {
            case PENDING:
                return "Tu reporte está siendo revisado por nuestro equipo. Te notificaremos cuando sea procesado.";
            case IN_PROGRESS:
                return "Tu reporte está siendo atendido por las autoridades competentes. Gracias por tu colaboración.";
            case RESOLVED:
                return "¡Excelente! El problema reportado ha sido solucionado. Gracias por ayudar al medio ambiente.";
            case VERIFIED:
                return "Tu reporte ha sido verificado y confirmado por las autoridades. ¡Buen trabajo!";
            case REJECTED:
                return "Lamentablemente tu reporte no cumple con nuestros criterios. Puedes crear uno nuevo con más detalles.";
            case DUPLICATE:
                return "Ya existe un reporte similar para esta ubicación. Tu contribución es valorada.";
            default:
                return "Estado del reporte actualizado.";
        }
    }
}
