package com.example.readtracker.entidades.Dto;

import com.example.readtracker.entidades.Reading;

public class DtoReading {

    private int estado;
    private String msg;
    private Reading[] readings;

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

    public Reading[] getReadings() {
        return readings;
    }

    public void setReadings(Reading[] readings) {
        this.readings = readings;
    }

}
