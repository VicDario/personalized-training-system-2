import frontend.MenuPrincipal;
import javax.swing.SwingUtilities;

// Punto de entrada de la aplicación
public class AppRutinas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
