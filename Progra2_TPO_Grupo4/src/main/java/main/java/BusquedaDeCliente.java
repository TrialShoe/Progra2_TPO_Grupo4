package main.java;

import TDAs.ListaIMP;
import TDAs.ListaTDA;

import java.util.Scanner;

public class BusquedaDeCliente {

    private ListaTDA<Cliente> clientes;

    // Constructor
    public BusquedaDeCliente(ListaTDA<Cliente> clientes) {
        this.clientes = clientes;
    }


    public String buscarPorNombre() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del Cliente que desea buscar: ");
        String nombreInsertado = sc.nextLine();


        for (int i = 0; i < clientes.largo(); i++) {
            Cliente cliente = clientes.obtener(i);

            if (cliente.getNombre().equalsIgnoreCase(nombreInsertado)) {
                return "Cliente encontrado. Nombre: "
                        + cliente.getNombre()
                        + ", Scoring: "
                        + cliente.getScoring();
            }
        }

        return "Cliente no encontrado.";
    }


    public String buscarPorScoring() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el Scoring que desea buscar: ");
        int scoringInsertado = sc.nextInt();

        ListaTDA<Cliente> encontrados = new ListaIMP<>(clientes.largo());

        for (int i = 0; i < clientes.largo(); i++) {
            Cliente cliente = clientes.obtener(i);

            if (cliente.getScoring() == scoringInsertado) {
                encontrados.agregar(cliente);
            }
        }

        if (encontrados.largo() == 0) {
            return "No se encontró ningún cliente con el Scoring: " + scoringInsertado;
        }

        String resultado = "Clientes encontrados con el Scoring " + scoringInsertado + ":\n";
        for (int i = 0; i < encontrados.largo(); i++) {
            Cliente c = encontrados.obtener(i);
            resultado += "- " + c.getNombre() + "\n";
        }
        return resultado;
    }
}
