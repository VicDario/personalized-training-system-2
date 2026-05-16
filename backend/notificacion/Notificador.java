package backend.notificacion;

import javax.swing.SwingUtilities;

// Bus de eventos singleton: distribuye notificaciones a los suscriptores registrados
public class Notificador {

    private static final int MAX_SUSCRIPTORES = 10;

    // Instancia única compartida por todo el sistema
    private static final Notificador INSTANCIA = new Notificador();

    private Suscriptor[] suscriptores;
    private int cantidad;

    private Notificador() {
        suscriptores = new Suscriptor[MAX_SUSCRIPTORES];
        cantidad = 0;
    }

    public static Notificador getInstancia() { return INSTANCIA; }

    // Registra un suscriptor si hay espacio disponible
    public void suscribir(Suscriptor s) {
        if (cantidad < suscriptores.length) {
            suscriptores[cantidad] = s;
            cantidad++;
        }
    }

    // Elimina un suscriptor y compacta el arreglo
    public void desuscribir(Suscriptor s) {
        for (int i = 0; i < cantidad; i++) {
            if (suscriptores[i] == s) {
                for (int j = i; j < cantidad - 1; j++) {
                    suscriptores[j] = suscriptores[j + 1];
                }
                suscriptores[cantidad - 1] = null;
                cantidad--;
                break;
            }
        }
    }

    // Envía el evento a todos los suscriptores en el hilo de Swing (EDT)
    public void notificar(final String evento, final Object datos) {
        for (int i = 0; i < cantidad; i++) {
            final Suscriptor s = suscriptores[i];
            SwingUtilities.invokeLater(() -> s.actualizar(evento, datos));
        }
    }
}
