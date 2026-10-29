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
    public List<Usuario> cargarUsuarios(String ruta){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ruta));
            String linea = lector.readLine();
            Usuario user = new Usuario();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|");
                String usuario =  datos[0]; //nombre del usuario
                String hash = datos[1]; //clave con Hash
                boolean esPremium = Boolean.parseBoolean(datos[2]); //verificacion de si es usuario
                user.setUsuario(usuario);
                user.setHash(hash);
                user.setPremium(esPremium);
                usuarios.add(user);
            }
            lector.close();
        } 
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return usuarios;
    }
}
