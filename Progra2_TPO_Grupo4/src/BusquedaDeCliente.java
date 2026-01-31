import java.util.ArrayList;
import java.util.Scanner;

public class BusquedaDeCliente {
    private ArrayList<Cliente> clientes;

    public BusquedaDeCliente(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String buscarPorNombre() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del Cliente que desea buscar: ");
        String nombreInsertado = sc.nextLine();

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombreInsertado)) {
                return "Cliente encontrado. Nombre: "
                        + cliente.getNombre() +
                        ", Scoring: "
                        + cliente.getScoring();
            }
        }
        return "Cliente no encontrado.";
    }


    public String buscarPorScoring() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el Scoring que desea buscar: ");
        int scoringInsertado = sc.nextInt();

        ArrayList<Cliente> encontrados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            if (cliente.getScoring() == scoringInsertado) {
                encontrados.add(cliente);
            }
        }

        if (encontrados.isEmpty()) {
            return "No se encontró ningún cliente con el Scoring: " + scoringInsertado;
        }

        return "Clientes encontrados con el Scoring " + scoringInsertado + ": " + "\n" + encontrados;
    }
}
