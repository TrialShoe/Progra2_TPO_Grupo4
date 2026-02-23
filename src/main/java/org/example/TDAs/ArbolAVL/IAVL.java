package org.example.TDAs.ArbolAVL;

public interface IAVL<T extends Comparable<T>>{
    void agregar(T elemento);
    void imprimirPorNivel(int nivel);
    T maximo();
}
