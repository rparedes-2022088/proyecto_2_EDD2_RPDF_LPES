/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.util.List;

/**
 *
 * @author luisp
 */
public class Algoritmo3DES {
    private static int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    private static int[] P8  = {6, 3, 7, 4, 8, 5, 10, 9};
    
    public Algoritmo3DES() {}

    public static String[] CifrarSDES(String clave) {
        //p10
        StringBuilder p10 = new StringBuilder();
        for (int i = 0; i < P10.length; i++) {
            p10.append(clave.charAt(P10[i] - 1));
        }
        
        //mitades
        String izquierda = p10.substring(0, 5);
        String derecha = p10.substring(5, 10);
        
        //shift 1
        izquierda = izquierda.substring(1) + izquierda.charAt(0);
        derecha = derecha.substring(1) + derecha.charAt(0);

        //k1
        String primer = izquierda + derecha;
        StringBuilder k1 = new StringBuilder();
        for (int i = 0; i < P8.length; i++) {
            k1.append(primer.charAt(P8[i] - 1));
        }

        // 5) shift 2 a las dos mitades
        izquierda = izquierda.substring(2) + izquierda.substring(0, 2);
        derecha = derecha.substring(2) + derecha.substring(0, 2);

        // 6) p8 a las mitades
        String segundo = izquierda + derecha;
        StringBuilder k2 = new StringBuilder();
        for (int i = 0; i < P8.length; i++) {
            k2.append(segundo.charAt(P8[i] - 1));
        }
        return new String[] { k1.toString(), k2.toString() };
    }
}
