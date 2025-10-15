package com.ecovive.model;

/**
 * Enum para las categorías de reportes ambientales
 * 
 * Define los diferentes tipos de problemas ambientales que pueden ser reportados
 * en la aplicación EcoVive Perú, junto con sus puntos ecológicos correspondientes.
 */
public enum ReportCategory {
    
    TRASH("Basura", "🗑️", 10),
    POLLUTION("Contaminación", "💨", 15),
    DEFORESTATION("Tala de Árboles", "🌳", 20),
    WATER_POLLUTION("Contaminación del Agua", "💧", 25),
    AIR_POLLUTION("Contaminación del Aire", "🌫️", 20),
    WILDLIFE("Vida Silvestre", "🦋", 30),
    NOISE("Contaminación Sonora", "🔊", 15),
    SOIL("Contaminación del Suelo", "🌍", 18),
    ANIMAL("Maltrato Animal", "🐾", 25),
    OTHER("Otros", "📝", 5);

    private final String title;
    private final String icon;
    private final Integer ecoPoints;

    ReportCategory(String title, String icon, Integer ecoPoints) {
        this.title = title;
        this.icon = icon;
        this.ecoPoints = ecoPoints;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getEcoPoints() {
        return ecoPoints;
    }

    /**
     * Obtiene la descripción detallada de la categoría
     */
    public String getDescription() {
        switch (this) {
            case TRASH:
                return "Acumulación de basura, residuos sólidos o desechos en espacios públicos";
            case POLLUTION:
                return "Contaminación general del medio ambiente";
            case DEFORESTATION:
                return "Tala ilegal de árboles, destrucción de áreas verdes";
            case WATER_POLLUTION:
                return "Contaminación de ríos, lagos, playas o fuentes de agua";
            case AIR_POLLUTION:
                return "Emisiones contaminantes, humo, malos olores";
            case WILDLIFE:
                return "Problemas que afectan la vida silvestre local";
            case NOISE:
                return "Contaminación sonora excesiva";
            case SOIL:
                return "Contaminación del suelo o terrenos";
            case ANIMAL:
                return "Maltrato o abandono de animales";
            case OTHER:
                return "Otros problemas ambientales no categorizados";
            default:
                return "Categoría de reporte ambiental";
        }
    }

    /**
     * Obtiene consejos para prevenir este tipo de problemas
     */
    public String getPreventionTips() {
        switch (this) {
            case TRASH:
                return "• Usa contenedores de basura apropiados\n• Reduce el uso de plásticos\n• Participa en jornadas de limpieza";
            case POLLUTION:
                return "• Usa transporte público o bicicleta\n• Evita quemar basura\n• Reporta emisiones ilegales";
            case DEFORESTATION:
                return "• Planta árboles nativos\n• Reporta tala ilegal\n• Apoya iniciativas de reforestación";
            case WATER_POLLUTION:
                return "• No arrojes basura al agua\n• Usa productos biodegradables\n• Reporta derrames químicos";
            case AIR_POLLUTION:
                return "• Mantén tu vehículo en buen estado\n• Usa energías renovables\n• Evita fogatas innecesarias";
            case WILDLIFE:
                return "• No alimentes animales silvestres\n• Respeta sus hábitats naturales\n• Reporta actividades ilegales";
            case NOISE:
                return "• Respeta los horarios de silencio\n• Usa auriculares en espacios públicos\n• Mantén el volumen moderado";
            case SOIL:
                return "• Evita el uso excesivo de químicos\n• Practica agricultura sostenible\n• Reporta vertidos ilegales";
            case ANIMAL:
                return "• Adopta, no compres mascotas\n• Esteriliza a tus mascotas\n• Reporta casos de maltrato";
            case OTHER:
                return "• Mantente informado sobre problemas ambientales\n• Participa en iniciativas comunitarias\n• Reporta cualquier problema que observes";
            default:
                return "Sé consciente del medio ambiente y reporta problemas que observes";
        }
    }

    /**
     * Obtiene el color asociado a la categoría para la UI
     */
    public String getColor() {
        switch (this) {
            case TRASH:
                return "#FF9800"; // Naranja
            case POLLUTION:
                return "#9C27B0"; // Púrpura
            case DEFORESTATION:
                return "#8BC34A"; // Verde claro
            case WATER_POLLUTION:
                return "#2196F3"; // Azul
            case AIR_POLLUTION:
                return "#607D8B"; // Azul gris
            case WILDLIFE:
                return "#4CAF50"; // Verde
            case NOISE:
                return "#FF5722"; // Rojo oscuro
            case SOIL:
                return "#795548"; // Marrón
            case ANIMAL:
                return "#E91E63"; // Rosa
            case OTHER:
                return "#9E9E9E"; // Gris
            default:
                return "#757575"; // Gris medio
        }
    }
}
