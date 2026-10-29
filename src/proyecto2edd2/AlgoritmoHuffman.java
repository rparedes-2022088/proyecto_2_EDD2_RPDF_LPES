/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Dario
 */
public class AlgoritmoHuffman {
    static final int MAX_CHARS = 256;

    static char[] caracteres = new char[MAX_CHARS];
    static String[] codigos = new String[MAX_CHARS];
    static int conteoCodigos = 0;

    public static int[] conteoFrecuencias(String texto) {
        int[] freq = new int[MAX_CHARS];

        for (int i = 0; i < texto.length(); i++) {
            freq[texto.charAt(i)]++;
        }

        return freq;
    }

    public static ArrayList<NodoHuffman> crearLista(int[] freq) {
        ArrayList<NodoHuffman> nodoLista = new ArrayList<>();

        for (int i = 0; i < MAX_CHARS; i++) {
            if (freq[i] > 0) {
                NodoHuffman node = new NodoHuffman();
                node.caracter = (char) i;
                node.frecuencia = freq[i];
                node.izquierda = null;
                node.derecha = null;
                nodoLista.add(node);
            }
        }

        return nodoLista;
    }

    public static void OrdenarLista(ArrayList<NodoHuffman> lista) {
        Collections.sort(lista, (NodoHuffman n1, NodoHuffman n2) -> n1.frecuencia - n2.frecuencia);
    }

    public static void recorreArbol(NodoHuffman node, int nivel){
        if ((!(node==null))){
            recorreArbol(node.derecha, nivel+1);
            if(!(node.caracter=='-')){            
            }
            recorreArbol(node.izquierda, nivel+1);
        }
    }

    public static NodoHuffman contruirArbol(String texto) {
        int[] frequencies = conteoFrecuencias(texto);
        ArrayList<NodoHuffman> nodes = crearLista(frequencies);
        OrdenarLista(nodes);
        while (nodes.size() > 1) {
            OrdenarLista(nodes);

            NodoHuffman left = nodes.remove(0);
            NodoHuffman right = nodes.remove(0);

            NodoHuffman parent = new NodoHuffman();
            parent.caracter = '-';
            parent.frecuencia = left.frecuencia + right.frecuencia;
            parent.izquierda = left;
            parent.derecha = right;
            nodes.add(parent);
        }
        return nodes.get(0);
    }
    
    public static void generarCodigos(NodoHuffman raiz, String codigo) {
        if (raiz == null) return;
        if (raiz.izquierda == null && raiz.derecha == null) {
            caracteres[conteoCodigos] = raiz.caracter;
            codigos[conteoCodigos] = codigo;
            conteoCodigos++;
            return;
        }
        generarCodigos(raiz.izquierda, codigo + "0");
        generarCodigos(raiz.derecha, codigo + "1");
        recorreArbol(raiz,0);
    }

    public static String Codigo(char c) {
        for (int i = 0; i < conteoCodigos; i++) {
            if (caracteres[i] == c) return codigos[i];
        }
        return ""; 
    }

    public static String Comprimir(String texto) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            sb.append(Codigo(texto.charAt(i)));
        }

        return sb.toString();
    }
    
    public static String Descomprimir(String textoComprimido, NodoHuffman raiz) {
        StringBuilder textoOriginal = new StringBuilder();
        NodoHuffman nodoActual = raiz;

        for (int i = 0; i < textoComprimido.length(); i++) {
            char bit = textoComprimido.charAt(i);

            if (bit == '0') {
                nodoActual = nodoActual.izquierda;
            } else {
                nodoActual = nodoActual.derecha;
            }

            if (nodoActual.izquierda == null && nodoActual.derecha == null) {
                textoOriginal.append(nodoActual.caracter);
                nodoActual = raiz;
            }
        }

        return textoOriginal.toString();
    }
}
