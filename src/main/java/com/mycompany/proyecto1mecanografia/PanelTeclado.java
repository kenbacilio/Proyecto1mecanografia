import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTeclado extends JPanel {
    private JButton[] botones;
    private boolean capsActivo;
    private boolean primerToque;
    private JTextField areaTexto;
    private MarcoMemoria marco;

    public PanelTeclado(JTextField areaTexto, MarcoMemoria marco) {
        this.areaTexto = areaTexto;
        this.marco = marco;
        primerToque = false;
        setLayout(new GridLayout(4, 10));
        inicializarTeclado();
    }

    private void inicializarTeclado() {
        String[] letras = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                           "A", "S", "D", "F", "G", "H", "J", "K", "L", ";",
                           "Z", "X", "C", "V", "B", "N", "M", "Ñ", ",", ".", "/",
                           "Caps", "Space", "Enter", "Borrar"};

        botones = new JButton[letras.length];

        for (int i = 0; i < letras.length; i++) {
            botones[i] = new JButton(letras[i]);
            final int index = i;

            personalizarBoton(botones[i], letras[index]);

            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tecla = letras[index];
                    procesarTecla(tecla);
                }
            });
            add(botones[i]);
        }
    }

    private void personalizarBoton(JButton boton, String letra) {
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setForeground(Color.BLACK);

        if (letra.equals("Caps") || letra.equals("Space") || letra.equals("Enter") || letra.equals("Borrar")) {
            boton.setBackground(Color.DARK_GRAY);
            boton.setForeground(Color.WHITE);
        }

        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void procesarTecla(String tecla) {
        if (tecla.equals("Caps")) {
            capsActivo = !capsActivo;
            actualizarEstadoTeclado();
        } else if (tecla.equals("Space")) {
            marco.procesarTecla(" ");
        } else {
            if (!primerToque) {
                ocultarLetrasTeclado();
                primerToque = true;
            }
            marco.procesarTecla(capsActivo ? tecla.toUpperCase() : tecla.toLowerCase());
        }
    }

    private void ocultarLetrasTeclado() {
        for (JButton boton : botones) {
            boton.setForeground(boton.getBackground());
        }
    }

    private void actualizarEstadoTeclado() {
        for (JButton boton : botones) {
            String texto = boton.getText();
            if (!texto.equals("Caps") && !texto.equals("Space") && !texto.equals("Enter") && !texto.equals("Borrar")) {
                boton.setText(capsActivo ? texto.toUpperCase() : texto.toLowerCase());
            }
        }
    }
}
