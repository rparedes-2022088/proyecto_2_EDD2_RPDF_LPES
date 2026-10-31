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

    public Algoritmo3DES() {
    }

    //claveD = clave Diffie
    public codificar(int claveD) {

    }

    public void cifrarSDES(String llave) {
        boolean correcto = false;
        while (correcto == false) {
            if (llave.length() != 10) {
                System.out.println("Llave de lengitud incorrecta, intente nuevamente...");
            } else if (llave.length() == 10) {
                correcto = true;
            }
        }
        List<Boolean> clave = SDES.bits(llave);
        List<Boolean> bloque = SDES.bits(texto);

        System.out.println("Clave: " + llave);
        System.out.println("Bloque original: " + texto);

        // Crear instancia de SDES (genera las subclaves K1 y K2)
        SDES sdes = new SDES(clave);

        // Cifrar el bloque
        List<Boolean> cifrado = sdes.cifrar(bloque);
        String cifradoStr = SDES.bitsAString(cifrado);
        System.out.println("Bloque cifrado: " + cifradoStr);

    }
}
