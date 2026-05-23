package frontend;

import javax.swing.*;
import java.awt.*;
import backend.modelo.*;
import backend.datos.EjercicioDAO;

public class DialogoEjercicio extends JDialog {

    private final EjercicioDAO dao = new EjercicioDAO();
    private final Ejercicio existente;
    private boolean guardado;

    private final JComboBox<TipoEjercicio> tipo = new JComboBox<>(TipoEjercicio.values());
    private final JComboBox<NivelIntensidad> nivel = new JComboBox<>(NivelIntensidad.values());
    private final JTextField nombre = Tema.campo();
    private final JTextField descripcion = Tema.campo();
    private final JTextField duracion = Tema.campo();
    private final JLabel lblExtra = Tema.etiqueta("");
    private final JTextField extra = Tema.campo();

    public DialogoEjercicio(JFrame padre, Ejercicio existente) {
        super(padre, true);
        this.existente = existente;
        setTitle(existente == null ? "Nuevo ejercicio" : "Editar ejercicio");
        setSize(420, 420);
        setLocationRelativeTo(padre);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Tema.FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 0, 6, 0);

        estilizarCombo(tipo);
        estilizarCombo(nivel);

        agregar(formulario, c, Tema.etiqueta("Tipo"), tipo);
        agregar(formulario, c, Tema.etiqueta("Nombre"), nombre);
        agregar(formulario, c, Tema.etiqueta("Descripción"), descripcion);
        agregar(formulario, c, Tema.etiqueta("Nivel de intensidad"), nivel);
        agregar(formulario, c, Tema.etiqueta("Duración (min)"), duracion);
        agregar(formulario, c, lblExtra, extra);

        tipo.addActionListener(e -> actualizarExtra());

        JButton guardar = Tema.botonAcento("Guardar");
        guardar.addActionListener(e -> guardar());
        JButton cancelar = Tema.botonSecundario("Cancelar");
        cancelar.addActionListener(e -> dispose());

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pie.setBackground(Tema.FONDO);
        pie.add(cancelar);
        pie.add(guardar);

        c.gridy++;
        c.insets = new Insets(14, 0, 0, 0);
        formulario.add(pie, c);

        setContentPane(formulario);
        cargarDatos();
        actualizarExtra();
    }

    public boolean fueGuardado() { return guardado; }

    private void cargarDatos() {
        if (existente == null) return;
        tipo.setSelectedItem(existente.getTipo());
        tipo.setEnabled(false);
        nombre.setText(existente.getNombre());
        descripcion.setText(existente.getDescripcion());
        nivel.setSelectedItem(existente.getNivel());
        duracion.setText(String.valueOf(existente.getTiempoEstimado()));
        if (existente instanceof EjercicioCardiovascular) {
            extra.setText(String.valueOf(((EjercicioCardiovascular) existente).getPulsoRecomendado()));
        } else {
            extra.setText(String.valueOf(((EjercicioFuerza) existente).getPeso()));
        }
    }

    private void actualizarExtra() {
        boolean cardio = tipo.getSelectedItem() == TipoEjercicio.CARDIOVASCULAR;
        lblExtra.setText(cardio ? "Pulso recomendado (bpm)" : "Peso (kg)");
    }

    private void guardar() {
        String nombreTexto = nombre.getText().trim();
        if (nombreTexto.isEmpty()) {
            error("El nombre es obligatorio.");
            return;
        }
        int minutos = enteroPositivo(duracion.getText(), "La duración debe ser un número mayor a 0.");
        int valorExtra = enteroPositivo(extra.getText(), "Ingrese un valor numérico mayor a 0.");
        if (minutos <= 0 || valorExtra <= 0) return;

        TipoEjercicio tipoElegido = (TipoEjercicio) tipo.getSelectedItem();
        Ejercicio e;
        if (tipoElegido == TipoEjercicio.CARDIOVASCULAR) {
            EjercicioCardiovascular cardio = new EjercicioCardiovascular();
            cardio.setPulsoRecomendado(valorExtra);
            e = cardio;
        } else {
            EjercicioFuerza fuerza = new EjercicioFuerza();
            fuerza.setPeso(valorExtra);
            e = fuerza;
        }
        e.setNombre(nombreTexto);
        e.setDescripcion(descripcion.getText().trim());
        e.setNivel((NivelIntensidad) nivel.getSelectedItem());
        e.setTiempoEstimado(minutos);

        try {
            if (existente == null) {
                e.setCodigo(dao.siguienteCodigo(tipoElegido));
                dao.crear(e);
            } else {
                e.setCodigo(existente.getCodigo());
                dao.actualizar(e);
            }
            guardado = true;
            dispose();
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    private int enteroPositivo(String texto, String mensaje) {
        try {
            int valor = Integer.parseInt(texto.trim());
            if (valor <= 0) error(mensaje);
            return valor;
        } catch (NumberFormatException ex) {
            error(mensaje);
            return 0;
        }
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    private void agregar(JPanel panel, GridBagConstraints c, JComponent etiqueta, JComponent campo) {
        panel.add(etiqueta, c);
        c.gridy++;
        panel.add(campo, c);
        c.gridy++;
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBackground(Tema.SUPERFICIE);
        combo.setForeground(Tema.TEXTO);
    }
}
