/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

/**
 *
 * @author Dario
 */
public class NodoHuffman {
    char caracter;
    int frecuencia;
    NodoHuffman izquierda;
    NodoHuffman derecha;

    @Override
    public String toString() {
        return "NodoHuffman{" + "caracter=" + caracter + ", frecuencia=" + frecuencia + '}';
    }
}
