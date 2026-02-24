package org.example.TDAs.ArbolAVL;

// Interfaz del Arbol Binario de Búsqueda Balanceado.
public interface IAVL<T extends Comparable<T>>{
    void agregar(T elemento);
    void imprimirPorNivel(int nivel);
    T maximo();
}
