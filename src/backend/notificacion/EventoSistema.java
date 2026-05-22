package backend.notificacion;

// Constantes de eventos que circulan por el Notificador
public class EventoSistema {

    public static final String CARGA_OK        = "CARGA_OK";
    public static final String CARGA_ERROR     = "CARGA_ERROR";
    public static final String RUTINA_GENERADA = "RUTINA_GENERADA";
    public static final String RUTINA_ERROR    = "RUTINA_ERROR";

    // Clase utilitaria: no se instancia
    private EventoSistema() {}
}
