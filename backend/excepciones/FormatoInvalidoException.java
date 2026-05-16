package backend.excepciones;

// Lanzada cuando un valor en la BD no coincide con el enum esperado
public class FormatoInvalidoException extends Exception {

    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
