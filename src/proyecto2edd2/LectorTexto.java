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
}