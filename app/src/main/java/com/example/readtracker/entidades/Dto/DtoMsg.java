package com.example.readtracker.entidades.Dto;

public class DtoMsg {
    private String msg;
    private int estado;

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

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }



    public DtoMsg(Object object){
        this.object = object;
    }
    public DtoMsg(String msg, int estado) {
        this.msg = msg;
        this.estado = estado;
    }

    public DtoMsg(String msg, int estado, Object object) {
        this.msg = msg;
        this.estado = estado;
        this.object = object;
    }



}
