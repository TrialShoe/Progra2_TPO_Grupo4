package org.example.TDAs.Arbol;

public interface iArbolBinarioTDA<T> {
    void crearArbol();
    void agregarElemento(T elemento);
    void imprimir();
    boolean estaVacio();
    void elimar(T elemento);
    public T maximaHojas();
}