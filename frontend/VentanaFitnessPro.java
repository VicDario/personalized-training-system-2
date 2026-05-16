package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import backend.modelo.*;
import backend.notificacion.*;

// Ventana principal: muestra estadísticas de los ejercicios cargados desde la BD
public class VentanaFitnessPro extends JFrame implements Suscriptor {

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
        main.setBackground(new Color(15, 15, 25));

        // Encabezado
        JLabel titulo = new JLabel("ZONA PRINCIPAL", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        main.add(titulo, BorderLayout.NORTH);

        // Inicializa los labels con guión mientras se carga la BD
        lblTotal        = new JLabel("—", JLabel.CENTER);
        lblTiempo       = new JLabel("—", JLabel.CENTER);
        lblCardio       = new JLabel("—", JLabel.CENTER);
        lblFuerza       = new JLabel("—", JLabel.CENTER);
        lblBasicoInter  = new JLabel("—", JLabel.CENTER);
        lblAvanzadoAlto = new JLabel("—", JLabel.CENTER);

        // Grid de cards con las estadísticas
        JPanel grid = new JPanel(new GridLayout(3, 2, 15, 15));
        grid.setBackground(new Color(15, 15, 25));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        grid.add(crearCard("EJERCICIOS",     "🏋", new Color(255, 80,  80),  lblTotal));
        grid.add(crearCard("TIEMPO TOTAL",   "⏱",  new Color(80,  140, 255), lblTiempo));
        grid.add(crearCard("CARDIOVASCULAR", "🏃", new Color(140, 100, 255), lblCardio));
        grid.add(crearCard("FUERZA",         "💪", new Color(255, 150, 80),  lblFuerza));
        grid.add(crearCard("BÁSICO/INTER.",  "⭐", new Color(80,  220, 140), lblBasicoInter));
        grid.add(crearCard("AVANZ./ALTO R.", "🔥", new Color(200, 100, 255), lblAvanzadoAlto));

        main.add(grid, BorderLayout.CENTER);

        // Botón para iniciar la generación de rutina
        JButton iniciar = new JButton("INICIAR");
        iniciar.setBackground(new Color(255, 80, 80));
        iniciar.setForeground(Color.WHITE);
        iniciar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        iniciar.setFocusPainted(false);
        iniciar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        main.add(iniciar, BorderLayout.SOUTH);

        add(main);

        // Se registra en el bus de eventos para recibir notificaciones del backend
        Notificador.getInstancia().suscribir(this);
        setVisible(true);
    }

    // Recibe y despacha los eventos del backend
    @Override
    public void actualizar(String evento, Object datos) {
        if (evento.equals(EventoSistema.CARGA_OK)) {
            actualizarEstadisticas((Ejercicio[]) datos);
        } else if (evento.equals(EventoSistema.CARGA_ERROR)) {
            JOptionPane.showMessageDialog(this, (String) datos, "Error de carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Calcula las estadísticas y actualiza cada label del grid
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

    // Construye una card con icono, valor actualizable y título
    private JPanel crearCard(String titulo, String icono, Color color, JLabel valorLabel) {
        JPanel card = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        card.setOpaque(false);
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIcono = new JLabel(icono, JLabel.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        valorLabel.setForeground(Color.WHITE);
        valorLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));

        JPanel centro = new JPanel(new GridLayout(2, 1));
        centro.setOpaque(false);
        centro.add(lblIcono);
        centro.add(valorLabel);

        card.add(centro, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);

        // Efecto hover: aclara el color al pasar el mouse
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(color.brighter());
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(color);
                card.repaint();
            }
        });

        return card;
    }
}
