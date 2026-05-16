package backend.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import backend.excepciones.ConexionFallidaException;

// Gestiona la conexion a la base de datos MySQL
public class ConexionBD {

    // Credenciales y URL de conexión
    private static final String URL     = "jdbc:mysql://localhost:3306/fitnesspro";
    private static final String USUARIO = "root";
    private static final String CLAVE   = "";

    private Connection conexion;

    public ConexionBD() {
        conexion = null;
    }

    // Abre la conexión; lanza excepción si la BD no está disponible
    public void conectar() throws ConexionFallidaException {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            throw new ConexionFallidaException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    // Cierra la conexión si está abierta
    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            // cierre silencioso
        }
    }

    public Connection getConexion() { return conexion; }
}
