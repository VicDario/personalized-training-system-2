package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaFitnessPro extends JFrame {

    public VentanaFitnessPro() {
        setTitle("Fitness Pro");
        setSize(500, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(15, 15, 25));

        // HEADER
        JLabel titulo = new JLabel("ZONA PRINCIPAL", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        main.add(titulo, BorderLayout.NORTH);

        // GRID
        JPanel grid = new JPanel(new GridLayout(3, 2, 15, 15));
        grid.setBackground(new Color(15, 15, 25));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        grid.add(crearCard("TODO EL CUERPO", new Color(255, 80, 80)));
        grid.add(crearCard("ABDOMINALES", new Color(80, 140, 255)));
        grid.add(crearCard("PECHO", new Color(140, 100, 255)));
        grid.add(crearCard("BRAZOS", new Color(255, 150, 80)));
        grid.add(crearCard("PIERNAS", new Color(80, 220, 140)));
        grid.add(crearCard("ESPALDA", new Color(200, 100, 255)));

        main.add(grid, BorderLayout.CENTER);

        // BOTÓN
        JButton iniciar = new JButton("INICIAR");
        iniciar.setBackground(new Color(255, 80, 80));
        iniciar.setForeground(Color.WHITE);
        iniciar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        iniciar.setFocusPainted(false);
        iniciar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        main.add(iniciar, BorderLayout.SOUTH);

        add(main);
        setVisible(true);
    }

    private JPanel crearCard(String texto, Color color) {

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

        JLabel icono = new JLabel("🏋", JLabel.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icono.setForeground(Color.WHITE);

        JLabel label = new JLabel(texto, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        card.add(icono, BorderLayout.CENTER);
        card.add(label, BorderLayout.SOUTH);

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
