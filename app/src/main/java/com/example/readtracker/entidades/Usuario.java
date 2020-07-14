package com.example.readtracker.entidades;
import java.io.Serializable;

public class Usuario implements Serializable{
private String nombreUsuario;
private String password;
private String correo;
private String usuarioId;

    public String getUsuarioId(){
        return usuarioId;
    }
    public String setUsuarioId(String usuarioId){
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
