package org.example.TDAs.Arbol;

public class ABB<T extends Comparable<T>> implements iArbolBinarioTDA<T> {

    ArbolBinario<T> arbolBinario;

    public ABB() {
        arbolBinario = new ArbolBinario<T>();
    }

    @Override
    public void crearArbol() {
        arbolBinario.crearArbol();
    }

    public T maximo() {
        return maximo(arbolBinario.raiz);
    }

    private T maximo(Nodo<T> nodo) {
        if(nodo == null) return null;
        if(nodo.der == null) return nodo.elemento;
        return maximo(nodo.der );
    }



    @Override
    public void agregarElemento(T elemento) {
        arbolBinario.raiz = agregar(arbolBinario.raiz, elemento);
    }

    private Nodo<T> agregar(Nodo<T> nodo, T elemento) {
        if (nodo == null) {
            nodo = new Nodo<T>(elemento);
        } else {
            if (elemento.compareTo(nodo.elemento) < 0) {
                nodo.izq = agregar(nodo.izq, elemento);
            } else {
                nodo.der = agregar(nodo.der, elemento);
            }
        }
        return nodo;
    }

    @Override
    public void imprimir() {
        arbolBinario.imprimir();
    }

    @Override
    public boolean estaVacio() {
        return arbolBinario.estaVacio();
    }

    @Override
    public void elimar(T elemento) {
        arbolBinario.elimar(elemento);
    }

    @Override
    public T maximaHojas() {
        return arbolBinario.maximaHojas();
    }






    public void imprimirNivel(int nivel) {
        imprimirNivel(arbolBinario.raiz, 1, nivel); // raíz en nivel 1
    }

    private void imprimirNivel(Nodo<T> nodo, int actual, int objetivo) {
        if (nodo == null) return;

        if (actual == objetivo) {
            System.out.println(nodo.elemento); // imprime ESTE nodo del nivel
            return;
        }

        imprimirNivel(nodo.izq, actual + 1, objetivo);
        imprimirNivel(nodo.der, actual + 1, objetivo);
    }



}

