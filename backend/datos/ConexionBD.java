package backend.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
import backend.excepciones.ConexionFallidaException;

// Gestiona la conexion a la base de datos MySQL
public class ConexionBD {

    // Carga las variables desde el archivo .env (no falla si no existe)
    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    // Credenciales y URL para conectar (cargadas desde .env)
    private static final String URL     = DOTENV.get("FITNESSPRO_DB_URL", "jdbc:mysql://localhost:3306/fitnesspro");
    private static final String USUARIO = DOTENV.get("FITNESSPRO_DB_USER", "root");
    private static final String CLAVE   = DOTENV.get("FITNESSPRO_DB_PASSWORD", "");

    private Connection conexion;

    public ConexionBD() {
        conexion = null;
    }

    // Empieza la conexion (lanza excepcion si la BD no esta disponible)
    public void conectar() throws ConexionFallidaException {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            throw new ConexionFallidaException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }

    // Cierra la conexion
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
