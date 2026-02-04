package org.example;

import java.util.List;

public class Cliente {
    String nombre;
    int scoring;
    public List<String> siguiendo;
    public List<String> conexiones;

    public Cliente(String nombre, int scoring) {
        this.nombre = nombre;
        this.scoring = scoring;
    }

    @Override
    public String toString() {
        return "Cliente: " + "\n" + "nombre = " + nombre + "\n" + "scoring = " + scoring;
    }
}
