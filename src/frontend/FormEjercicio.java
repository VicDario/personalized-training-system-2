package frontend;

import javax.swing.JOptionPane;
import backend.datos.EjercicioDAO;
import backend.modelo.Ejercicio;
import backend.modelo.EjercicioCardiovascular;
import backend.modelo.EjercicioFuerza;
import backend.modelo.NivelIntensidad;
import backend.modelo.TipoEjercicio;

public class FormEjercicio extends javax.swing.JDialog {

    private final EjercicioDAO dao = new EjercicioDAO();
    private Ejercicio existente;
    private boolean guardado;

    public FormEjercicio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        cmbNivel = new javax.swing.JComboBox<>();
        lblDuracion = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        lblExtra = new javax.swing.JLabel();
        txtExtra = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejercicio");

        lblTipo.setText("Tipo");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cardiovascular", "Fuerza" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        lblNombre.setText("Nombre");

        lblDescripcion.setText("Descripción");

        lblNivel.setText("Nivel de intensidad");

        cmbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Básico", "Intermedio", "Avanzado", "Alto rendimiento" }));

        lblDuracion.setText("Duración (min)");

        lblExtra.setText("Pulso recomendado (bpm)");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre)
                    .addComponent(lblDescripcion)
                    .addComponent(txtDescripcion)
                    .addComponent(lblNivel)
                    .addComponent(cmbNivel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDuracion)
                    .addComponent(txtDuracion)
                    .addComponent(lblExtra)
                    .addComponent(txtExtra)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblDescripcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblNivel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblDuracion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtExtra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        boolean cardio = cmbTipo.getSelectedIndex() == 0;
        lblExtra.setText(cardio ? "Pulso recomendado (bpm)" : "Peso (kg)");
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    public void cargar(Ejercicio e) {
        this.existente = e;
        if (e == null) {
            setTitle("Nuevo ejercicio");
            return;
        }
        setTitle("Editar ejercicio");
        cmbTipo.setSelectedIndex(e.getTipo().ordinal());
        cmbTipo.setEnabled(false);
        txtNombre.setText(e.getNombre());
        txtDescripcion.setText(e.getDescripcion());
        cmbNivel.setSelectedIndex(e.getNivel().ordinal());
        txtDuracion.setText(String.valueOf(e.getTiempoEstimado()));
        if (e instanceof EjercicioCardiovascular) {
            txtExtra.setText(String.valueOf(((EjercicioCardiovascular) e).getPulsoRecomendado()));
        } else {
            txtExtra.setText(String.valueOf(((EjercicioFuerza) e).getPeso()));
        }
    }

    public boolean fueGuardado() {
        return guardado;
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            error("El nombre es obligatorio.");
            return;
        }
        int minutos = enteroPositivo(txtDuracion.getText(), "La duración debe ser un número mayor a 0.");
        int valorExtra = enteroPositivo(txtExtra.getText(), "Ingrese un valor numérico mayor a 0.");
        if (minutos <= 0 || valorExtra <= 0) return;

        TipoEjercicio tipo = TipoEjercicio.values()[cmbTipo.getSelectedIndex()];
        Ejercicio e;
        if (tipo == TipoEjercicio.CARDIOVASCULAR) {
            EjercicioCardiovascular cardio = new EjercicioCardiovascular();
            cardio.setPulsoRecomendado(valorExtra);
            e = cardio;
        } else {
            EjercicioFuerza fuerza = new EjercicioFuerza();
            fuerza.setPeso(valorExtra);
            e = fuerza;
        }
        e.setNombre(nombre);
        e.setDescripcion(txtDescripcion.getText().trim());
        e.setNivel(NivelIntensidad.values()[cmbNivel.getSelectedIndex()]);
        e.setTiempoEstimado(minutos);

        try {
            if (existente == null) {
                e.setCodigo(dao.siguienteCodigo(tipo));
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbNivel;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblExtra;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtExtra;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
