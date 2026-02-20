package org.example.TDAs.Arbol;

import java.util.Random;

public class ArbolBinario<T extends Comparable<T>> implements  iArbolBinarioTDA<T>{
    Nodo<T> raiz;
    static Random random = new Random();
    @Override
    public void crearArbol() {
        this.raiz = null;
    }

    public T maximaHojas() {
        //recorrer el arbol y devolver la maxima hoja
        return maximaHojas(raiz);
    }

    private T maximaHojas(Nodo<T> nodo) {
        if(nodo == null) return null;
        if(nodo.izq == null && nodo.der == null) return nodo.elemento;
        T maxIzq = maximaHojas(nodo.izq);
        T maxDer = maximaHojas(nodo.der);
        if(maxIzq == null) return maxDer;
        if(maxDer == null) return maxIzq;
        return maxIzq.compareTo(maxDer) > 0 ? maxIzq : maxDer;
    }

    public int longitudDeLaRamaMasCorta() {
        return longitudDeLaRamaMasCorta(raiz);
    }

    private int longitudDeLaRamaMasCorta(Nodo<T> nodo) {
        if(nodo == null) return 0;
        return 1 + Math.min(longitudDeLaRamaMasCorta(nodo.izq), longitudDeLaRamaMasCorta(nodo.der));
    }

    // Cambiar el nodo actual con el mayor valor de sus hijos, siempre que ambos
    // no sean nulos. Si son nulos o alguno de los dos es nulo no hace nada.

    public void cambiarNodoActualConElMayorValorDeSusHijos() {
        cambiarNodoActualConElMayorValorDeSusHijos(raiz);
    }

    private void cambiarNodoActualConElMayorValorDeSusHijos(Nodo<T> nodo) {
        if(nodo == null) return;
        if(nodo.izq == null && nodo.der == null) return;
        if(nodo.izq == null) {
            nodo.elemento = nodo.der.elemento;
            nodo.der = null;
        } else if (nodo.der == null) {
            nodo.elemento = nodo.izq.elemento;
            nodo.izq = null;
        } else {
            T maxIzq = maximaHojas(nodo.izq);
            T maxDer = maximaHojas(nodo.der);
            if(maxIzq.compareTo(maxDer) > 0) {
                nodo.elemento = maxIzq;
                nodo.izq = null;
            } else {
                nodo.elemento = maxDer;
                nodo.der = null;
            }
        }
    }




    @Override
    public void agregarElemento(T elemento) {
        if(this.estaVacio()) {
            this.raiz = new Nodo<>(elemento);
        } else {
            agregarElemento(raiz, elemento);
        }
    }

    private void agregarElemento(Nodo<T> nodo, T elemento) {
        if(nodo.izq == null) {
            nodo.izq = new Nodo<>(elemento);
        } else if (nodo.der == null) {
            nodo.der = new Nodo<>(elemento);
        } else {
            if(random.nextBoolean()) {
                agregarElemento(nodo.izq, elemento);
            } else {
                agregarElemento(nodo.der, elemento);
            }
        }
    }

    @Override
    public void imprimir() {
        if(this.estaVacio()) return;
        imprimir(raiz);
        /*
        if(!this.estaVacio()) {
            System.out.println("Raiz "+ this.raiz);
            if(raiz.izq != null) System.out.println("izq " + raiz.izq);
            if(raiz.der != null) System.out.println("der " + raiz.der);
            if(raiz.izq != null) System.out.println("izq " + raiz.izq);
            if(raiz.der != null) System.out.println("der " + raiz.der);
            if(raiz.izq != null) System.out.println("izq " + raiz.izq);
            if(raiz.der != null) System.out.println("der " + raiz.der);
        }
             */
    }

    private void imprimir(Nodo<T> nodo) {
        if(nodo == null) return;
        imprimir(nodo.izq);
        imprimir(nodo.der);
        System.out.println(nodo.elemento.toString());
    }

    @Override
    public boolean estaVacio() {
        return this.raiz==null;
    }

    @Override
    public void elimar(T elemento) {

    }


}
