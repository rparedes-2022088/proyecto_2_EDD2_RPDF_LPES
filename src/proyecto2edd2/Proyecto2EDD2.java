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
        Scanner sc = new Scanner(System.in);
        AlgoritmoCodificacion algoritmo = new AlgoritmoCodificacion();
        Diffie diffie = new Diffie();
        LectorUsuarios lectorUsr = new LectorUsuarios();
        LectorLibros lectorLibs = new LectorLibros();
        LectorTexto lectorText = new LectorTexto();
        List<Usuario> usuarios = new ArrayList<Usuario>();
        boolean terminar = false;
        final String carpetaUsuarios = "Usuarios";
        final String carpetaTitulos = "Catalogo";
        final String carpetaTextos = "Libros";
        final String nombreArUsrs = "MD5.txt";
        final String nombreArTit = "Catalogo.txt";
        String libroDescargado = "";
        List<Libro> libros = new ArrayList<Libro>();
        System.out.println("Proyecto 2 - EDD2");
        
        //ingreso de datos para crear conexion al servidor
        System.out.print("Ingrese la IP del servidor al que desea acceder: ");
        String ip = sc.nextLine().trim();
        System.out.print("Ingrese la direccion de la donde se descargaran los archivos: ");
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
        }/* finally {
            if (conexion != null) {
                conexion.cerrarConexion();
            }
            sc.close();
            System.out.println("Cerrando programa...");
        }*/
        
        String[] k = new String[3];
        k = diffie.obtenerClavesPrivada(sc);
        
        SDES sdes1 = new SDES(diffie.bits(k[0]));
        SDES sdes2 = new SDES(diffie.bits(k[1]));
        SDES sdes3 = new SDES(diffie.bits(k[2]));
        
        conexion.descargarArchivo(carpetaUsuarios, nombreArUsrs);
        conexion.descargarArchivo(carpetaTitulos, nombreArTit);
        usuarios = lectorUsr.cargarUsuarios(direccion + "/" + nombreArUsrs);
        libros = lectorLibs.cargarLibros(direccion + "/" + nombreArTit);
        int opcion = 0;
        int opcion2 = 0;
        int opcion3 = 0;
        int libroSel = 0;
        Usuario usr = new Usuario();
        String usrName = "", contra = "";
        while(terminar == false){
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
                        break;
                    }
                }
            }
            if(usr.isPremium()){
                while(opcion != 3 && terminar == false){
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
                                System.out.println((i+1) + ". ISBN: " + libros.get(i).getIsbn() + ", titulo: " + libros.get(i).getTitulo());
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
                                conexion.descargarArchivo(carpetaTextos,(libros.get(libroSel).getIsbn()+".bin"));
                                System.out.println("Libro descargado exitosamente.");
                                if(opcion3 == 1){
                                    lectorText.mostrarTexto(direccion + "/" + libros.get(libroSel).getIsbn()+".bin", sdes1, sdes2, sdes3);
                                } else {
                                    lectorText.descargarYComprimirLibro(direccion + "/" + libros.get(libroSel).getIsbn()+".bin", direccion + "/" + libros.get(libroSel).getIsbn()+"_compressed.txt", sdes1, sdes2, sdes3);
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Ingrese el numero de ISBN del libro descargado");
                            String isbn = sc.nextLine();
                            lectorText.leerLibroLocalComprimido(direccion + "/" + "ISBN" + isbn +"_compressed.txt");
                            break;
                        case 3:
                            usr = new Usuario();
                            System.out.println("Cerrando sesion...");
                            break;
                        case 4:
                            terminar = true;
                            System.out.println("Saliendo del sistema, vuelva pronto :D ...");
                            break;
                        default:
                            System.out.println("Opcion invalida, intentelo nuevamente...");
                    }
                }
            } else {
                while(opcion != 2 && terminar == false){
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
                                System.out.println((i+1) + ". ISBN: " + libros.get(i).getIsbn() + ", titulo: " + libros.get(i).getTitulo());
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
                                conexion.descargarArchivo(carpetaTextos,(libros.get(libroSel).getIsbn()+".bin"));
                                System.out.println("Libro descargado exitosamente.");
                                lectorText.mostrarTexto(direccion + "/" + libros.get(libroSel).getIsbn()+".bin", sdes1, sdes2, sdes3);
                            }
                            break;
                        case 2:
                            usr = new Usuario();
                            System.out.println("Cerrando sesion...");
                            break;
                        case 3:
                            terminar = true;
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
