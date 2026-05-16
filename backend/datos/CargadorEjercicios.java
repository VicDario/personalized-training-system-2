package backend.datos;

import java.sql.*;
import backend.modelo.*;
import backend.excepciones.*;
import backend.notificacion.*;

// Lee todos los ejercicios desde la BD y notifica el resultado al frontend
public class CargadorEjercicios {

    private ConexionBD bd;
    private int cantidadCargada;

    public CargadorEjercicios() {
        bd = new ConexionBD();
        cantidadCargada = 0;
    }

    public void cargar() {
        Notificador bus = Notificador.getInstancia();
        Ejercicio[] ejercicios = null;

        try {
            bd.conectar();
            Connection con = bd.getConexion();

            // Cuenta el total de registros (para dimensionar el arreglo) 
            Statement stConteo = con.createStatement();
            ResultSet rsConteo = stConteo.executeQuery("SELECT COUNT(*) FROM ejercicios");
            rsConteo.next();
            int total = rsConteo.getInt(1);
            ejercicios = new Ejercicio[total];

            // Lee cada registro de la tabla
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT codigo, nombre, tipo, nivel, tiempo_estimado, descripcion, semana_ultimo_uso " +
                "FROM ejercicios"
            );

            int i = 0;
            while (rs.next()) {
                String codigo      = rs.getString("codigo");
                String nombre      = rs.getString("nombre");
                String tipoStr     = rs.getString("tipo");
                String nivelStr    = rs.getString("nivel");
                int    tiempo      = rs.getInt("tiempo_estimado");
                String descripcion = rs.getString("descripcion");
                int    semana      = rs.getInt("semana_ultimo_uso");

                // Valida que los campos obligatorios no esten vacíos
                if (estaVacio(codigo) || estaVacio(nombre) || estaVacio(tipoStr) || estaVacio(nivelStr)) {
                    throw new InformacionIncompletaException(
                        "Registro con campos obligatorios vacíos (codigo=" + codigo + ")."
                    );
                }

                // Convierte el string a -- enum TipoEjercicio
                TipoEjercicio tipo;
                try {
                    tipo = TipoEjercicio.valueOf(tipoStr);
                } catch (IllegalArgumentException ex) {
                    throw new FormatoInvalidoException("Tipo de ejercicio inválido: '" + tipoStr + "'.");
                }

                // Convierte el string a -- enum NivelIntensidad
                NivelIntensidad nivel;
                try {
                    nivel = NivelIntensidad.valueOf(nivelStr);
                } catch (IllegalArgumentException ex) {
                    throw new FormatoInvalidoException("Nivel de intensidad inválido: '" + nivelStr + "'.");
                }

                // Instancia la subclase correcta (dependiendo del tipo)
                Ejercicio e = (tipo == TipoEjercicio.CARDIOVASCULAR)
                    ? new EjercicioCardiovascular()
                    : new EjercicioFuerza();

                e.setCodigo(codigo);
                e.setNombre(nombre);
                e.setNivel(nivel);
                e.setTiempoEstimado(tiempo);
                e.setDescripcion(descripcion != null ? descripcion : "");
                e.setSemanaUltimoUso(semana);

                ejercicios[i] = e;
                i++;
            }

            cantidadCargada = i;
            bus.notificar(EventoSistema.CARGA_OK, ejercicios);

        } catch (ConexionFallidaException | FormatoInvalidoException | InformacionIncompletaException e) {
            bus.notificar(EventoSistema.CARGA_ERROR, e.getMessage());
        } catch (SQLException e) {
            bus.notificar(EventoSistema.CARGA_ERROR, "Error SQL: " + e.getMessage());
        } finally {
            bd.cerrar();
        }
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public int getCantidadCargada() { return cantidadCargada; }
}
