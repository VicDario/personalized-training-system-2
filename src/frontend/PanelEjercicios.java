package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import backend.modelo.*;
import backend.datos.EjercicioDAO;

public class PanelEjercicios extends JPanel {

    private final VentanaFitnessPro ventana;
    private final EjercicioDAO dao = new EjercicioDAO();
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private List<Ejercicio> ejercicios;

    public PanelEjercicios(VentanaFitnessPro ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Tema.FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        add(crearCabecera(), BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Tipo", "Nivel", "Tiempo"}, 0) {
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

        cabecera.add(Tema.titulo("Ejercicios"), BorderLayout.WEST);
        cabecera.add(inicio, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        acciones.setBackground(Tema.FONDO);

        JButton nuevo = Tema.botonAcento("Nuevo");
        nuevo.addActionListener(e -> nuevo());
        JButton editar = Tema.botonSecundario("Editar");
        editar.addActionListener(e -> editar());
        JButton eliminar = Tema.botonSecundario("Eliminar");
        eliminar.addActionListener(e -> eliminar());
        JButton detalle = Tema.botonSecundario("Detalle");
        detalle.addActionListener(e -> detalle());
        JButton buscar = Tema.botonSecundario("Buscar por intensidad");
        buscar.addActionListener(e -> buscarPorIntensidad());
        JButton todos = Tema.botonSecundario("Ver todos");
        todos.addActionListener(e -> refrescar());

        acciones.add(nuevo);
        acciones.add(editar);
        acciones.add(eliminar);
        acciones.add(detalle);
        acciones.add(buscar);
        acciones.add(todos);
        return acciones;
    }

    public void refrescar() {
        try {
            mostrar(dao.listar());
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void mostrar(List<Ejercicio> lista) {
        ejercicios = lista;
        modelo.setRowCount(0);
        for (Ejercicio e : lista) {
            modelo.addRow(new Object[]{
                e.getCodigo(), e.getNombre(), e.getTipo(), e.getNivel(), e.getTiempoEstimado() + " min"});
        }
    }

    private Ejercicio seleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio de la lista.");
            return null;
        }
        return ejercicios.get(fila);
    }

    private void nuevo() {
        DialogoEjercicio dialogo = new DialogoEjercicio(ventana, null);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) refrescar();
    }

    private void editar() {
        Ejercicio e = seleccionado();
        if (e == null) return;
        DialogoEjercicio dialogo = new DialogoEjercicio(ventana, e);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) refrescar();
    }

    private void eliminar() {
        Ejercicio e = seleccionado();
        if (e == null) return;
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el ejercicio " + e.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) return;
        try {
            dao.eliminar(e.getCodigo());
            refrescar();
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    private void detalle() {
        Ejercicio e = seleccionado();
        if (e == null) return;
        StringBuilder info = new StringBuilder();
        info.append("Código: ").append(e.getCodigo()).append('\n');
        info.append("Nombre: ").append(e.getNombre()).append('\n');
        info.append("Tipo: ").append(e.getTipo()).append('\n');
        info.append("Nivel: ").append(e.getNivel()).append('\n');
        info.append("Duración: ").append(e.getTiempoEstimado()).append(" minutos\n");
        info.append("Descripción: ").append(e.getDescripcion()).append('\n');
        if (e instanceof EjercicioCardiovascular) {
            info.append("Pulso recomendado: ").append(((EjercicioCardiovascular) e).getPulsoRecomendado()).append(" bpm");
        } else {
            info.append("Peso: ").append(((EjercicioFuerza) e).getPeso()).append(" kg");
        }
        JOptionPane.showMessageDialog(this, info.toString(), "Detalle del ejercicio", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buscarPorIntensidad() {
        NivelIntensidad nivel = (NivelIntensidad) JOptionPane.showInputDialog(this,
            "Seleccione la intensidad:", "Buscar por intensidad", JOptionPane.QUESTION_MESSAGE,
            null, NivelIntensidad.values(), NivelIntensidad.BASICO);
        if (nivel == null) return;
        try {
            List<Ejercicio> encontrados = dao.buscarPorNivel(nivel);
            if (encontrados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ejercicios con esa intensidad.");
            }
            mostrar(encontrados);
        } catch (Exception e) {
            error(e.getMessage());
        }
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
        tabla.setShowGrid(true);

        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setBackground(Tema.SUPERFICIE_H);
        cabecera.setForeground(Tema.TEXTO);
        cabecera.setFont(Tema.SUBTITULO);
        cabecera.setReorderingAllowed(false);
    }
}
