/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisp
 */
public class Conexion {
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Path carpetaDescargas;
    
    //constructor 67
    public Conexion(String ip, String direccionDescargas) throws IOException {
        carpetaDescargas = Path.of(direccionDescargas).toAbsolutePath().normalize();
        Files.createDirectories(carpetaDescargas);
        socket = new Socket(ip, 5000);
        entrada = new DataInputStream(socket.getInputStream());
        salida = new DataOutputStream(socket.getOutputStream());
    }

    //listado de archivos en una sola carpeta
    public List<String> listarArchivos(String carpeta) throws IOException {
        List<String> archivos = new ArrayList<>();
        salida.writeUTF("LISTAR");
        salida.writeUTF(carpeta);
        salida.flush();
        int cantidadArchivos = entrada.readInt();
        for (int i = 0; i < cantidadArchivos; i++) {
            String nombreArchivo = entrada.readUTF();
            archivos.add(nombreArchivo);
        }
        return archivos;
    }

    //copia loal del archivo desde el servidor
    public boolean descargarArchivo(String carpeta,String nombreArchivo) throws IOException {
        salida.writeUTF("DESCARGAR");
        salida.writeUTF(carpeta);
        salida.writeUTF(nombreArchivo);
        salida.flush();

        boolean existe = entrada.readBoolean();

        if (!existe) {
            System.out.println("El archivo no existe o no está disponible.");
            return false;
        }
        long tamano = entrada.readLong();
        Path destino = carpetaDescargas.resolve(nombreArchivo).normalize();
        if (!destino.startsWith(carpetaDescargas)) {
            System.out.println("Nombre de archivo no válido.");
            return false;
        }

        try (var archivo = Files.newOutputStream(destino)) {

            byte[] buffer = new byte[8192];

            long bytesRestantes = tamano;

            while (bytesRestantes > 0) {

                int cantidad = (int) Math.min(
                        buffer.length,
                        bytesRestantes
                );

                entrada.readFully(buffer, 0, cantidad);

                archivo.write(buffer, 0, cantidad);

                bytesRestantes = bytesRestantes - cantidad;
            }
        }

        System.out.println("\nDescarga completada.");
        System.out.println("Archivo: " + nombreArchivo);
        System.out.println("Tamaño: " + tamano + " bytes");
        System.out.println("Ubicación: " + destino);

        return true;
    }
    //terminar conexion con el servidor (ahorrar recursos)
    public void cerrarConexion() {

        try {
            if (salida != null) {
                salida.writeUTF("SALIR");
                salida.flush();
            }

            if (entrada != null) {
                entrada.close();
            }

            if (salida != null) {
                salida.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al cerrar la conexión: "
                    + e.getMessage()
            );
        }
    }
}
