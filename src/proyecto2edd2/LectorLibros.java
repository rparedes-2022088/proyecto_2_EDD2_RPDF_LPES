package proyecto2edd2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author luisp
 */
public class LectorLibros {
    public List<Libro> cargarLibros(String ruta) {
        List<Libro> catalogo = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = lector.readLine()) != null) {
                //verificacion de que no haya un caracter invisible al inicio del archivo (BOM)
                if (primeraLinea) {
                    if (linea.startsWith("\uFEFF")) {
                        linea = linea.substring(1);
                    }
                }
                primeraLinea = false;
                //ignorar lineas vacias
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|");  
                // confirmar que la linea si tenga suficientes caracteres para ser rial no fake
                if (datos.length < 2) {
                    continue; 
                }

                String isbn = datos[0].trim();   // Código ISBN 
                String titulo = datos[1].trim(); // Título del libro 
                
                Libro libro = new Libro();
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                
                catalogo.add(libro);
            }
        } 
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
        return catalogo;
    }
}