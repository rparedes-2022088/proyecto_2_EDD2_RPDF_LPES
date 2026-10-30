/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2edd2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // TODO code application logic here
        //TODO implementar las lecturas de los libros
        Scanner sc = new Scanner(System.in);
        AlgoritmoCodificacion algoritmo = new AlgoritmoCodificacion();
        LectorUsuarios lectorUsr = new LectorUsuarios();
        LectorLibros lectorLibs = new LectorLibros();
        List<Usuario> usuarios = new ArrayList<Usuario>();
        final String carpetaUsuarios = "USUARIOS";
        final String carpetaTitulos = "TITULOS";
        final String carpetaTextos = "TEXTOS";
        final String nombreArUsrs = "usuarios.txt";
        final String nombreArTit = "titulos.txt";
        String libroDescargado = "";
        List<Libro> libros = new ArrayList<Libro>();
        System.out.println("Proyecto 2 - EDD2");
        
        //ingreso de datos para crear conexion al servidor
        System.out.println("Ingrese la IP del servidor al que desea acceder");
        String ip = sc.nextLine().trim();
        System.out.println("Ingrese la direccion de la donde se descargaran los archivos:");
        String direccion = sc.nextLine().trim();
        //crear conexion
        Conexion conexion = null;
        try {
            conexion = new Conexion(ip,direccion);
            System.out.println("\nconexion realizada con exito");
            System.out.println("Carpeta de descargas: " + direccion);     
        } catch (IOException e) {
            //en caso de que no funcione la conexion
            System.out.println("No se pudo realizar la conexion. Intente mas tarde");
        } finally {
            if (conexion != null) {
                conexion.cerrarConexion();
            }
            sc.close();
            System.out.println("Cerrando programa...");
        }

        conexion.descargarArchivo(carpetaUsuarios, nombreArUsrs);
        conexion.descargarArchivo(carpetaTitulos, nombreArTit);
        usuarios = lectorUsr.cargarUsuarios(nombreArUsrs);
        libros = lectorLibs.cargarLibros(nombreArTit);
        int opcion = 0;
        int opcion2 = 0;
        int opcion3 = 0;
        int libroSel = 0;
        Usuario usr = new Usuario();
        String usrName = "", contra = "";
        while(opcion != 4){
            while(usr.getUsuario() == null && usr.getHash() == null){
                System.out.println("=======Sistema de libros digitales=======");
                System.out.println("INICIO DE SESION");
                System.out.print("Ingrese su nombre de usuario: ");
                usrName = sc.nextLine();
                System.out.print("Ingrese su contrasena de usuario: ");
                contra = algoritmo.codificar(sc.nextLine());
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
                            System.out.println("*******CATALOGO DE LIBROS*******");
                            for(int i = 0; i < libros.size(); i++){
                                System.out.println((i+1) + ". ISBN: " + libros.get(i).getIsbn() + ", titulo: " + libros.get(i).getIsbn());
                            }
                            System.out.println("-------------Seleccione una opcion-------------");
                            System.out.println("1. Seleccionar libro");
                            System.out.println("2. Regresar al menu");
                            opcion2 = sc.nextInt();
                            sc.nextLine();
                            if(opcion2 == 1){
                                System.out.print("Ingrese el numero de libro a seleccionar: ");
                                libroSel = (sc.nextInt()-1);
                                sc.nextLine();
                                System.out.println("-------------Seleccione una opcion-------------");
                                System.out.println("1. Leer en linea");
                                System.out.println("2. Descargar libro");
                                opcion3 = sc.nextInt();
                                sc.nextLine();
                                //TODO: Mostrar el texto o descargar libro seleccionado
                                if(opcion3 == 1){
                                    
                                } else {
                                    conexion.descargarArchivo(carpetaTitulos,("ISBN" + libros.get(libroSel).getIsbn()+".txt"));
                                    System.out.println("Libro descargado exitosamente.");
                                }
                            }
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
                            System.out.println("*******CATALOGO DE LIBROS*******");
                            for(int i = 0; i < libros.size(); i++){
                                System.out.println((i+1) + ". ISBN: " + libros.get(i).getIsbn() + ", titulo: " + libros.get(i).getIsbn());
                            }
                            System.out.println("-------------Seleccione una opcion-------------");
                            System.out.println("1. Seleccionar libro");
                            System.out.println("2. Regresar al menu");
                            opcion2 = sc.nextInt();
                            sc.nextLine();
                            if(opcion2 == 1){
                                System.out.print("Ingrese el numero de libro a seleccionar: ");
                                libroSel = (sc.nextInt()-1);
                                sc.nextLine();
                                //TODO: Mostrar texto del libro seleccionado
                            }
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
