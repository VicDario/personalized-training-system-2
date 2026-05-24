package frontend;

import java.util.List;
import javax.swing.JOptionPane;
import backend.datos.RutinaDAO;
import backend.modelo.Cliente;
import backend.modelo.NivelIntensidad;
import backend.modelo.Rutina;

public class VistaCliente extends javax.swing.JFrame {

    private final RutinaDAO rutinaDAO = new RutinaDAO();
    private Cliente cliente;
    private List<Rutina> rutinas;

    public VistaCliente() {
        initComponents();
        setLocationRelativeTo(null);
        tablaRutinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    abrirDetalleRutina();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRutinas = new javax.swing.JTable();
        btnGenerar = new javax.swing.JButton();
        btnVerRutinas = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cliente");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombre.setText("Cliente");

        lblInfo.setText("RUT - Semana actual");

        tablaRutinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Semana", "Nivel de Intensidad", "Ejercicios Totales", "Total de Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaRutinas);

        btnGenerar.setText("Generar rutina");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnVerRutinas.setText("Ver rutina");
        btnVerRutinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerRutinasActionPerformed(evt);
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
                    .addComponent(lblNombre)
                    .addComponent(lblInfo)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGenerar)
                        .addGap(8, 8, 8)
                        .addComponent(btnVerRutinas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerar)
                    .addComponent(btnVerRutinas)
                    .addComponent(btnVolver))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        FormRutina dialogo = new FormRutina(this, true, cliente.getSemanaActual());
        dialogo.setVisible(true);
        if (!dialogo.fueAceptado()) return;
        try {
            construirRutina(dialogo.getCantidad(), dialogo.getNivel());
        } catch (Exception e) {
            error(e.getMessage());
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnVerRutinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRutinasActionPerformed
        abrirDetalleRutina();
    }//GEN-LAST:event_btnVerRutinasActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    public void cargarCliente(Cliente cliente) {
        this.cliente = cliente;
        lblNombre.setText(cliente.getNombre());
        lblInfo.setText("RUT " + cliente.getRut() + "  -  Semana actual: " + cliente.getSemanaActual());
        verRutinas();
    }

    private void construirRutina(int total, NivelIntensidad nivel) throws Exception {
        rutinaDAO.generarYCrear(cliente, total, nivel);
        lblInfo.setText("RUT " + cliente.getRut() + "  -  Semana actual: " + cliente.getSemanaActual());
        verRutinas();
    }

    private void verRutinas() {
        try {
            rutinas = rutinaDAO.listarPorCliente(cliente);
            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaRutinas.getModel();
            modelo.setRowCount(0);
            for (Rutina rutina : rutinas) {
                String intensidadStr = "N/A";
                if (rutina.getCantidadEjercicios() > 0 && rutina.getEjercicios()[0] != null) {
                    intensidadStr = rutina.getEjercicios()[0].getNivel().toString();
                }
                
                modelo.addRow(new Object[]{
                    "Semana " + rutina.getSemana(),
                    intensidadStr,
                    rutina.getCantidadEjercicios(),
                    rutina.getTiempoTotal() + " min"
                });
            }
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void abrirDetalleRutina() {
        int fila = tablaRutinas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una rutina de la lista.", "Detalle de Rutina", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Rutina rutina = rutinas.get(fila);
        VistaDetalleRutina dialogo = new VistaDetalleRutina(this, true, rutina);
        dialogo.setVisible(true);
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnVerRutinas;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRutinas;
    // End of variables declaration//GEN-END:variables
}
