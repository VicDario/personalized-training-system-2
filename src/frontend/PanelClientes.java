package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import backend.modelo.Cliente;
import backend.datos.ClienteDAO;

public class PanelClientes extends JPanel {

    private final VentanaFitnessPro ventana;
    private final ClienteDAO dao = new ClienteDAO();
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private List<Cliente> clientes;

    public PanelClientes(VentanaFitnessPro ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Tema.FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        add(crearCabecera(), BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Nombre", "RUT", "Semana actual"}, 0) {
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tabla = new JTable(modelo);
        estilizarTabla(tabla);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Tema.SUPERFICIE);
        scroll.setBorder(BorderFactory.createLineBorder(Tema.BORDE));
        add(scroll, BorderLayout.CENTER);

        add(crearAcciones(), BorderLayout.SOUTH);
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(Tema.FONDO);
        cabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JButton inicio = Tema.botonSecundario("← Inicio");
        inicio.addActionListener(e -> ventana.irA("dashboard"));

        cabecera.add(Tema.titulo("Clientes"), BorderLayout.WEST);
        cabecera.add(inicio, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        acciones.setBackground(Tema.FONDO);

        JButton nuevo = Tema.botonAcento("Nuevo cliente");
        nuevo.addActionListener(e -> nuevo());
        JButton seleccionar = Tema.botonSecundario("Seleccionar");
        seleccionar.addActionListener(e -> seleccionar());

        acciones.add(nuevo);
        acciones.add(seleccionar);
        return acciones;
    }

    public void refrescar() {
        try {
            clientes = dao.listar();
            modelo.setRowCount(0);
            for (Cliente c : clientes) {
                modelo.addRow(new Object[]{c.getNombre(), c.getRut(), c.getSemanaActual()});
            }
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void nuevo() {
        JTextField nombre = Tema.campo();
        JTextField rut = Tema.campo();
        JPanel formulario = new JPanel(new GridLayout(4, 1, 0, 4));
        formulario.add(Tema.etiqueta("Nombre"));
        formulario.add(nombre);
        formulario.add(Tema.etiqueta("RUT"));
        formulario.add(rut);

        int respuesta = JOptionPane.showConfirmDialog(this, formulario, "Nuevo cliente",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta != JOptionPane.OK_OPTION) return;

        if (nombre.getText().trim().isEmpty() || rut.getText().trim().isEmpty()) {
            error("Nombre y RUT son obligatorios.");
            return;
        }
        try {
            Cliente creado = dao.crear(nombre.getText().trim(), rut.getText().trim());
            refrescar();
            ventana.abrirCliente(creado);
        } catch (Exception e) {
            error("No se pudo crear el cliente. Puede que el RUT ya exista.");
        }
    }

    private void seleccionar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la lista.");
            return;
        }
        ventana.abrirCliente(clientes.get(fila));
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setBackground(Tema.SUPERFICIE);
        tabla.setForeground(Tema.TEXTO);
        tabla.setGridColor(Tema.BORDE);
        tabla.setRowHeight(28);
        tabla.setFont(Tema.NORMAL);
        tabla.setSelectionBackground(Tema.ACENTO);
        tabla.setSelectionForeground(Color.WHITE);

        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setBackground(Tema.SUPERFICIE_H);
        cabecera.setForeground(Tema.TEXTO);
        cabecera.setFont(Tema.SUBTITULO);
        cabecera.setReorderingAllowed(false);
    }
}
