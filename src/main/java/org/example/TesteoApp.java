package org.example;

public class TesteoApp {
    public static void main(String[] args) {
        Sistema s = new Sistema();

        Cliente c1 = new Cliente("Pedro", 88);

        System.out.println(s.cargarDesdeJson("datos.json"));

        System.out.println(s.agregarCliente(c1));

        System.out.println(c1.seguir("bob", s));

        System.out.println(c1.seguir("charlie",s));

        System.out.println(c1.verSolisPendientes());

        System.out.println(c1.procesarTodasSolicitudes(s));

        System.out.println(s.verUltimaAccion());

        System.out.println(s.deshacerUltimaAccion());

        System.out.println(s.buscarPorNombre("pedRO"));

    }
}