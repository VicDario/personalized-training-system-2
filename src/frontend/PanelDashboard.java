package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import backend.modelo.*;
import backend.datos.EjercicioDAO;
import backend.notificacion.*;

public class PanelDashboard extends JPanel implements Suscriptor {

    private JLabel lblTotal;
    private JLabel lblTiempo;
    private JLabel lblCardio;
    private JLabel lblFuerza;
    private JLabel lblBasicoInter;
    private JLabel lblAvanzadoAlto;

    public PanelDashboard(VentanaFitnessPro ventana) {
        setLayout(new BorderLayout());
        setBackground(Tema.FONDO);
        add(crearEncabezado(), BorderLayout.NORTH);

        lblTotal        = new JLabel("—");
        lblTiempo       = new JLabel("—");
        lblCardio       = new JLabel("—");
        lblFuerza       = new JLabel("—");
        lblBasicoInter  = new JLabel("—");
        lblAvanzadoAlto = new JLabel("—");

        JPanel grid = new JPanel(new GridLayout(3, 2, 14, 14));
        grid.setBackground(Tema.FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(6, 22, 6, 22));

        grid.add(crearCard("EJERCICIOS",     "🏋", new Color(255, 90,  70),  lblTotal));
        grid.add(crearCard("TIEMPO TOTAL",   "⏱",  new Color(90,  150, 255), lblTiempo));
        grid.add(crearCard("CARDIOVASCULAR", "🏃", new Color(80,  200, 200), lblCardio));
        grid.add(crearCard("FUERZA",         "💪", new Color(255, 160, 80),  lblFuerza));
        grid.add(crearCard("BÁSICO/INTER.",  "⭐", new Color(90,  210, 140), lblBasicoInter));
        grid.add(crearCard("AVANZ./ALTO R.", "🔥", new Color(190, 120, 255), lblAvanzadoAlto));

        add(grid, BorderLayout.CENTER);

        JButton ejercicios = Tema.botonAcento("EJERCICIOS");
        ejercicios.addActionListener(e -> ventana.irA("ejercicios"));
        JButton clientes = Tema.botonSecundario("CLIENTES");
        clientes.addActionListener(e -> ventana.irA("clientes"));

        JPanel pie = new JPanel(new GridLayout(1, 2, 12, 0));
        pie.setBackground(Tema.FONDO);
        pie.setBorder(BorderFactory.createEmptyBorder(8, 22, 22, 22));
        pie.add(ejercicios);
        pie.add(clientes);
        add(pie, BorderLayout.SOUTH);
    }

    @Override
    public void actualizar(String evento, Object datos) {
        if (evento.equals(EventoSistema.CARGA_OK)) {
            calcular((Ejercicio[]) datos);
        } else if (evento.equals(EventoSistema.CARGA_ERROR)) {
            JOptionPane.showMessageDialog(this, (String) datos, "Error de carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescar() {
        try {
            List<Ejercicio> lista = new EjercicioDAO().listar();
            calcular(lista.toArray(new Ejercicio[0]));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcular(Ejercicio[] ejercicios) {
        int tiempoTotal = 0, cardio = 0, fuerza = 0;
        int basico = 0, intermedio = 0, avanzado = 0, alto = 0;

        for (Ejercicio e : ejercicios) {
            tiempoTotal += e.getTiempoEstimado();
            if (e.getTipo() == TipoEjercicio.CARDIOVASCULAR) cardio++;
            else fuerza++;
            switch (e.getNivel()) {
                case BASICO:           basico++;     break;
                case INTERMEDIO:       intermedio++; break;
                case AVANZADO:         avanzado++;   break;
                case ALTO_RENDIMIENTO: alto++;       break;
            }
        }

        lblTotal.setText(String.valueOf(ejercicios.length));
        lblTiempo.setText(tiempoTotal + " min");
        lblCardio.setText(String.valueOf(cardio));
        lblFuerza.setText(String.valueOf(fuerza));
        lblBasicoInter.setText((basico + intermedio) + " ej.");
        lblAvanzadoAlto.setText((avanzado + alto) + " ej.");
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Tema.FONDO);
        header.setBorder(BorderFactory.createEmptyBorder(30, 22, 14, 22));

        JLabel titulo = new JLabel("ZONA PRINCIPAL");
        titulo.setForeground(Tema.TEXTO);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel linea = new JPanel();
        linea.setBackground(Tema.ACENTO);
        linea.setMaximumSize(new Dimension(64, 3));
        linea.setAlignmentX(CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(12));
        header.add(linea);
        return header;
    }

    private JPanel crearCard(String titulo, String icono, Color acento, JLabel valorLabel) {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(acento);
                g2.fillRoundRect(0, 14, 5, getHeight() - 28, 5, 5);
            }
        };
        card.setOpaque(false);
        card.setBackground(Tema.SUPERFICIE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 22, 16, 16));

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setForeground(acento);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        lblIcono.setAlignmentX(LEFT_ALIGNMENT);

        valorLabel.setForeground(Tema.TEXTO);
        valorLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valorLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Tema.TEXTO_TENUE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);

        card.add(lblIcono);
        card.add(Box.createVerticalGlue());
        card.add(valorLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(lblTitulo);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(Tema.SUPERFICIE_H);
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Tema.SUPERFICIE);
                card.repaint();
            }
        });
        return card;
    }
}
