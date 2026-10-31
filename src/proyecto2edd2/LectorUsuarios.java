/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class LectorUsuarios {
    public List<Usuario> cargarUsuarios(String ruta) {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|");
                String usuarioStr = datos[0]; // nombre del usuario
                String hash = datos[1];// clave con Hash
                boolean esPremium = Boolean.parseBoolean(datos[2]); // verificacion de si es premium
                
                Usuario user = new Usuario();
                user.setUsuario(usuarioStr);
                user.setHash(hash);
                user.setPremium(esPremium);
                usuarios.add(user);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        
        return usuarios;
    }
}
