package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import backend.modelo.*;
import backend.datos.ClienteDAO;
import backend.datos.EjercicioDAO;
import backend.datos.RutinaDAO;

public class PanelCliente extends JPanel {

    private final VentanaFitnessPro ventana;
    private final EjercicioDAO ejercicioDAO = new EjercicioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final RutinaDAO rutinaDAO = new RutinaDAO();

    private Cliente cliente;
    private final JLabel titulo = Tema.titulo("");
    private final JLabel subtitulo = Tema.etiqueta("");
    private final JTextArea detalle = new JTextArea();

    public PanelCliente(VentanaFitnessPro ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Tema.FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        add(crearCabecera(), BorderLayout.NORTH);

        detalle.setEditable(false);
        detalle.setBackground(Tema.SUPERFICIE);
        detalle.setForeground(Tema.TEXTO);
        detalle.setFont(new Font("Monospaced", Font.PLAIN, 13));
        detalle.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JScrollPane scroll = new JScrollPane(detalle);
        scroll.setBorder(BorderFactory.createLineBorder(Tema.BORDE));
        add(scroll, BorderLayout.CENTER);

        add(crearAcciones(), BorderLayout.SOUTH);
    }

    public void cargarCliente(Cliente cliente) {
        this.cliente = cliente;
        titulo.setText(cliente.getNombre());
        subtitulo.setText("RUT " + cliente.getRut() + "  ·  Semana actual: " + cliente.getSemanaActual());
        verRutinas();
    }

    private JPanel crearCabecera() {
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Tema.FONDO);
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(subtitulo);

        JButton volver = Tema.botonSecundario("← Clientes");
        volver.addActionListener(e -> ventana.irA("clientes"));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(Tema.FONDO);
        cabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        cabecera.add(textos, BorderLayout.WEST);
        cabecera.add(volver, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        acciones.setBackground(Tema.FONDO);

        JButton generar = Tema.botonAcento("Generar rutina");
        generar.addActionListener(e -> generarRutina());
        JButton ver = Tema.botonSecundario("Ver rutinas");
        ver.addActionListener(e -> verRutinas());

        acciones.add(generar);
        acciones.add(ver);
        return acciones;
    }

    private void generarRutina() {
        JTextField cantidad = Tema.campo();
        JComboBox<NivelIntensidad> nivel = new JComboBox<>(NivelIntensidad.values());
        nivel.setBackground(Tema.SUPERFICIE);
        nivel.setForeground(Tema.TEXTO);

        JPanel formulario = new JPanel(new GridLayout(4, 1, 0, 4));
        formulario.add(Tema.etiqueta("Cantidad de ejercicios"));
        formulario.add(cantidad);
        formulario.add(Tema.etiqueta("Nivel de intensidad"));
        formulario.add(nivel);

        int respuesta = JOptionPane.showConfirmDialog(this, formulario,
            "Generar rutina - Semana " + cliente.getSemanaActual(),
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta != JOptionPane.OK_OPTION) return;

        int total;
        try {
            total = Integer.parseInt(cantidad.getText().trim());
        } catch (NumberFormatException e) {
            error("Ingrese una cantidad válida.");
            return;
        }
        if (total <= 0) {
            error("La cantidad debe ser mayor a 0.");
            return;
        }

        try {
            construirRutina(total, (NivelIntensidad) nivel.getSelectedItem());
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void construirRutina(int total, NivelIntensidad nivel) throws Exception {
        List<Ejercicio> disponibles = ejercicioDAO.buscarPorNivel(nivel);
        Set<String> usadosSemanaAnterior = new HashSet<>();
        for (Ejercicio e : rutinaDAO.ejerciciosUltimaRutina(cliente)) {
            usadosSemanaAnterior.add(e.getCodigo());
        }

        List<Ejercicio> elegidos = new ArrayList<>();
        for (Ejercicio e : disponibles) {
            if (elegidos.size() == total) break;
            if (usadosSemanaAnterior.contains(e.getCodigo())) continue;
            elegidos.add(e);
        }

        if (elegidos.size() < total) {
            int faltantes = total - elegidos.size();
            error("No hay ejercicios suficientes con esos criterios.\n" +
                  "Pruebe con otra intensidad o cantidad.\nEjercicios faltantes: " + faltantes);
            return;
        }

        Rutina rutina = new Rutina(elegidos.size());
        rutina.setCliente(cliente.getNombre());
        rutina.setSemana(cliente.getSemanaActual());
        for (Ejercicio e : elegidos) {
            rutina.agregarEjercicio(e);
        }

        rutinaDAO.crear(cliente, rutina);
        cliente.incrementarSemana();
        clienteDAO.incrementarSemana(cliente);

        subtitulo.setText("RUT " + cliente.getRut() + "  ·  Semana actual: " + cliente.getSemanaActual());
        verRutinas();
    }

    private void verRutinas() {
        try {
            List<Rutina> rutinas = rutinaDAO.listarPorCliente(cliente);
            if (rutinas.isEmpty()) {
                detalle.setText("Este cliente todavía no tiene rutinas.");
                return;
            }
            StringBuilder texto = new StringBuilder();
            for (Rutina rutina : rutinas) {
                texto.append("Semana ").append(rutina.getSemana())
                     .append("  ·  Duración total: ").append(rutina.getTiempoTotal()).append(" min\n");
                for (int i = 0; i < rutina.getCantidadEjercicios(); i++) {
                    Ejercicio e = rutina.getEjercicios()[i];
                    texto.append("   - ").append(e.getNombre())
                         .append(" (").append(e.getNivel()).append(", ")
                         .append(e.getTiempoEstimado()).append(" min)\n");
                }
                texto.append('\n');
            }
            detalle.setText(texto.toString());
            detalle.setCaretPosition(0);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
