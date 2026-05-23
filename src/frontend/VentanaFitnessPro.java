package frontend;

import javax.swing.*;
import java.awt.*;
import backend.modelo.Cliente;
import backend.notificacion.Notificador;

public class VentanaFitnessPro extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;
    private PanelDashboard dashboard;
    private PanelEjercicios ejercicios;
    private PanelClientes clientes;
    private PanelCliente cliente;

    public VentanaFitnessPro() {
        setTitle("Fitness Pro");
        setSize(820, 780);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setBackground(Tema.FONDO);

        dashboard = new PanelDashboard(this);
        ejercicios = new PanelEjercicios(this);
        clientes = new PanelClientes(this);
        cliente = new PanelCliente(this);

        contenedor.add(dashboard, "dashboard");
        contenedor.add(ejercicios, "ejercicios");
        contenedor.add(clientes, "clientes");
        contenedor.add(cliente, "cliente");

        add(contenedor);

        Notificador.getInstancia().suscribir(dashboard);
        setVisible(true);
    }

    public void irA(String nombre) {
        if (nombre.equals("dashboard")) dashboard.refrescar();
        if (nombre.equals("ejercicios")) ejercicios.refrescar();
        if (nombre.equals("clientes")) clientes.refrescar();
        cardLayout.show(contenedor, nombre);
    }

    public void abrirCliente(Cliente seleccionado) {
        cliente.cargarCliente(seleccionado);
        cardLayout.show(contenedor, "cliente");
    }
}
