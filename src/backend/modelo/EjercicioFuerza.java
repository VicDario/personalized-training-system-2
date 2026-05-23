package backend.modelo;

// Ejercicio fuerza
public class EjercicioFuerza extends Ejercicio {

    private int peso;

    public EjercicioFuerza() {
        super();
        setTipo(TipoEjercicio.FUERZA);
        peso = 0;
    }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }
}
