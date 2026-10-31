package proyecto2edd2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisp
 */
public class Algoritmo3DES {
    public Algoritmo3DES() {}
    
    public String desencriptar3DSBinario(SDES sdes1, SDES sdes2, SDES sdes3, byte[] bytesEncriptados) {
        List<List<Boolean>> bloques = new ArrayList<>();
        for (byte b : bytesEncriptados) {
            List<Boolean> bloque = new ArrayList<>();
            for (int i = 7; i >= 0; i--) {
                bloque.add(((b >> i) & 1) == 1);
            }
            bloques.add(bloque);
        }
        
        List<List<Boolean>> bloquesDescifrados = new ArrayList<>();
        for(List<Boolean> bloque : bloques){
            List<Boolean> fase1 = sdes3.descifrar(bloque);
            List<Boolean> fase2 = sdes2.cifrar(fase1);
            List<Boolean> fase3 = sdes1.descifrar(fase2);
            bloquesDescifrados.add(fase3);
        }
        return sdes1.bloquesATexto(bloquesDescifrados);
    }
}