package org.example.TDAs;

public class ImpPila implements TADPila{

    private Nodo cima;

    public ImpPila() {
        this.cima = null;
    }

    public void apilar(String dato) {
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
    }

    public String desapilar() {
        if (cima == null) {
            return "Error: La pila está vacía.";
        }
        String dato = cima.dato;
        cima = cima.siguiente;
        return dato;
    }

    public String cima() {
        if (cima == null) {
            return "Error: La pila está vacía.";
        }
        return cima.dato;
    }

    public boolean estaVacia() {
        return cima == null;
    }
}