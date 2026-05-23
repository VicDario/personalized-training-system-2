package backend.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import backend.modelo.Cliente;
import backend.excepciones.ConexionFallidaException;

public class ClienteDAO {

    public List<Cliente> listar() throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            List<Cliente> lista = new ArrayList<>();
            Statement st = bd.getConexion().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clientes ORDER BY nombre");
            while (rs.next()) {
                lista.add(construir(rs));
            }
            return lista;
        } finally {
            bd.cerrar();
        }
    }

    public Cliente buscarPorRut(String rut) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "SELECT * FROM clientes WHERE rut = ?");
            ps.setString(1, rut);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? construir(rs) : null;
        } finally {
            bd.cerrar();
        }
    }

    public Cliente crear(String nombre, String rut) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "INSERT INTO clientes (nombre, rut, semana_actual) VALUES (?, ?, 1)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            ps.setString(2, rut);
            ps.executeUpdate();
            ResultSet generado = ps.getGeneratedKeys();
            generado.next();
            return new Cliente(generado.getInt(1), nombre, rut, 1);
        } finally {
            bd.cerrar();
        }
    }

    public void incrementarSemana(Cliente cliente) throws ConexionFallidaException, SQLException {
        ConexionBD bd = new ConexionBD();
        try {
            bd.conectar();
            PreparedStatement ps = bd.getConexion().prepareStatement(
                "UPDATE clientes SET semana_actual = ? WHERE id = ?");
            ps.setInt(1, cliente.getSemanaActual());
            ps.setInt(2, cliente.getId());
            ps.executeUpdate();
        } finally {
            bd.cerrar();
        }
    }

    private Cliente construir(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("rut"),
            rs.getInt("semana_actual"));
    }
}
