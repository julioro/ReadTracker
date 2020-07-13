package com.example.readtracker.entidades.Dto;

import com.example.readtracker.entidades.Lectura;

public class DtoLectura {

    private int estado;
    private String msg;
    private Lectura[] lecturas;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Lectura[] getLecturas() {
        return lecturas;
    }

    public void setLecturas(Lectura[] lecturas) {
        this.lecturas = lecturas;
    }

}
