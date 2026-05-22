package backend.excepciones;

// Se lanza cuando la conexión a la base de datos falla
public class ConexionFallidaException extends Exception {

    public ConexionFallidaException(String mensaje) {
        super(mensaje);
    }
}
