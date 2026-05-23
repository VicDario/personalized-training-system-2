package frontend;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import backend.datos.EjercicioDAO;
import backend.modelo.Ejercicio;
import backend.modelo.EjercicioCardiovascular;
import backend.modelo.EjercicioFuerza;
import backend.modelo.NivelIntensidad;

public class VistaEjercicios extends javax.swing.JFrame {

    private final EjercicioDAO dao = new EjercicioDAO();
    private List<Ejercicio> ejercicios;

    public VistaEjercicios() {
        initComponents();
        setLocationRelativeTo(null);
        refrescar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEjercicios = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnDetalle = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejercicios");

        tablaEjercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Código", "Nombre", "Tipo", "Nivel", "Tiempo" }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEjercicios);

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnDetalle.setText("Detalle");
        btnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar por intensidad");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnRefrescar.setText("Ver todos");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(8, 8, 8)
                        .addComponent(btnEditar)
                        .addGap(8, 8, 8)
                        .addComponent(btnEliminar)
                        .addGap(8, 8, 8)
                        .addComponent(btnDetalle)
                        .addGap(8, 8, 8)
                        .addComponent(btnBuscar)
                        .addGap(8, 8, 8)
                        .addComponent(btnRefrescar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnDetalle)
                    .addComponent(btnBuscar)
                    .addComponent(btnRefrescar)
                    .addComponent(btnVolver))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        FormEjercicio dialogo = new FormEjercicio(this, true);
        dialogo.cargar(null);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) refrescar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        Ejercicio e = seleccionado();
        if (e == null) return;
        FormEjercicio dialogo = new FormEjercicio(this, true);
        dialogo.cargar(e);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) refrescar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
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
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleActionPerformed
        Ejercicio e = seleccionado();
        if (e == null) return;
        String info = "Código: " + e.getCodigo()
            + "\nNombre: " + e.getNombre()
            + "\nTipo: " + e.getTipo()
            + "\nNivel: " + e.getNivel()
            + "\nDuración: " + e.getTiempoEstimado() + " minutos"
            + "\nDescripción: " + e.getDescripcion();
        if (e instanceof EjercicioCardiovascular) {
            info += "\nPulso recomendado: " + ((EjercicioCardiovascular) e).getPulsoRecomendado() + " bpm";
        } else {
            info += "\nPeso: " + ((EjercicioFuerza) e).getPeso() + " kg";
        }
        JOptionPane.showMessageDialog(this, info, "Detalle del ejercicio", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnDetalleActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        NivelIntensidad nivel = (NivelIntensidad) JOptionPane.showInputDialog(this,
            "Seleccione la intensidad:", "Buscar por intensidad", JOptionPane.QUESTION_MESSAGE,
            null, NivelIntensidad.values(), NivelIntensidad.BASICO);
        if (nivel == null) return;
        try {
            List<Ejercicio> encontrados = dao.buscarPorNivel(nivel);
            if (encontrados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ejercicios con esa intensidad.");
            }
            ejercicios = encontrados;
            cargarTabla(encontrados);
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        refrescar();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void refrescar() {
        try {
            ejercicios = dao.listar();
            cargarTabla(ejercicios);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void cargarTabla(List<Ejercicio> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tablaEjercicios.getModel();
        modelo.setRowCount(0);
        for (Ejercicio e : lista) {
            modelo.addRow(new Object[]{
                e.getCodigo(), e.getNombre(), e.getTipo(), e.getNivel(), e.getTiempoEstimado() + " min"});
        }
    }

    private Ejercicio seleccionado() {
        int fila = tablaEjercicios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio de la lista.");
            return null;
        }
        return ejercicios.get(fila);
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDetalle;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEjercicios;
    // End of variables declaration//GEN-END:variables
}
