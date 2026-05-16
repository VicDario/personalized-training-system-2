package backend.excepciones;

// Ocurre en caso de que un registro tiene campos obligatorios vacios, nulos.
public class InformacionIncompletaException extends Exception {

    public InformacionIncompletaException(String mensaje) {
        super(mensaje);
    }
}
