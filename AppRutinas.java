import frontend.VentanaFitnessPro;
import backend.datos.CargadorEjercicios;
import javax.swing.SwingUtilities;

// Punto de entrada de la aplicación
public class AppRutinas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaFitnessPro();
            // Carga de ejercicios, se hace en hilo separado para no bloquear la UI
            new Thread(() -> new CargadorEjercicios().cargar()).start();
        });
    }
}
