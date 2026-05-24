package frontend;

import javax.swing.table.DefaultTableModel;
import backend.modelo.Ejercicio;
import backend.modelo.EjercicioCardiovascular;
import backend.modelo.EjercicioFuerza;
import backend.modelo.Rutina;

public class VistaDetalleRutina extends javax.swing.JDialog {

    public VistaDetalleRutina(java.awt.Frame parent, boolean modal, Rutina rutina) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        cargarDatos(rutina);
    }

    private void cargarDatos(Rutina rutina) {
        lblCliente.setText("Cliente: " + rutina.getCliente());
        lblSemana.setText("Semana de rutina: " + rutina.getSemana());
        
        String intensidadStr = "N/A";
        if (rutina.getCantidadEjercicios() > 0 && rutina.getEjercicios()[0] != null) {
            intensidadStr = rutina.getEjercicios()[0].getNivel().toString();
        }
        lblIntensidad.setText("Nivel de intensidad: " + intensidadStr);
        lblTotales.setText("Ejercicios totales: " + rutina.getCantidadEjercicios() + "  |  Tiempo total: " + rutina.getTiempoTotal() + " min");

        DefaultTableModel modelo = (DefaultTableModel) tablaEjercicios.getModel();
        modelo.setRowCount(0);
        for (int i = 0; i < rutina.getCantidadEjercicios(); i++) {
            Ejercicio e = rutina.getEjercicios()[i];
            if (e != null) {
                String parametro = "N/A";
                if (e instanceof EjercicioCardiovascular) {
                    parametro = "Pulso: " + ((EjercicioCardiovascular) e).getPulsoRecomendado() + " bpm";
                } else if (e instanceof EjercicioFuerza) {
                    parametro = "Peso: " + ((EjercicioFuerza) e).getPeso() + " kg";
                }
                
                modelo.addRow(new Object[]{
                    e.getCodigo(),
                    e.getNombre(),
                    e.getTipo(),
                    e.getTiempoEstimado() + " min",
                    parametro,
                    e.getDescripcion()
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        lblSemana = new javax.swing.JLabel();
        lblIntensidad = new javax.swing.JLabel();
        lblTotales = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEjercicios = new javax.swing.JTable();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalle de Rutina");

        lblCliente.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCliente.setText("Cliente: ");

        lblSemana.setText("Semana: ");

        lblIntensidad.setText("Intensidad: ");

        lblTotales.setText("Ejercicios totales: ");

        tablaEjercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Código", "Nombre", "Tipo", "Duración", "Parámetro Específico", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEjercicios);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCliente)
                            .addComponent(lblSemana)
                            .addComponent(lblIntensidad)
                            .addComponent(lblTotales))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSemana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIntensidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotales)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblIntensidad;
    private javax.swing.JLabel lblSemana;
    private javax.swing.JLabel lblTotales;
    private javax.swing.JTable tablaEjercicios;
    // End of variables declaration//GEN-END:variables
}
