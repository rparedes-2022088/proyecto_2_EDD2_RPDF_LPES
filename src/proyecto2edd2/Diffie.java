/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Dario
 */
public class Diffie {

    public Diffie() {
    }
    
    public String[] obtenerClavesPrivada(){
        Scanner sc = new Scanner(System.in);
        int[] P = new int[3];
        int[] G = new int[3];
        int[] a = new int[3];
        int[] b = new int[3];
        String[] k = new String[3];
        int[] A = new int[3];
        int[] B = new int[3];
        int[] kA = new int[3];
        int[] kB = new int[3];
        for(int i = 0; i < 3; i++){
            System.out.print("Ingrese P" + (1+1) + ": ");
            P[i] = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese G" + (1+1) + ": ");
            G[i] = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese a" + (1+1) + ": ");
            a[i] = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese b" + (1+1) + ": ");
            b[i] = sc.nextInt();
            sc.nextLine();
            A[i] = (int)(Math.pow(G[i], a[i])) % P[i];
            B[i] = (int)(Math.pow(G[i], b[i])) % P[i];
            kA[i] = (int)(Math.pow(B[i], a[i])) % P[i];
            kB[i] = (int)(Math.pow(A[i], b[i])) % P[i];
            if(kA[i] == kB[i]){
                String kBin = Integer.toBinaryString(kA[i]);
                if(kBin.length() < 10){
                    int cerosFaltantes = 10 - kBin.length();
                    while(cerosFaltantes > 0){
                        kBin = 0 + kBin;
                        cerosFaltantes -= 1;
                    }
                }
                k[i] = kBin;
            }
        }
        return k;
    }
}
