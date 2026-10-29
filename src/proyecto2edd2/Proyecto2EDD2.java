/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2edd2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Dario
 * Ruben Dario Paredes Flores - 1152225
 * Luis Paulo Espinoza Sandoval - 1202925
 */
public class Proyecto2EDD2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        List<Usuario> usuarios = new ArrayList<Usuario>();
        List<Libro> libros = new ArrayList<Libro>();
        int opcion = 0;
        Usuario usr = new Usuario();
        String usrName = "", contra = "";
        while(opcion != 4){
            while(usr.getUsuario() == null && usr.getHash() == null){
                System.out.println("=======Sistema de libros digitales=======");
                System.out.println("INICIO DE SESION");
                System.out.print("Ingrese su nombre de usuario: ");
                usrName = sc.nextLine();
                System.out.print("Ingrese su contrasena de usuario: ");
                contra = sc.nextLine();
                for(Usuario usuario:usuarios){
                    if(usuario.getUsuario().equals(usrName) && usuario.getHash().equals(contra)){
                        usr = usuario;
                    }
                }
            }
            while(opcion != 3){
                if(usr.isPremium()){
                    System.out.println("=========================================");
                    System.out.println("Seleccione una opcion: ");
                    System.out.println("1. Explorar catalogo");
                    System.out.println("2. Lectura local");
                    System.out.println("3. Cerrar sesion");
                    System.out.println("4. Salir");
                    opcion = sc.nextInt();
                    sc.nextLine();
                    switch(opcion){
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            usr.setUsuario(null);
                            usr.setHash(null);
                            usr.setPremium(false);
                            System.out.println("Cerrando sesion...");
                            break;
                        case 4:
                            System.out.println("Saliendo del sistema, vuelva pronto :D ...");
                            break;
                        default:
                            System.out.println("Opcion invalida, intentelo nuevamente...");
                    } 
                } else {
                    System.out.println("=========================================");
                    System.out.println("Seleccione una opcion: ");
                    System.out.println("1. Explorar catalogo");
                    System.out.println("2. Cerrar sesion");
                    System.out.println("3. Salir");
                    opcion = sc.nextInt();
                    sc.nextLine();
                    switch(opcion){
                        case 1:
                            break;
                        case 2:
                            usr.setUsuario(null);
                            usr.setHash(null);
                            usr.setPremium(false);
                            System.out.println("Cerrando sesion...");
                            break;
                        case 3:
                            System.out.println("Saliendo del sistema, vuelva pronto :D ...");
                            break;
                        default:
                            System.out.println("Opcion invalida, intentelo nuevamente...");
                    } 
                }
            }
        }
    }
    
}
