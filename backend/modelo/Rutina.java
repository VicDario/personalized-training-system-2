package backend.modelo;

// Conjunto de ejercicios asignada a un cliente
public class Rutina {

    private String cliente;
    private Ejercicio[] ejercicios;
    private int cantidadEjercicios;

    // Dimensiona el arreglo al máx. de ejercicios permitidos
    public Rutina(int maxEjercicios) {
        cliente = "";
        ejercicios = new Ejercicio[maxEjercicios];
        cantidadEjercicios = 0;
    }

    // Getters y setters
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Ejercicio[] getEjercicios() { return ejercicios; }
    public int getCantidadEjercicios() { return cantidadEjercicios; }

    // Agrega un ejercicio si hay espacio disponible
    public void agregarEjercicio(Ejercicio e) {
        if (cantidadEjercicios < ejercicios.length) {
            ejercicios[cantidadEjercicios] = e;
            cantidadEjercicios++;
        }
    }

    // Suma el tiempo estimado de todos los ejercicios de la rutina
    public int getTiempoTotal() {
        int total = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            total += ejercicios[i].getTiempoEstimado();
        }
        return total;
    }

    // Cuenta ejercicios que corresponden al TIPO indicado
    public int contarPorTipo(TipoEjercicio tipo) {
        int count = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            if (ejercicios[i].getTipo() == tipo) count++;
        }
        return count;
    }

    // Cuenta ejercicios que corresponden al NIVEL indicado
    public int contarPorNivel(NivelIntensidad nivel) {
        int count = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            if (ejercicios[i].getNivel() == nivel) count++;
        }
        return count;
    }

    // print del resumen 
    public void mostrarInfo() {
        System.out.println("=== Resumen de rutina ===");
        System.out.println("Cliente: " + getCliente());
        System.out.println("Ejercicios cardiovasculares: " + contarPorTipo(TipoEjercicio.CARDIOVASCULAR));
        System.out.println("Ejercicios de fuerza:        " + contarPorTipo(TipoEjercicio.FUERZA));
        System.out.println("Tiempo total:                " + getTiempoTotal() + " min");
        for (NivelIntensidad n : NivelIntensidad.values()) {
            System.out.println("Nivel " + n + ": " + contarPorNivel(n));
        }
    }
}
