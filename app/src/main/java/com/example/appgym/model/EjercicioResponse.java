package com.example.appgym.model;

public class EjercicioResponse {
    private String nombre;
    private String categoria;
    private String imagen;

    public EjercicioResponse(String nombre, String categoria, String imagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
