import javax.swing.SwingUtilities;

public class JuegoMemorizacion {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarcoMemoria().setVisible(true);
            }
        });
    }
}
