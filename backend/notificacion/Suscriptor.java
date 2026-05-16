package backend.notificacion;

// Contrato para recibir notificaciones del sistema
public interface Suscriptor {
    void actualizar(String evento, Object datos);
}
