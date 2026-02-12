package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    String nombre;
    int scoring;
    public List<String> siguiendo;
    public List<String> conexiones;

    // Constructor para agregar datos A MANO.
    public Cliente(String nombre, int scoring) {
        this.nombre = nombre;
        this.scoring = scoring;
        this.siguiendo = new ArrayList<>();
        this.conexiones = new ArrayList<>();
    }

    // Constructor para leer datos del JSON.
    public Cliente(String nombre, int scoring, List<String> siguiendo, List<String> conexiones) {
        this.nombre = nombre;
        this.scoring = scoring;
        this.siguiendo = (siguiendo != null) ? siguiendo : new ArrayList<>();
        this.conexiones = (conexiones != null) ? conexiones : new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getScoring() {
        return scoring;
    }

    public List<String> getSiguiendo() {
        return siguiendo;
    }

    public List<String> getConexiones() {
        return conexiones;
    }

    public boolean yaSigue(String nombreSeguido) {
        return siguiendo.contains(nombreSeguido);
    }

    public void seguir(String nombreSeguido) {
        siguiendo.add(nombreSeguido);
    }

    public boolean dejarDeSeguir(String nombreSeguido) {
        return siguiendo.remove(nombreSeguido);
    }

    @Override
    public String toString() {
        return "Nombre = " + nombre.trim() + "\n"
                + "Scoring = " + scoring + "\n"
                + "Siguiendo = " + siguiendo + "\n"
                + "Conexiones = " + conexiones + "\n";
    }
}

//agregar las solicitudes por cada cliente acá.
//cada cliente tiene su cola de la gente que sigue.