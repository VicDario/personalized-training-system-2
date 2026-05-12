package backend;

public class Rutina {

    private String cliente;
    private Ejercicio[] ejercicios;
    private int cantidadEjercicios;

    public Rutina(int maxEjercicios) {
        cliente = "";
        ejercicios = new Ejercicio[maxEjercicios];
        cantidadEjercicios = 0;
    }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Ejercicio[] getEjercicios() { return ejercicios; }
    public int getCantidadEjercicios() { return cantidadEjercicios; }

    public void agregarEjercicio(Ejercicio e) {
        if (cantidadEjercicios < ejercicios.length) {
            ejercicios[cantidadEjercicios] = e;
            cantidadEjercicios++;
        }
    }

    public int getTiempoTotal() {
        int total = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            total += ejercicios[i].getTiempoEstimado();
        }
        return total;
    }

    public int contarPorTipo(TipoEjercicio tipo) {
        int count = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            if (ejercicios[i].getTipo() == tipo) count++;
        }
        return count;
    }

    public int contarPorNivel(NivelIntensidad nivel) {
        int count = 0;
        for (int i = 0; i < cantidadEjercicios; i++) {
            if (ejercicios[i].getNivel() == nivel) count++;
        }
        return count;
    }

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
