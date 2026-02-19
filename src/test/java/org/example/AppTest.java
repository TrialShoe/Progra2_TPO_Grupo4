package org.example;

import junit.framework.TestCase;

public class AppTest extends TestCase {

    public void todo() {
        Sistema s = new Sistema();

        Cliente c1 = new Cliente("Mateo",88);
        Cliente c2 = new Cliente("Pedro",99);

        System.out.println(s.cargarDesdeJson("datos.json"));

        System.out.println(s.mostrarClientes());

        System.out.println(s.agregarCliente(c1));

        System.out.println(s.agregarCliente(c2));

        System.out.println(s.mostrarClientes());

        System.out.println(s.eliminarCliente("Pedro"));

        System.out.println(s.mostrarClientes());

        System.out.println(s.buscarPorNombre("Bob"));

        System.out.println(s.buscarPorScoring(88));

        System.out.println(s.encolarSolicitud("Mateo","Bob"));

        System.out.println(s.verSolicitudesPendientes());

        System.out.println(s.procesarSiguienteSolicitud());

        System.out.println(s.buscarPorNombre("Mateo"));

        System.out.println(s.verUltimaAccion());

        System.out.println(s.deshacerUltimaAccion());

        System.out.println(s.buscarPorNombre("Mateo"));
    }
}
