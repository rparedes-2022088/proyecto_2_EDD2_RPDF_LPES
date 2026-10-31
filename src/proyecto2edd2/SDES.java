/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dario
 */
public class SDES {
    private static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};


    private static final int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};


    private static final int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};


    private static final int[] IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};


    private static final int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};


    private static final int[] P4 = {2, 4, 3, 1};


    private static final int[][] S0 = {
        {1, 0, 3, 2},
        {3, 2, 1, 0},
        {0, 2, 1, 3},
        {3, 1, 3, 2}
    };


    private static final int[][] S1 = {
        {0, 1, 2, 3},
        {2, 0, 1, 3},
        {3, 0, 1, 0},
        {2, 1, 0, 3}
    };


    private List<Boolean> K1;
    private List<Boolean> K2;




    // CONSTRUCTOR


    public SDES(List<Boolean> clave) {
        generarSubclaves(clave);


    }




    // ============================================================
    // GENERACIÓN DE SUBCLAVES
    // ============================================================


    private void generarSubclaves(List<Boolean> clave) {
        // Paso 1: Aplicar P10
        List<Boolean> permutada = permutar(clave, P10);


        // Paso 2: Dividir en dos mitades de 5 bits
        List<Boolean> izquierda = new ArrayList<>(permutada.subList(0, 5));
        List<Boolean> derecha = new ArrayList<>(permutada.subList(5, 10));


        // Paso 3: LS-1 (desplazamiento circular a la izquierda de 1)
        izquierda = desplazamientoIzquierda(izquierda, 1);
        derecha = desplazamientoIzquierda(derecha, 1);


        // Paso 4: Combinar y aplicar P8 para obtener K1
        List<Boolean> combinado1 = combinar(izquierda, derecha);
        // List<Boolean> k1 = permutar(combinador1,p8);
        K1 = permutar(combinado1, P8);


        // Paso 5: LS-2 (desplazamiento circular a la izquierda de 2 más)
        izquierda = desplazamientoIzquierda(izquierda, 2);
        derecha = desplazamientoIzquierda(derecha, 2);


        // Paso 6: Combinar y aplicar P8 para obtener K2
        List<Boolean> combinado2 = combinar(izquierda, derecha);
        // List<Boolean> k2 = permutar(combinador1,p8);
        K2 = permutar(combinado2, P8);
    }




    // CIFRADO


    public List<Boolean> cifrar(List<Boolean> bloque) {
        // Paso 1: Aplicar IP (Initial Permutation)
        List<Boolean> ipResult = permutar(bloque, IP);


        // Paso 2: Aplicar fk con K1
        List<Boolean> fk1 = fk(ipResult, K1);


        // Paso 3: SW (intercambiar mitades)
        List<Boolean> swResult = swap(fk1);


        // Paso 4: Aplicar fk con K2
        List<Boolean> fk2 = fk(swResult, K2);


        // Paso 5: Aplicar IP^-1 (Inverse IP)
        List<Boolean> resultado = permutar(fk2, IP_INV);


        return resultado;
    }




    // DESCIFRADO


    public List<Boolean> descifrar(List<Boolean> bloque) {
        // Paso 1: Aplicar IP (Initial Permutation)
        List<Boolean> ipResult = permutar(bloque, IP);


        // Paso 2: Aplicar fk con K2 (invertido respecto al cifrado)
        List<Boolean> fk1 = fk(ipResult, K2);


        // Paso 3: SW (intercambiar mitades)
        List<Boolean> swResult = swap(fk1);


        // Paso 4: Aplicar fk con K1 (invertido respecto al cifrado)
        List<Boolean> fk2 = fk(swResult, K1);


        // Paso 5: Aplicar IP^-1 (Inverse IP)
        List<Boolean> resultado = permutar(fk2, IP_INV);


        return resultado;
    }




    // FUNCIÓN Fk


    private List<Boolean> fk(List<Boolean> bloque,List<Boolean> clave) {
        // Dividir en izquierda (4 bits) y derecha (4 bits)
        List<Boolean> izquierda = new ArrayList<>(bloque.subList(0, 4));
        List<Boolean> derecha = new ArrayList<>(bloque.subList(4, 8));


        // Aplicar función F a la derecha con la clave
        List<Boolean> fResult = funcionF(derecha, clave);


        // XOR de izquierda con resultado de F
        List<Boolean> nuevaIzquierda = xor(izquierda, fResult);


        // Combinar nueva izquierda con derecha (sin cambios)
        return combinar(nuevaIzquierda, derecha);
    }




    // ============================================================
    // FUNCIÓN F
    // ============================================================


    private List<Boolean> funcionF(List<Boolean> derecha,List<Boolean> clave) {
        // Paso 1: Aplicar EP (Expansion Permutation) - expande 4 bits a 8 bits
        List<Boolean> epResult = permutar(derecha, EP);


        // Paso 2: XOR con la clave
        List<Boolean> xorResult = xor(epResult, clave);


        // Paso 3: Dividir en dos mitades de 4 bits
        List<Boolean> izquierdaS = new ArrayList<>(xorResult.subList(0, 4));
        List<Boolean> derechaS = new ArrayList<>(xorResult.subList(4, 8));


        // Paso 4: Aplicar S-boxes
        List<Boolean> s0Result = sBox(izquierdaS, S0);
        List<Boolean> s1Result = sBox(derechaS, S1);


        // Paso 5: Combinar resultados (4 bits totales)
        List<Boolean> combinadoS = combinar(s0Result, s1Result);


        // Paso 6: Aplicar P4
        List<Boolean> resultado = permutar(combinadoS, P4);


        return resultado;
    }




    // S-BOX


    private List<Boolean> sBox(List<Boolean> bits,int[][] sbox) {


        /*
         * Bits:
         *
         * b0 b1 b2 b3
         *
         * Fila = b0b3
         * Columna = b1b2
         */


        int fila =
                (bits.get(0) ? 2 : 0) +
                (bits.get(3) ? 1 : 0);


        int columna =
                (bits.get(1) ? 2 : 0) +
                (bits.get(2) ? 1 : 0);


        int valor = sbox[fila][columna];


        List<Boolean> resultado = new ArrayList<>();


        // Convertir el valor 0-3 a dos bits
        resultado.add((valor & 2) != 0);
        resultado.add((valor & 1) != 0);


        return resultado;
    }




    // XOR
    private List<Boolean> xor(List<Boolean> a,List<Boolean> b) {


        List<Boolean> resultado = new ArrayList<>();


        for (int i = 0; i < a.size(); i++) {


            // XOR lógico
            resultado.add(a.get(i) ^ b.get(i));


        }


        return resultado;
    }




    // PERMUTACIÓN
    
    private List<Boolean> permutar(List<Boolean> bits,int[] posiciones) {


        List<Boolean> resultado = new ArrayList<>();


        for (int posicion : posiciones) {


            // Las posiciones de S-DES comienzan en 1
            resultado.add(bits.get(posicion - 1));


        }


        return resultado;
    }


	// DESPLAZAMIENTO CIRCULAR A LA IZQUIERDA


    private List<Boolean> desplazamientoIzquierda(List<Boolean> bits,int posiciones) {


        List<Boolean> resultado = new ArrayList<>(bits);


        for (int i = 0; i < posiciones; i++) {


            Boolean primero = resultado.remove(0);


            resultado.add(primero);
        }


        return resultado;
    }




    // SW
    
    private List<Boolean> swap(List<Boolean> bits) {
        // Intercambiar las dos mitades de 4 bits
        List<Boolean> izquierda = new ArrayList<>(bits.subList(0, 4));
        List<Boolean> derecha = new ArrayList<>(bits.subList(4, 8));


        // Combinar en orden invertido (derecha primero)
        return combinar(derecha, izquierda);
    }




    // COMBINAR LISTAS


    private List<Boolean> combinar(List<Boolean> izquierda,List<Boolean> derecha) {


        List<Boolean> resultado = new ArrayList<>();


        resultado.addAll(izquierda);
        resultado.addAll(derecha);


        return resultado;
    }




    // CONVERSIÓN STRING -> LIST<Boolean>
    
    public static List<Boolean> bits(String cadena) {


        List<Boolean> resultado = new ArrayList<>();


        for (char c : cadena.toCharArray()) {


            if (c == '0') {
                resultado.add(false);
            } else if (c == '1') {
                resultado.add(true);
            }


        }


        return resultado;
    }

    public static List<List<Boolean>> textoABloques(String texto) {
        List<List<Boolean>> bloques = new ArrayList<>();
        
        // Obtiene el valor numérico (código ASCII/UTF-8) de cada carácter
        byte[] bytes = texto.getBytes();
        
        for (byte b : bytes) {
            List<Boolean> bloque = new ArrayList<>();
            
            // Extrae los 8 bits del byte, desde el más significativo (posición 7) al menos (0)
            for (int i = 7; i >= 0; i--) {
                // Desplaza el bit actual hacia la derecha y aplica una máscara AND (1)
                boolean bit = ((b >> i) & 1) == 1;
                bloque.add(bit);
            }
            
            bloques.add(bloque);
        }
        
        return bloques;
    }
    
    public static String bloquesATexto(List<List<Boolean>> bloques) {
        byte[] bytes = new byte[bloques.size()];
        
        for (int i = 0; i < bloques.size(); i++) {
            List<Boolean> bloque = bloques.get(i);
            byte b = 0;
            
            // Reconstruye el byte leyendo de izquierda (más significativo) a derecha
            for (int j = 0; j < 8; j++) {
                if (bloque.get(j)) {
                    // Desplaza un '1' a la posición correcta (7, 6, 5... 0) y lo suma con OR
                    b |= (1 << (7 - j));
                }
            }
            bytes[i] = b;
        }
        
        // Convierte el arreglo de bytes reconstruido de vuelta a caracteres
        return new String(bytes);
    }
    
    // CONVERSIÓN LIST<Boolean> -> STRING
    public static String bitsAString(List<Boolean> bits) {


        StringBuilder resultado = new StringBuilder();


        for (Boolean bit : bits) {


            resultado.append(bit ? '1' : '0');


        }


        return resultado.toString();
    }
}
