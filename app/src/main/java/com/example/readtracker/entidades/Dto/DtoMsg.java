package com.example.readtracker.entidades.Dto;

public class DtoMsg {
    private String msg;
    private int estado;

    public DtoMsg(String msg, int estado) {
        this.msg = msg;
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


}
