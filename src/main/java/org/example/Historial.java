package org.example;

import org.example.TDAs.ImpPila;
import org.example.TDAs.TADPila;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Historial {
    // Agregamos la Implementación de Pila, para el Historial de Acciones.
    public final TADPila PilaHistorial = new ImpPila();

    private final Sistema s;

    public Historial(Sistema s) {
        this.s = s;
    }


    // Convertir Lista a String (Serializar).
    public String listaAString(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "-";
        }
        return String.join(",", lista);
    }

    // Convertir String a Lista.
    public List<String> stringALista(String s) {
        if (s == null || s.equals("-") || s.isBlank()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }

    // Para ver la última acción de la Pila (Historial de acciones).
    public String verUltimaAccion() {
        return "Última acción: " + PilaHistorial.cima();
    }



    // Deshacer última acción.
    public String deshacerUltimaAccion() {
        if (PilaHistorial.estaVacia()) {
            return "No hay acciones para deshacer.";
        }

        String accion = PilaHistorial.desapilar();              // Pila = LIFO, Desapilamos la última acción.
        String[] partes = accion.split("\\|", -1);
        String tipo = partes[0];


        switch (tipo) {
            case "ADD": {
                // Deshacer esto, es eliminar al cliente.
                String nombreKey = partes[1];

                // Eliminamos sin volver a registrar historial.
                return s.eliminarClienteSinHistorial(nombreKey);
            }

            case "DEL": {
                // Deshacer esto, es añadir al cliente.
                String nombre = partes[1];
                int scoring = Integer.parseInt(partes[2]);
                List<String> siguiendo = stringALista(partes[3]);
                List<String> conexiones = stringALista(partes[4]);

                Cliente c = new Cliente(nombre, scoring, siguiendo, conexiones);
                return s.agregarClienteSinHistorial(c);
            }

            case "FOLLOW": {
                // Deshacer esto, es dejar de seguir al cliente.
                String seguidor = partes[1];
                String seguido = partes[2];
                return s.dejarDeSeguirSinHistorial(seguidor, seguido);
            }

            case "UNFOLLOW": {
                // Deshacer esto, es seguir al cliente.
                String seguidor = partes[1];
                String seguido = partes[2];
                return s.seguirSinHistorial(seguidor, seguido);
            }

            default:
                return "Error: Acción desconocida: " + accion;
        }
    }
}
