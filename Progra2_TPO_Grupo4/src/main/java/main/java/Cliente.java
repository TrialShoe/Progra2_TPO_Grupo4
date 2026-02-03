package main.java;

public class Cliente {
    String nombre;
    int scoring;

    public Cliente(String nombre, int scoring) {
        this.nombre = nombre;
        this.scoring = scoring;
    }

    public String getNombre() {
        return nombre;
    }

    public int getScoring() {
        return scoring;
    }

    @Override
    public String toString() {
        return nombre;
    }


    // Hacer una función de "VerClientes()" con el Nombre: ... y Scoring: ... con un DICCIONARIO una vez que lo aprendamos.
}
