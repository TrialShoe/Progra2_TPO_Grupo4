package org.example;

import org.example.TDAs.Arbol.ABB;

public class TesteoApp {
    public static void main(String[] args) {
        Sistema s = new Sistema();

        Cliente c1 = new Cliente("Pedro", 88);

        System.out.println(s.cargarDesdeJson("datos.json"));

        s.agregarCliente(c1);

        s.mostrarClientes();



        c1.seguir("bob", s);

        c1.seguir("charlie",s);

        System.out.println(c1.seguir("david",s));

        c1.procesarTodasSolicitudes(s);

        System.out.println(s.verUltimaAccion());

        System.out.println(s.deshacerUltimaAccion());

        System.out.println(s.buscarPorNombre("pEDRo"));

        System.out.println(s.mostrarSeguidos("Alice"));

        System.out.println(s.mostrarConexiones("david"));


        System.out.println();
        s.pruebaImprimirNiveles();



        System.out.println();

        System.out.println(s.clienteConMasSeguidores());



    }
}