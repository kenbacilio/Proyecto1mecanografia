import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GestorMemoria {
    private List<String> frases;
    private Random generadorAleatorio;
    private String fraseActual;
    private String fraseOriginal;
    private Map<Character, Integer> conteoErrores;
    private int aciertosTotales;
    private int erroresTotales;

    public GestorMemoria() {
        generadorAleatorio = new Random();
        frases = new ArrayList<>();
        cargarFrasesDeArchivo();
        conteoErrores = new HashMap<>();
        aciertosTotales = 0;
        erroresTotales = 0;
    }

    private void cargarFrasesDeArchivo() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("frases.txt"), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    frases.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String nuevaFrase() {
        if (frases.isEmpty()) {
            return "No hay frases disponibles.";
        }
        fraseActual = frases.get(generadorAleatorio.nextInt(frases.size()));
        fraseOriginal = fraseActual;
        return fraseActual;
    }

    public boolean verificarFrase(String intentoUsuario) {
        boolean esCorrecto = intentoUsuario.equalsIgnoreCase(fraseOriginal);
        if (esCorrecto) {
            aciertosTotales += fraseOriginal.length();
        } else {
            for (char c : intentoUsuario.toCharArray()) {
                conteoErrores.put(c, conteoErrores.getOrDefault(c, 0) + 1);
            }
            erroresTotales += intentoUsuario.length();
        }
        return esCorrecto;
    }

    public void procesarTecla(char tecla) {
        if (fraseActual != null && fraseActual.length() > 0 && tecla == fraseActual.charAt(0)) {
            aciertosTotales++;
            fraseActual = fraseActual.substring(1);
        } else {
            erroresTotales++;
            conteoErrores.put(tecla, conteoErrores.getOrDefault(tecla, 0) + 1);
        }
    }

    public void retrocederPosicion() {
        if (fraseActual.length() > 0) {
            fraseActual = fraseOriginal.substring(0, fraseOriginal.length() - fraseActual.length() + 1);
        }
    }

    public String getFraseActual() {
        return fraseActual;
    }

    public String generarInforme() {
        StringBuilder informe = new StringBuilder();
        informe.append("Informe de Juego de Memoria\n");
        informe.append("Aciertos Totales: ").append(aciertosTotales).append("\n");
        informe.append("Errores Totales: ").append(erroresTotales).append("\n");
        informe.append("Teclas con más errores:\n");
        for (Map.Entry<Character, Integer> entry : conteoErrores.entrySet()) {
            informe.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return informe.toString();
    }
}
