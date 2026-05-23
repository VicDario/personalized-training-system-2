package backend.datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import backend.modelo.Cliente;
import backend.modelo.Ejercicio;
import backend.modelo.Rutina;
import backend.excepciones.ConexionFallidaException;

public class RutinaDAO {

    public void crear(Cliente cliente, Rutina rutina) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "INSERT INTO rutinas (cliente_id, semana) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, cliente.getId());
            ps.setInt(2, rutina.getSemana());
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            generado.next();
            int rutinaId = generado.getInt(1);

            PreparedStatement detalle = bd.getConexion().prepareStatement(
                "INSERT INTO rutina_ejercicios (rutina_id, ejercicio_codigo) VALUES (?, ?)");
            for (int i = 0; i < rutina.getCantidadEjercicios(); i++) {
                detalle.setInt(1, rutinaId);
                detalle.setString(2, rutina.getEjercicios()[i].getCodigo());
                detalle.addBatch();
            }
            detalle.executeBatch();
        } finally {
            bd.cerrar();
        }
    }

    public List<Rutina> listarPorCliente(Cliente cliente) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            List<Rutina> rutinas = new ArrayList<>();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT id, semana FROM rutinas WHERE cliente_id = ? ORDER BY semana");
            ps.setInt(1, cliente.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                List<Ejercicio> ejercicios = ejerciciosDeRutina(bd, rs.getInt("id"));
                Rutina rutina = new Rutina(ejercicios.size());
                rutina.setCliente(cliente.getNombre());
                rutina.setSemana(rs.getInt("semana"));
                for (Ejercicio e : ejercicios) {
                    rutina.agregarEjercicio(e);
                }
                rutinas.add(rutina);
            }
            return rutinas;
        } finally {
            bd.cerrar();
        }
    }

    public List<Ejercicio> ejerciciosUltimaRutina(Cliente cliente) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT id FROM rutinas WHERE cliente_id = ? ORDER BY semana DESC, id DESC LIMIT 1");
            ps.setInt(1, cliente.getId());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return new ArrayList<>();
            }
            return ejerciciosDeRutina(bd, rs.getInt("id"));
        } finally {
            bd.cerrar();
        }
    }

    private List<Ejercicio> ejerciciosDeRutina(ConexionBD bd, int rutinaId) throws SQLException {
        List<Ejercicio> ejercicios = new ArrayList<>();
        PreparedStatement ps = bd.getConexion().prepareStatement(
            "SELECT e.* FROM rutina_ejercicios re JOIN ejercicios e ON e.codigo = re.ejercicio_codigo " +
            "WHERE re.rutina_id = ? ORDER BY e.codigo");
        ps.setInt(1, rutinaId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ejercicios.add(EjercicioDAO.construir(rs));
        }
        return ejercicios;
    }
}
