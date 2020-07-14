package com.example.readtracker.entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", correo='" + correo + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", listReadings=" + listReadings +
                '}';
    }

    public User() {
    }

    public User(String correo, String usuarioId, ArrayList<Reading> listReadings) {
        this.correo = correo;
        this.usuarioId = usuarioId;
        this.listReadings = listReadings;
    }

    private String nombreUsuario;
    private String password;
    private String correo;
    private String usuarioId;
    private ArrayList<Reading> listReadings;

    public ArrayList<Reading> getListReadings() {
        return listReadings;
    }

    public void setListReadings(ArrayList<Reading> listReadings) {
        this.listReadings = listReadings;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
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
