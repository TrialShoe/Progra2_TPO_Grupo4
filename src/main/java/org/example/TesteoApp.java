package org.example;

public class TesteoApp {
    public static void main(String[] args) {
        Sistema s = new Sistema();

        System.out.println(s.cargarDesdeJson("datos.json"));

        Cliente c1 = new Cliente("Pedro", 88);
        Cliente c2 = s.obtenerCliente("bob");

        System.out.println(s.buscarPorNombre("bob"));
        System.out.println(c2.seguir("charlie",s));

        s.agregarCliente(c1);
        c1.seguir("bob", s);
        c1.seguir("charlie",s);
        c1.procesarTodasSolicitudes(s);

        System.out.println(s.verUltimaAccion());
        System.out.println(s.deshacerUltimaAccion());

        System.out.println(s.buscarPorNombre("pEDRo"));

        System.out.println(s.mostrarSeguidos("Alice"));
        System.out.println(s.mostrarSeguidos("pedro"));
        System.out.println(s.mostrarConexiones("david"));

        // System.out.println();
        // s.pruebaImprimirNiveles();

        System.out.println();
        s.imprimirNivel4();

        System.out.println();
        System.out.println(s.clienteConMasSeguidores());




        /*

        System.out.println();
        System.out.println();

        Cliente obama = s.obtenerCliente("obama");
        Cliente messi = s.obtenerCliente("messi");
        Cliente bob = s.obtenerCliente("bob");
        Cliente alice = s.obtenerCliente("alice");

        s.agregarAmistad("obama","messi");
        s.agregarAmistad("bob","alice");
        s.agregarAmistad("messi","bob");

        System.out.println("Vecinos de Obama: " + s.getVecinos("obama"));
        System.out.println("Vecinos de Messi: " + s.getVecinos("messi"));
        System.out.println();

        System.out.println("Amistades: " + s.getAmistades());

        System.out.println("Distancia Obama -> Messi: " + s.distancia("obama", "messi"));
        System.out.println("Distancia Obama -> Bob: " + s.distancia("obama", "bob"));
        System.out.println("Distancia Obama -> Alice: " + s.distancia("obama", "alice"));
        System.out.println("Distancia Obama -> Obama: " + s.distancia("obama", "obama"));
        System.out.println("Distancia Obama -> X: " + s.distancia("obama", "x"));

         */

    }
}