package backend.modelo;

// Niveles de intensidad disponibles para un ejercicio
public enum NivelIntensidad {
    BASICO, INTERMEDIO, AVANZADO, ALTO_RENDIMIENTO;

    // Representación legible para la UI
    public String toString() {
        switch (this) {
            case BASICO:           return "Básico";
            case INTERMEDIO:       return "Intermedio";
            case AVANZADO:         return "Avanzado";
            case ALTO_RENDIMIENTO: return "Alto rendimiento";
            default:               return name();
        }
    }
}
