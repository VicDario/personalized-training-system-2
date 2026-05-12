package backend;

public enum TipoEjercicio {
    CARDIOVASCULAR, FUERZA;

    public String toString() {
        switch (this) {
            case CARDIOVASCULAR: return "Cardiovascular";
            case FUERZA:         return "Fuerza";
            default:             return name();
        }
    }
}
