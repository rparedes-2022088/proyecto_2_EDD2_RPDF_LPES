/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Dario
 */
public class AlgoritmoCodificacion {

    public AlgoritmoCodificacion() {
    }
    
    public String codificar(String texto) throws NoSuchAlgorithmException{
        MessageDigest mensaje = MessageDigest.getInstance("MD5");
        byte[] hash = mensaje.digest(texto.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }
}
