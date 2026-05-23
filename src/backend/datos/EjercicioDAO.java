package backend.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import backend.modelo.*;
import backend.excepciones.ConexionFallidaException;

public class EjercicioDAO {

    public List<Ejercicio> listar() throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            List<Ejercicio> lista = new ArrayList<>();
            Statement st = bd.getConexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ejercicios ORDER BY codigo");
            while (rs.next()) {
                lista.add(construir(rs));
            }
            return lista;
        } finally {
            bd.cerrar();
        }
    }

    public List<Ejercicio> buscarPorNivel(NivelIntensidad nivel) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            List<Ejercicio> lista = new ArrayList<>();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT * FROM ejercicios WHERE nivel = ? ORDER BY codigo");
            ps.setString(1, nivel.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(construir(rs));
            }
            return lista;
        } finally {
            bd.cerrar();
        }
    }

    public Ejercicio obtener(String codigo) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT * FROM ejercicios WHERE codigo = ?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? construir(rs) : null;
        } finally {
            bd.cerrar();
        }
    }

    public void crear(Ejercicio e) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "INSERT INTO ejercicios (codigo, nombre, tipo, nivel, tiempo_estimado, descripcion, pulso_recomendado, peso) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, e.getCodigo());
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getTipo().name());
            ps.setString(4, e.getNivel().name());
            ps.setInt(5, e.getTiempoEstimado());
            ps.setString(6, e.getDescripcion());
            asignarPulsoPeso(ps, 7, 8, e);
            ps.executeUpdate();
        } finally {
            bd.cerrar();
        }
    }

    public void actualizar(Ejercicio e) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "UPDATE ejercicios SET nombre = ?, nivel = ?, tiempo_estimado = ?, descripcion = ?, " +
                "pulso_recomendado = ?, peso = ? WHERE codigo = ?");
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getNivel().name());
            ps.setInt(3, e.getTiempoEstimado());
            ps.setString(4, e.getDescripcion());
            asignarPulsoPeso(ps, 5, 6, e);
            ps.setString(7, e.getCodigo());
            ps.executeUpdate();
        } finally {
            bd.cerrar();
        }
    }

    public void eliminar(String codigo) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "DELETE FROM ejercicios WHERE codigo = ?");
            ps.setString(1, codigo);
            ps.executeUpdate();
        } finally {
            bd.cerrar();
        }
    }

    public String siguienteCodigo(TipoEjercicio tipo) throws ConexionFallidaException, SQLException {
        String prefijo = tipo == TipoEjercicio.CARDIOVASCULAR ? "C" : "F";
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT codigo FROM ejercicios WHERE codigo LIKE ? ORDER BY codigo DESC LIMIT 1");
            ps.setString(1, prefijo + "%");
            ResultSet rs = ps.executeQuery();
            int numero = rs.next() ? Integer.parseInt(rs.getString(1).substring(1)) + 1 : 1;
            return String.format("%s%03d", prefijo, numero);
        } finally {
            bd.cerrar();
        }
    }

    private void asignarPulsoPeso(PreparedStatement ps, int indicePulso, int indicePeso, Ejercicio e) throws SQLException {
        if (e instanceof EjercicioCardiovascular) {
            ps.setInt(indicePulso, ((EjercicioCardiovascular) e).getPulsoRecomendado());
            ps.setNull(indicePeso, java.sql.Types.INTEGER);
        } else {
            ps.setNull(indicePulso, java.sql.Types.INTEGER);
            ps.setInt(indicePeso, ((EjercicioFuerza) e).getPeso());
        }
    }

    static Ejercicio construir(ResultSet rs) throws SQLException {
        TipoEjercicio tipo = TipoEjercicio.valueOf(rs.getString("tipo"));
        Ejercicio e;
        if (tipo == TipoEjercicio.CARDIOVASCULAR) {
            EjercicioCardiovascular cardio = new EjercicioCardiovascular();
            cardio.setPulsoRecomendado(rs.getInt("pulso_recomendado"));
            e = cardio;
        } else {
            EjercicioFuerza fuerza = new EjercicioFuerza();
            fuerza.setPeso(rs.getInt("peso"));
            e = fuerza;
        }
        e.setCodigo(rs.getString("codigo"));
        e.setNombre(rs.getString("nombre"));
        e.setNivel(NivelIntensidad.valueOf(rs.getString("nivel")));
        e.setTiempoEstimado(rs.getInt("tiempo_estimado"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setSemanaUltimoUso(rs.getInt("semana_ultimo_uso"));
        return e;
    }
}
