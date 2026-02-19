package org.example;

import org.example.TDAs.ICliente;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cliente implements ICliente {
    String nombre;
    int scoring;
    public List<String> siguiendo;
    public List<String> conexiones;

    // Constructor para agregar datos A MANO.
    public Cliente(String nombre, int scoring) {
        this.nombre = nombre;
        this.scoring = scoring;
        this.siguiendo = new ArrayList<>();
        this.conexiones = new ArrayList<>();
    }

    // Constructor para leer datos del JSON.
    public Cliente(String nombre, int scoring, List<String> siguiendo, List<String> conexiones) {
        this.nombre = nombre;
        this.scoring = scoring;
        this.siguiendo = (siguiendo != null) ? siguiendo : new ArrayList<>();
        this.conexiones = (conexiones != null) ? conexiones : new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getScoring() {
        return scoring;
    }

    public List<String> getSiguiendo() {
        return siguiendo;
    }

    public List<String> getConexiones() {
        return conexiones;
    }

    private final Queue<String> colaSolicitudes = new LinkedList<>();

    public boolean yaSigue(String nombreSeguido) {
        return siguiendo.contains(nombreSeguido);
    }

    public String seguir(String nombreSeguido, Sistema sistema) {
        String seguidoN = Sistema.normalizarNombre(nombreSeguido);

        if (seguidoN.isBlank()) return "Error: Ingrese un nombre válido.";

        if (!sistema.existeClienteKey(seguidoN)) return "Error: El cliente que trata de seguir no existe.";

        if (yaSigue(seguidoN)) return "Error: " + nombre + " ya sigue a " + seguidoN;

        colaSolicitudes.add("FOLLOW|" + seguidoN);

        return "Solicitud de seguimiento en la cola. Solis pendientes: " + colaSolicitudes.size() + "\n";
    }

    public String dejarDeSeguir(String nombreSeguido, Sistema sistema) {
        String seguidoN = Sistema.normalizarNombre(nombreSeguido);

        if (seguidoN.isBlank()) return "Error: Ingrese un nombre válido.";

        if (!sistema.existeClienteKey(seguidoN)) return "Error: El cliente que trata de seguir no existe.";

        if (!yaSigue(seguidoN)) return "Error: El cliente no sigue a " + seguidoN;

        colaSolicitudes.add("UNFOLLOW|" + seguidoN);

        return "Solicitud de dejar de seguir en la cola. Solis pendientes: " + colaSolicitudes.size();
    }

    public String procesarSiguienteSolicitud(Sistema sistema) {
        if (colaSolicitudes.isEmpty()) return "Error: No hay solicitudes pendientes.";

        String soli = colaSolicitudes.poll();
        String[] p = soli.split("\\|", -1);
        String tipo = p[0].trim();
        String cliente = p[1].trim();

        Cliente CObjetivo = sistema.getClienteKey(cliente);

        if (CObjetivo == null) return "Error: el cliente ya no existe. Solis pendientes: " + colaSolicitudes.size();

        // Aplicamos Follow
        if (tipo.equals("FOLLOW")) {
            if (yaSigue(cliente)) return "Error: el cliente ya lo seguía. Solis pendientes: " + colaSolicitudes.size();

            // Se aplica el Follow
            seguirDirecto(cliente);
            sistema.registrarAccion("FOLLOW|" + Sistema.normalizarNombre(this.nombre) + "|" + cliente);

            return this.nombre + " ahora sigue a " + CObjetivo.getNombre() +
                    ". Solis pendientes: " + colaSolicitudes.size() + "\n";
        }

        // APLICAR UNFOLLOW
        if (tipo.equals("UNFOLLOW")) {
            if (!yaSigue(cliente)) {
                return "Solicitud no aplicada: no lo seguías. Pendientes: " + colaSolicitudes.size();
            }

            dejarDeSeguirDirecto(cliente);
            sistema.registrarAccion("UNFOLLOW|" + Sistema.normalizarNombre(this.nombre) + "|" + cliente);

            return this.nombre + " dejó de seguir a " + CObjetivo.getNombre() +
                    ". Solis pendientes: " + colaSolicitudes.size() + "\n";
        }

        return "Error: Solicitud inválida, " + soli;
    }

    public String procesarTodasSolicitudes(Sistema sistema) {
        if (colaSolicitudes.isEmpty()) return "No hay solicitudes pendientes.";

        int total = colaSolicitudes.size();
        int procesadas = 0;

        for (int i = 0; i < total; i++) {
            procesarSiguienteSolicitud(sistema);
            procesadas++;
        }

        return "Solicitudes procesadas: " + procesadas + "\n";
    }

    public String verSolisPendientes() {
        if (colaSolicitudes.isEmpty()) return "No hay solicitudes pendientes.";
        return "Solicitudes pendientes de " + nombre + ": " + colaSolicitudes + "\n";
    }



    private void seguirDirecto(String cliente) {
        siguiendo.add(cliente);
    }

    private boolean dejarDeSeguirDirecto(String cliente) {
        return siguiendo.remove(cliente);
    }


    // Para el historial
    void seguirDirectoSinCola(String objetivoKey) {
        if (!siguiendo.contains(objetivoKey)) {
            siguiendo.add(objetivoKey);
        }
    }

    // Para el historial
    boolean dejarDeSeguirDirectoSinCola(String objetivoKey) {
        return siguiendo.remove(objetivoKey);
    }



    @Override
    public String toString() {
        return "Nombre = " + nombre.trim() + "\n"
                + "Scoring = " + scoring + "\n"
                + "Siguiendo = " + siguiendo + "\n"
                + "Conexiones = " + conexiones + "\n";
    }

}