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

        // s.pruebaImprimirNiveles();

        System.out.println();
        s.imprimirNivel4();

        System.out.println();
        System.out.println(s.clienteConMasSeguidores());




        System.out.println("\n\n\n\n\n");
        System.out.println("~~~~~~~~~~ GRAFOS AMISTADES ~~~~~~~~~~ \n");

        Cliente obama = s.obtenerCliente("obama");
        Cliente messi = s.obtenerCliente("messi");
        Cliente bob = s.obtenerCliente("bob");
        Cliente alice = s.obtenerCliente("alice");
        Cliente frank = s.obtenerCliente("frank");
        Cliente profe = s.obtenerCliente("manuel adrian caceres");

        s.agregarAmistad(obama,messi);
        s.agregarAmistad(messi,bob);

        // Prueba para ver si añade aunque ya esté la Amistad.
        s.agregarAmistad(profe,alice);
        s.agregarAmistad(alice,profe);

        System.out.println("Vecinos de Obama: " + s.getVecinos("obama"));
        System.out.println("Vecinos de Messi: " + s.getVecinos("messi"));
        System.out.println();

        System.out.println("Amistades: \n" + s.getAmistades());
        System.out.println();

        System.out.println("Distancia Obama -> Frank: " + s.distancia(obama, frank));
        System.out.println("Distancia Frank -> Obama: " + s.distancia(frank, obama));
        System.out.println("Distancia Obama -> Obama: " + s.distancia(obama, obama));


        //System.out.println();
        //s.debugDistanciasDesde(obama);
    }
}