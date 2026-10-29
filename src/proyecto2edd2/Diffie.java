/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.lang.Math;
import java.util.Random;

/**
 *
 * @author Dario
 */
public class Diffie {
    public int obtenerClavePrivada(int p, int g, int B){
        Random rand = new Random();
        int a = rand.nextInt(9) + 1;
        int A = (int)(Math.pow(g, a)) % p;
        int kA = (int)(Math.pow(B, a)) % p;
        return kA;
    }
}
