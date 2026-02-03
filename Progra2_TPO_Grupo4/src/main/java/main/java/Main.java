package main.java;

import TDAs.ListaIMP;
import TDAs.ListaTDA;

public class Main {
    public static void main(String[] args) {

        ListaTDA<Cliente> listaClientes = new ListaIMP<>(10);
        listaClientes.agregar(new Cliente("Mateo",80));
        listaClientes.agregar(new Cliente("Agustin",70));
        listaClientes.agregar(new Cliente("Facundo", 50));

        listaClientes.agregar(new Cliente("Pepe", 50));

        BusquedaDeCliente buscador = new BusquedaDeCliente(listaClientes);
        System.out.println(buscador.buscarPorNombre());
        System.out.println(buscador.buscarPorScoring());

        // GestorJson gestorJson = new GestorJson();
        // gestorJson.procesar();
    }
}