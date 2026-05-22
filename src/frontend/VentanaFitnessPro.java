package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.modelo.*;
import backend.notificacion.*;

// Ventana principal: muestra stats de los ejercicios cargados desde la BD
public class VentanaFitnessPro extends JFrame implements Suscriptor {

    // Paleta: fondo oscuro uniforme y un acento por metrica
    private static final Color FONDO       = new Color(18, 19, 24);
    private static final Color SUPERFICIE   = new Color(30, 32, 39);
    private static final Color SUPERFICIE_H = new Color(38, 41, 50);
    private static final Color TEXTO        = new Color(238, 240, 245);
    private static final Color TEXTO_TENUE  = new Color(150, 154, 165);
    private static final Color ACENTO       = new Color(255, 90, 70);

    // Labels actualizables con los datos reales del backend
    private JLabel lblTotal;
    private JLabel lblTiempo;
    private JLabel lblCardio;
    private JLabel lblFuerza;
    private JLabel lblBasicoInter;
    private JLabel lblAvanzadoAlto;

    public VentanaFitnessPro() {
        setTitle("Fitness Pro");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(FONDO);
        main.add(crearEncabezado(), BorderLayout.NORTH);

        // Inicializa los labels con guiones, mientras se carga la BD real
        lblTotal        = new JLabel("—");
        lblTiempo       = new JLabel("—");
        lblCardio       = new JLabel("—");
        lblFuerza       = new JLabel("—");
        lblBasicoInter  = new JLabel("—");
        lblAvanzadoAlto = new JLabel("—");

        // Grid de cards con las estadisticas
        JPanel grid = new JPanel(new GridLayout(3, 2, 14, 14));
        grid.setBackground(FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(6, 22, 6, 22));

        grid.add(crearCard("EJERCICIOS",     "🏋", new Color(255, 90,  70),  lblTotal));
        grid.add(crearCard("TIEMPO TOTAL",   "⏱",  new Color(90,  150, 255), lblTiempo));
        grid.add(crearCard("CARDIOVASCULAR", "🏃", new Color(80,  200, 200), lblCardio));
        grid.add(crearCard("FUERZA",         "💪", new Color(255, 160, 80),  lblFuerza));
        grid.add(crearCard("BÁSICO/INTER.",  "⭐", new Color(90,  210, 140), lblBasicoInter));
        grid.add(crearCard("AVANZ./ALTO R.", "🔥", new Color(190, 120, 255), lblAvanzadoAlto));

        main.add(grid, BorderLayout.CENTER);
        main.add(crearPie(), BorderLayout.SOUTH);

        add(main);

        // Se registra en el bus de eventos para recibir notificaciones del backend
        Notificador.getInstancia().suscribir(this);
        setVisible(true);
    }

    // Recibe y reparte los eventos del backend
    @Override
    public void actualizar(String evento, Object datos) {
        if (evento.equals(EventoSistema.CARGA_OK)) {
            actualizarEstadisticas((Ejercicio[]) datos);
        } else if (evento.equals(EventoSistema.CARGA_ERROR)) {
            JOptionPane.showMessageDialog(this, (String) datos, "Error de carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Calcula las stats y actualiza cada label del grid
    private void actualizarEstadisticas(Ejercicio[] ejercicios) {
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

    // Titulo de la ventana mas una linea de acento debajo
    private JPanel crearEncabezado() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(FONDO);
        header.setBorder(BorderFactory.createEmptyBorder(30, 22, 14, 22));

        JLabel titulo = new JLabel("ZONA PRINCIPAL");
        titulo.setForeground(TEXTO);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel linea = new JPanel();
        linea.setBackground(ACENTO);
        linea.setMaximumSize(new Dimension(64, 3));
        linea.setAlignmentX(CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(12));
        header.add(linea);
        return header;
    }

    // Card: superficie redondeada con barra de acento e icono, valor y titulo
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
        card.setBackground(SUPERFICIE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 22, 16, 16));

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setForeground(acento);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        lblIcono.setAlignmentX(LEFT_ALIGNMENT);

        valorLabel.setForeground(TEXTO);
        valorLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valorLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(TEXTO_TENUE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setAlignmentX(LEFT_ALIGNMENT);

        card.add(lblIcono);
        card.add(Box.createVerticalGlue());
        card.add(valorLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(lblTitulo);

        // hover: aclara la superficie de la card
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(SUPERFICIE_H);
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(SUPERFICIE);
                card.repaint();
            }
        });
        return card;
    }

    // Boton INICIAR (solo visual, a juego con las cards)
    private JPanel crearPie() {
        JButton iniciar = new JButton("INICIAR") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                super.paintComponent(g);
            }
        };
        iniciar.setContentAreaFilled(false);
        iniciar.setBorderPainted(false);
        iniciar.setFocusPainted(false);
        iniciar.setBackground(ACENTO);
        iniciar.setForeground(Color.WHITE);
        iniciar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        iniciar.setBorder(BorderFactory.createEmptyBorder(14, 10, 14, 10));
        iniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // hover: oscurece un poco el acento
        iniciar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                iniciar.setBackground(ACENTO.darker());
            }
            public void mouseExited(MouseEvent e) {
                iniciar.setBackground(ACENTO);
            }
        });

        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(FONDO);
        pie.setBorder(BorderFactory.createEmptyBorder(8, 22, 22, 22));
        pie.add(iniciar, BorderLayout.CENTER);
        return pie;
    }
}
