package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static int leerEntero(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int v = sc.nextInt();
                sc.nextLine(); // consume el salto de línea pendiente
                return v;
            }
            sc.nextLine(); // descarta entrada inválida
            System.out.println("Entrada inválida. Ingrese un número.");
        }
    }

    private static String leerTextoNoVacio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.isBlank()) {
                return s;
            }
            System.out.println("Entrada inválida. Ingrese texto.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema();

        System.out.println("Cargando datos desde JSON...");
        System.out.println(sistema.cargarDesdeJson("datos.json"));
        System.out.println("----------------------------------------");

        while (true) {
            System.out.println("----------------------------------------");
            System.out.println("MENÚ\n");
            System.out.println("1. Ver clientes");
            System.out.println("2. Buscar cliente por nombre");
            System.out.println("3. Buscar cliente por scoring");
            System.out.println("4. Ver historial");
            System.out.println("5. Salir\n");

            int opcion = leerEntero(sc, "Ingrese una opcion: ");

            switch (opcion) {
                case 1:
                    System.out.println("----------------------------------------");
                    System.out.println(sistema.cantidadClientes());

                    System.out.println("----------------------------------------");
                    System.out.println("1. Agregar cliente");
                    System.out.println("2. Eliminar cliente");
                    System.out.println("3. Volver atras\n");

                    int subOpcion = leerEntero(sc, "Ingrese una opcion: ");

                    switch (subOpcion) {
                        case 1: {
                            System.out.println("----------------------------------------");
                            String nombreNuevo = leerTextoNoVacio(sc, "Ingrese Nombre del cliente: ");
                            int scoringNuevo = leerEntero(sc, "Ingrese Scoring: ");

                            Cliente c = new Cliente(nombreNuevo, scoringNuevo, new ArrayList<>(), new ArrayList<>());
                            System.out.println(sistema.agregarCliente(c));
                            break;
                        }
                        case 2: {
                            System.out.println("----------------------------------------");
                            String nombreEliminar = leerTextoNoVacio(sc, "Ingrese Nombre del cliente a eliminar: ");
                            System.out.println(sistema.eliminarCliente(nombreEliminar));
                            break;
                        }
                        case 3:
                            break;

                        default:
                            System.out.println("Opcion no valida");
                    }
                    break;

                case 2: {
                    System.out.println("----------------------------------------");
                    String nombreBuscar = leerTextoNoVacio(sc, "Ingrese el nombre del cliente: ");
                    System.out.println(sistema.buscarPorNombre(nombreBuscar));
                    break;
                }

                case 3: {
                    System.out.println("----------------------------------------");
                    int scoringBuscar = leerEntero(sc, "Ingrese el scoring del cliente: ");
                    System.out.println(sistema.buscarPorScoring(scoringBuscar));
                    break;
                }

                case 4:
                    System.out.println("----------------------------------------");
                    System.out.println("Historial: (aún no implementado)\n");
                    System.out.println("1. Deshacer última acción");
                    System.out.println("2. Volver atrás\n");

                    int opHist = leerEntero(sc, "Ingrese una opcion: ");
                    if (opHist == 1) {
                        System.out.println("----------------------------------------");
                        System.out.println("Deshacer: (aún no implementado)");
                    }
                    break;

                case 5:
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Opcion no valida");
            }
        }
    }
}