package backend.modelo;

// Ejercicio cardiovascular
public class EjercicioCardiovascular extends Ejercicio {

    private int pulsoRecomendado;

    public EjercicioCardiovascular() {
        super(); //llama al constructor de la clase padre (Ejercicio)
        setTipo(TipoEjercicio.CARDIOVASCULAR);
        pulsoRecomendado = 0;
    }

    public int getPulsoRecomendado() { return pulsoRecomendado; }
    public void setPulsoRecomendado(int pulsoRecomendado) { this.pulsoRecomendado = pulsoRecomendado; }
}
