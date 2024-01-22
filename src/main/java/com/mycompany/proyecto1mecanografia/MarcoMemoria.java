import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarcoMemoria extends JFrame {
    private JTextField textoEntrada;
    private JButton botonMostrarFrase, botonComprobar;
    private JLabel etiquetaFrase, etiquetaInfo;
    private GestorMemoria gestor;
    private PanelTeclado teclado;

    public MarcoMemoria() {
        gestor = new GestorMemoria();
        initComponents();
    }

    private void initComponents() {
        setTitle("Juego de Memoria con Frases");
        setSize(500, 300);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textoEntrada = new JTextField(20);
        textoEntrada.setEditable(false);
        botonMostrarFrase = new JButton("Mostrar Frase");
        botonComprobar = new JButton("Verificar");
        etiquetaFrase = new JLabel("Presiona 'Mostrar Frase' para empezar");
        etiquetaInfo = new JLabel("");

        botonMostrarFrase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaFrase = gestor.nuevaFrase();
                etiquetaFrase.setText(nuevaFrase);
                textoEntrada.setText("");
            }
        });

        botonComprobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String intento = textoEntrada.getText();
                boolean correcto = gestor.verificarFrase(intento);
                etiquetaInfo.setText(correcto ? "¡Correcto!" : "Incorrecto. Intenta de nuevo.");
                if (correcto) {
                    mostrarInforme();
                }
            }
        });

        teclado = new PanelTeclado(textoEntrada, this);

        add(botonMostrarFrase);
        add(etiquetaFrase);
        add(textoEntrada);
        add(botonComprobar);
        add(etiquetaInfo);
        add(teclado);
    }

    public void procesarTecla(String tecla) {
        if (!tecla.equals("Borrar") && (gestor.getFraseActual().length() == 0 || tecla.charAt(0) != gestor.getFraseActual().charAt(0))) {
            return;
        }

        if (tecla.equals("Borrar") && textoEntrada.getText().length() > 0) {
            textoEntrada.setText(textoEntrada.getText().substring(0, textoEntrada.getText().length() - 1));
            gestor.retrocederPosicion();
        } else {
            textoEntrada.setText(textoEntrada.getText() + tecla);
            gestor.procesarTecla(tecla.charAt(0));
        }
    }

    private void mostrarInforme() {
        String informe = gestor.generarInforme();
        JOptionPane.showMessageDialog(this, informe, "Informe de Juego de Memoria", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(informe);
    }
}

