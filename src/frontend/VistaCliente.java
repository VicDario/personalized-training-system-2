package frontend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import backend.datos.ClienteDAO;
import backend.datos.EjercicioDAO;
import backend.datos.RutinaDAO;
import backend.modelo.Cliente;
import backend.modelo.Ejercicio;
import backend.modelo.NivelIntensidad;
import backend.modelo.Rutina;

public class VistaCliente extends javax.swing.JFrame {

    private final EjercicioDAO ejercicioDAO = new EjercicioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final RutinaDAO rutinaDAO = new RutinaDAO();
    private Cliente cliente;

    public VistaCliente() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRutinas = new javax.swing.JTextArea();
        btnGenerar = new javax.swing.JButton();
        btnVerRutinas = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cliente");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombre.setText("Cliente");

        lblInfo.setText("RUT - Semana actual");

        txtRutinas.setEditable(false);
        txtRutinas.setColumns(20);
        txtRutinas.setRows(5);
        jScrollPane1.setViewportView(txtRutinas);

        btnGenerar.setText("Generar rutina");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        btnVerRutinas.setText("Ver rutinas");
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
        verRutinas();
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
            error("No hay ejercicios suficientes con esos criterios.\n"
                + "Pruebe con otra intensidad o cantidad.\nEjercicios faltantes: " + faltantes);
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

        lblInfo.setText("RUT " + cliente.getRut() + "  -  Semana actual: " + cliente.getSemanaActual());
        verRutinas();
    }

    private void verRutinas() {
        try {
            List<Rutina> rutinas = rutinaDAO.listarPorCliente(cliente);
            if (rutinas.isEmpty()) {
                txtRutinas.setText("Este cliente todavía no tiene rutinas.");
                return;
            }
            StringBuilder texto = new StringBuilder();
            for (Rutina rutina : rutinas) {
                texto.append("Semana ").append(rutina.getSemana())
                     .append("  -  Duración total: ").append(rutina.getTiempoTotal()).append(" min\n");
                for (int i = 0; i < rutina.getCantidadEjercicios(); i++) {
                    Ejercicio e = rutina.getEjercicios()[i];
                    texto.append("   - ").append(e.getNombre())
                         .append(" (").append(e.getNivel()).append(", ")
                         .append(e.getTiempoEstimado()).append(" min)\n");
                }
                texto.append('\n');
            }
            txtRutinas.setText(texto.toString());
            txtRutinas.setCaretPosition(0);
        } catch (Exception e) {
            error(e.getMessage());
        }
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
    private javax.swing.JTextArea txtRutinas;
    // End of variables declaration//GEN-END:variables
}
