package org.example;

public class TesteoApp {
    public static void main(String[] args) {
        Sistema s = new Sistema();

        System.out.println(s.cargarDesdeJson("datos.json"));

        Cliente c1 = new Cliente("Pedro", 88);
        Cliente c2 = s.obtenerCliente("bob");

        System.out.println(s.buscarPorNombre("bob"));
        System.out.println(c2.seguir("charlie",s));
        c2.procesarSiguienteSolicitud(s);
        System.out.println(s.buscarPorNombre("bob"));

        s.agregarCliente(c1);
        c1.seguir("bob", s);
        c1.seguir("charlie",s);
        System.out.println(c1.seguir("david",s));
        c1.procesarTodasSolicitudes(s);

        System.out.println(s.verUltimaAccion());
        System.out.println(s.deshacerUltimaAccion());

        System.out.println(s.buscarPorNombre("pEDRo"));

        System.out.println(s.mostrarSeguidos("Alice"));
        System.out.println(s.mostrarSeguidos("pedro"));
        System.out.println(s.mostrarConexiones("david"));



        System.out.println();
        s.pruebaImprimirNiveles();

        System.out.println();
        s.imprimirNivel4();

        System.out.println();
        System.out.println(s.clienteConMasSeguidores());

    }
}