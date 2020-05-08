package com.example.proyecto_bar_reinols_movil;

import java.util.HashMap;

class Categoria {

    String nombre;
    HashMap<Integer, Producto> productos = new HashMap<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
