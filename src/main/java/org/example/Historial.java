package org.example;

import org.example.TDAs.ImpPila;

public class Historial {
    static ImpPila pila = new ImpPila();

    public static void siguio(String primerNombre, String segundoNombre) {
        pila.apilar(primerNombre + " siguió a " + segundoNombre);
    }

    public static void dejoSeguir(String primerNombre, String segundoNombre) {
        pila.apilar(primerNombre + " siguió a " + segundoNombre);
    }
}
