package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema();

        System.out.println("Cargando datos desde JSON...");
        System.out.println("----------------------------------------");
        System.out.println(sistema.cargarDesdeJson("datos.json"));


        while (true) {
            System.out.println("----------------------------------------");
            System.out.println("MENÚ\n");
            System.out.println("1. Ver clientes");
            System.out.println("2. Buscar cliente por nombre");
            System.out.println("3. Buscar cliente por scoring");
            System.out.println("4. Gestión de Solicitudes de Seguimiento");
            System.out.println("5. Ver historial");
            System.out.println("6. Salir\n");

            int opcion = sistema.leerEntero(sc, "Ingrese una opcion: ");

            switch (opcion) {
                case 1: {
                    System.out.println("----------------------------------------");
                    System.out.println(sistema.mostrarClientes());

                    System.out.println("----------------------------------------");
                    System.out.println("1. Agregar cliente");
                    System.out.println("2. Eliminar cliente");
                    System.out.println("3. Volver atras\n");

                    int subOpcion = sistema.leerEntero(sc, "Ingrese una opcion: ");

                    switch (subOpcion) {
                        case 1: {
                            System.out.println("----------------------------------------");
                            String nombreNuevo = sistema.leerTextoNoVacio(sc, "Ingrese Nombre del cliente: ");
                            int scoringNuevo = sistema.leerEntero(sc, "Ingrese Scoring: ");

                            Cliente c = new Cliente(nombreNuevo, scoringNuevo, new ArrayList<>(), new ArrayList<>());
                            System.out.println(sistema.agregarCliente(c));
                            break;
                        }
                        case 2: {
                            System.out.println("----------------------------------------");
                            String nombreEliminar = sistema.leerTextoNoVacio(sc, "Ingrese Nombre del cliente a eliminar: ");
                            System.out.println(sistema.eliminarCliente(nombreEliminar));
                            break;
                        }
                        case 3:
                            break;

                        default:
                            System.out.println("Opcion no valida");
                    }
                    break;
                }

                case 2: {
                    System.out.println("----------------------------------------");
                    String nombreBuscar = sistema.leerTextoNoVacio(sc, "Ingrese el nombre del cliente: ");
                    System.out.println(sistema.buscarPorNombre(nombreBuscar));
                    break;
                }

                case 3: {
                    System.out.println("----------------------------------------");
                    int scoringBuscar = sistema.leerEntero(sc, "Ingrese el scoring del cliente: ");
                    System.out.println(sistema.buscarPorScoring(scoringBuscar));
                    break;
                }

                case 4: {
                    System.out.println("----------------------------------------");
                    System.out.println("1. Enviar solicitud de seguimiento");
                    System.out.println("2. Procesar siguiente solicitud de la cola");
                    System.out.println("3. Procesar todas las solicitudes");
                    System.out.println("4. Ver solicitudes pendientes");
                    System.out.println("5. Volver atrás\n");

                    int subOpcion2 = sistema.leerEntero(sc, "Ingrese una opcion: ");

                    switch (subOpcion2) {
                        case 1: {
                            System.out.println("----------------------------------------");
                            String seguidor = sistema.leerTextoNoVacio(sc, "¿Quién envía la solicitud?: ");
                            String seguido = sistema.leerTextoNoVacio(sc, "¿A quién quiere seguir?: ");
                            System.out.println(sistema.encolarSolicitud(seguidor, seguido));
                            break;
                        }

                        case 2: {
                            System.out.println("----------------------------------------");
                            System.out.println(sistema.procesarSiguienteSolicitud());
                            break;
                        }

                        case 3: {
                            System.out.println("----------------------------------------");
                            System.out.println(sistema.procesarTodasSolicitudes());
                            break;
                        }

                        case 4: {
                            System.out.println("----------------------------------------");
                            System.out.println(sistema.verSolicitudesPendientes());
                            break;
                        }

                        case 5: {
                            break;
                        }

                        default: {
                            System.out.println("Opción no válida.");
                            break;
                        }
                    }
                    break;
                }

                case 5: {
                    System.out.println("----------------------------------------");
                    System.out.println("Historial: (aún no implementado)\n");
                    System.out.println("1. Deshacer última acción");
                    System.out.println("2. Volver atrás\n");

                    int opHist = sistema.leerEntero(sc, "Ingrese una opcion: ");
                    if (opHist == 1) {
                        System.out.println("----------------------------------------");
                        System.out.println("Deshacer: (aún no implementado)");
                    }
                    break;
                }

                case 6: {
                    sc.close();
                    System.exit(0);
                }

                default: {
                    System.out.println("Opcion no valida");
                }
            }
        }
    }
}