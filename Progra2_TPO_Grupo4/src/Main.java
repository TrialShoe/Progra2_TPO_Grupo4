import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Cliente cliente1 = new Cliente("Mateo", 5);
        // System.out.println(cliente1.getNombre());

        ArrayList<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(new Cliente("Mateo",80));
        listaClientes.add(new Cliente("Agustin",70));
        listaClientes.add(new Cliente("Facundo", 50));

        BusquedaDeCliente buscador = new BusquedaDeCliente(listaClientes);
        Cliente encontrado = buscador.buscarPorNombre();

        if (encontrado != null) {
            System.out.print("Cliente encontrado: ");
            System.out.print(encontrado.getNombre() + ", Scoring: " + encontrado.getScoring());
        } else {
            System.out.println("Cliente no encontrado");
        }
    }
}