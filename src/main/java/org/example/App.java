package org.example;

public class App {
    public static void main( String[] args ) {

        Sistema s = new Sistema();

        System.out.println(s.cargarDesdeJson("datos.json"));

        System.out.println();

        System.out.println(s.agregarCliente(new Cliente("  Pepe  ",50)));

        System.out.println(s.buscarPorNombre("  B +   !!23123  o b"));

        //System.out.println(s.eliminarCliente("Bob"));

        //System.out.println(s.buscarPorNombre(" ALICE  "));

        //System.out.println(s.cantidadClientes());

        //System.out.println(s.buscarPorScoring(80));

    }
}
