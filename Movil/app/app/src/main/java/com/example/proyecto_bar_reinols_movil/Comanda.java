package com.example.proyecto_bar_reinols_movil;

import android.widget.Toast;

public class Comanda {

    String nombreProducto;
    int cantidad;

    public Comanda(String nombreProducto, int cantidad) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public Comanda() {}

    public void sumarProducto(){
        this.cantidad++;
    }

    public void restarProducto(){

    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
