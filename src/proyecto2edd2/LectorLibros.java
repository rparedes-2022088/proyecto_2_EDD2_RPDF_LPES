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
                // 1. Limpiar el carácter BOM de UTF-8 si está presente en la primera línea
                if (primeraLinea) {
                    if (linea.startsWith("\uFEFF")) {
                        linea = linea.substring(1);
                    }
                    
                    /* 
                     * IMPORTANTE: Si tu archivo de texto TIENE una cabecera (títulos de columnas 
                     * en la primera línea), descomenta las siguientes dos líneas para saltártela:
                     */
                    // primeraLinea = false;
                    // continue; 
                }
                primeraLinea = false;

                // 2. Ignorar líneas vacías por seguridad
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split("\\|");
                
                // 3. Validar que la línea tenga al menos los 2 datos necesarios
                if (datos.length < 2) {
                    continue; 
                }

                String isbn = datos[0].trim();   // Código ISBN limpio de espacios extra
                String titulo = datos[1].trim(); // Título del libro limpio
                
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