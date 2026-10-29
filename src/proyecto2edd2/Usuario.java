/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2edd2;

/**
 *
 * @author Dario
 */
public class Usuario {
    private String usuario;
    private String hash;
    private boolean premium;

    public Usuario() {
    }

    public Usuario(String usuario, String hash, boolean premium) {
        this.usuario = usuario;
        this.hash = hash;
        this.premium = premium;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    
    
}
