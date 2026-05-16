package backend.excepciones;

// Lanzada cuando un registro tiene campos obligatorios vacíos o nulos
public class InformacionIncompletaException extends Exception {

    public InformacionIncompletaException(String mensaje) {
        super(mensaje);
    }
}
