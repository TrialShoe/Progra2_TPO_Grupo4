package org.example.TDAs;

public class DiccionarioImplDinamica<U, T> {
    // Clase interna para representar una tupla
    private static class Tupla<U, T> {
        U clave;
        T valor;

        Tupla(U clave, T valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    // Clase interna para representar un nodo
    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    // Nodo inicial de la lista
    private Nodo<Tupla<U, T>> cabeza;

    // Constructor
    public DiccionarioImplDinamica() {
        cabeza = null;
    }

    // Metodo para agregar un elemento al diccionario
    public void agregar(U clave, T valor) {
        Nodo<Tupla<U, T>> actual = cabeza;
        while (actual != null) {
            if (actual.dato.clave.equals(clave)) {
                actual.dato.valor = valor; // Actualizar el valor si la clave ya existe
                return;
            }
            actual = actual.siguiente;
        }
        // Agregar nueva tupla al inicio de la lista
        Nodo<Tupla<U, T>> nuevoNodo = new Nodo<>(new Tupla<>(clave, valor));
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
    }

    // Metodo para obtener un valor dado una clave
    public T obtener(U clave) {
        Nodo<Tupla<U, T>> actual = cabeza;
        while (actual != null) {
            if (actual.dato.clave.equals(clave)) {
                return actual.dato.valor;
            }
            actual = actual.siguiente;
        }
        return null; // Retornar null si la clave no existe
    }

    // Metodo para eliminar un elemento dado una clave
    public void eliminar(U clave) {
        Nodo<Tupla<U, T>> actual = cabeza;
        Nodo<Tupla<U, T>> anterior = null;

        while (actual != null) {
            if (actual.dato.clave.equals(clave)) {
                if (anterior == null) {
                    cabeza = actual.siguiente; // Eliminar el primer nodo
                } else {
                    anterior.siguiente = actual.siguiente; // Saltar el nodo actual
                }
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    // Metodo para verificar si el diccionario está vacío
    public boolean estaVacio() {
        return cabeza == null;
    }

    // Metodo para obtener el tamaño del diccionario
    public int tamano() {
        int contador = 0;
        Nodo<Tupla<U, T>> actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    // Metodo para verificar si una clave existe en el diccionario
    public boolean contieneClave(U clave) {
        Nodo<Tupla<U, T>> actual = cabeza;
        while (actual != null) {
            if (actual.dato.clave.equals(clave)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
}
