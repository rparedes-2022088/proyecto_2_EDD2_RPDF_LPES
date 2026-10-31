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
    public List<Libro> cargarLibros(String ruta){
        List<Libro> catalogo = new ArrayList<Libro>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                Libro libro = new Libro();
                String[] datos = linea.split("\\|");
                String isbn =  datos[0]; //codigo ISBN del libro actual
                String titulo = datos[1]; //Titulo del libro ingresado
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                catalogo.add(libro);
            }
            lector.close();
        } 
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return catalogo;
    }
}
