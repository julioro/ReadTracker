package com.example.readtracker.entidades;

import java.util.Date;

public class Lectura {
    private String id;
    private String titulo;
    private boolean estado;
    private String url;
    private int paginas;
    private String etiqueta;
    private Date fechaLeida;
    private Usuario usuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Date getFechaLeida() {
        return fechaLeida;
    }

    public void setFechaLeida(Date fechaLeida) {
        this.fechaLeida = fechaLeida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getInfo(){
        return this.titulo + " " + this.etiqueta + " " + String.valueOf(this.estado) + String.valueOf(this.fechaLeida);
    }



}
