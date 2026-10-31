package proyecto2edd2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Dario
 */
public class LectorTexto {
    
    public void mostrarTexto(String ruta, SDES sdes1, SDES sdes2, SDES sdes3) {
        Algoritmo3DES des = new Algoritmo3DES();
        try {
            byte[] archivoBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ruta));
            String textoDesencriptado = des.desencriptar3DSBinario(sdes1, sdes2, sdes3, archivoBytes);
            System.out.println(textoDesencriptado);      
        } catch (java.io.IOException e) {
            System.out.println("Error de lectura. Revisa que la ruta sea correcta: " + e.getMessage());
        }
    }
    
    public void descargarYComprimirLibro(String rutaBin, String rutaTxtDestino, SDES sdes1, SDES sdes2, SDES sdes3) {
        Algoritmo3DES des = new Algoritmo3DES();
        try {
            byte[] archivoBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(rutaBin));
            String textoPlano = des.desencriptar3DSBinario(sdes1, sdes2, sdes3, archivoBytes);
            AlgoritmoHuffman.conteoCodigos = 0;
            
            int[] frecuencias = AlgoritmoHuffman.conteoFrecuencias(textoPlano);
            NodoHuffman raiz = AlgoritmoHuffman.contruirArbol(textoPlano);
            
            AlgoritmoHuffman.generarCodigos(raiz, "");
            String textoComprimido = AlgoritmoHuffman.Comprimir(textoPlano);

            try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(rutaTxtDestino))) {
                StringBuilder freqStr = new StringBuilder();
                for (int i = 0; i < frecuencias.length; i++) {
                    if (frecuencias[i] > 0) {
                        freqStr.append(i).append(":").append(frecuencias[i]).append(",");
                    }
                }
                writer.println(freqStr.toString());
                
                writer.println(textoComprimido);
            }
            System.out.println("Libro Premium comprimido y guardado exitosamente en: " + rutaTxtDestino);
        } catch (Exception e) {
            System.out.println("Error en la compresión del libro: " + e.getMessage());
        }
    }
    
    public void leerLibroLocalComprimido(String rutaTxt) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(rutaTxt))) {
            String lineaFrecuencias = reader.readLine();
            String textoComprimido = reader.readLine();
            
            if (lineaFrecuencias == null || textoComprimido == null) {
                System.out.println("Error: El archivo comprimido está corrupto o vacío.");
                return;
            }

            int[] frecuencias = new int[AlgoritmoHuffman.MAX_CHARS];
            String[] pares = lineaFrecuencias.split(",");
            for (String par : pares) {
                if (!par.trim().isEmpty()) {
                    String[] datos = par.split(":");
                    int ascii = Integer.parseInt(datos[0]);
                    int freq = Integer.parseInt(datos[1]);
                    frecuencias[ascii] = freq;
                }
            }

            java.util.ArrayList<NodoHuffman> nodos = AlgoritmoHuffman.crearLista(frecuencias);
            AlgoritmoHuffman.OrdenarLista(nodos);
            while (nodos.size() > 1) {
                AlgoritmoHuffman.OrdenarLista(nodos);
                NodoHuffman left = nodos.remove(0);
                NodoHuffman right = nodos.remove(0);
                
                NodoHuffman parent = new NodoHuffman();
                parent.caracter = '-';
                parent.frecuencia = left.frecuencia + right.frecuencia;
                parent.izquierda = left;
                parent.derecha = right;
                nodos.add(parent);
            }
            NodoHuffman raiz = nodos.get(0);

            String textoPlano = AlgoritmoHuffman.Descomprimir(textoComprimido, raiz);
            
            System.out.println("\n======= LECTURA LOCAL (PREMIUM) =======");
            System.out.println(textoPlano);
            System.out.println("=======================================\n");

        } catch (Exception e) {
            System.out.println("Error al procesar el archivo local: " + e.getMessage());
        }
    }
}