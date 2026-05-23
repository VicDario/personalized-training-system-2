package frontend;

import javax.swing.*;
import java.awt.*;

public class Tema {

    public static final Color FONDO        = new Color(18, 19, 24);
    public static final Color SUPERFICIE    = new Color(30, 32, 39);
    public static final Color SUPERFICIE_H  = new Color(38, 41, 50);
    public static final Color BORDE         = new Color(48, 51, 61);
    public static final Color TEXTO         = new Color(238, 240, 245);
    public static final Color TEXTO_TENUE   = new Color(150, 154, 165);
    public static final Color ACENTO        = new Color(255, 90, 70);

    public static final Font TITULO   = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font NORMAL   = new Font("Segoe UI", Font.PLAIN, 14);

    public static JButton boton(String texto, Color fondo, Color colorTexto) {
        JButton b = new JButton(texto) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setBackground(fondo);
        b.setForeground(colorTexto);
        b.setFont(SUBTITULO);
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JButton botonAcento(String texto) {
        return boton(texto, ACENTO, Color.WHITE);
    }

    public static JButton botonSecundario(String texto) {
        return boton(texto, SUPERFICIE_H, TEXTO);
    }

    public static JLabel titulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO);
        l.setFont(TITULO);
        return l;
    }

    public static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setForeground(TEXTO_TENUE);
        l.setFont(NORMAL);
        return l;
    }

    public static JTextField campo() {
        JTextField c = new JTextField();
        c.setBackground(SUPERFICIE);
        c.setForeground(TEXTO);
        c.setCaretColor(TEXTO);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return c;
    }

    public static String nombreNivel(backend.modelo.NivelIntensidad nivel) {
        return nivel.toString();
    }
}
