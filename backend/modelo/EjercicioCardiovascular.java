package backend.modelo;

// Ejercicio cardiovascular
public class EjercicioCardiovascular extends Ejercicio {

    public EjercicioCardiovascular() {
        super(); //llama al constructor de la clase padre (Ejercicio)
        setTipo(TipoEjercicio.CARDIOVASCULAR);
    }
}
