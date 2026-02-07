package org.example;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Sistema {
    // Diccionario para buscar por Nombre (clave normalizada).
    private final Map<String, Cliente> porNombre = new HashMap<>();

    // Diccionario para buscar por Scoring (guarda claves normalizadas de nombre).
    private final Map<Integer, List<String>> porScoring = new HashMap<>();

    private static class DatosJson {
        public List<Cliente> clientes;
    }

    public String cargarDesdeJson(String ruta) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(ruta)) {
            DatosJson datos = gson.fromJson(reader, DatosJson.class);

            if (datos == null || datos.clientes == null) {
                return "Error: No se pudieron leer los Clientes.";
            }

            int cargados = 0;
            int repetidos = 0;

            for (Cliente c : datos.clientes) {
                if (c.getSiguiendo() == null) {
                    c.siguiendo = new ArrayList<>();
                }
                if (c.getConexiones() == null) {
                    c.conexiones = new ArrayList<>();
                }

                String r = agregarCliente(c);
                if (r.startsWith("Cliente agregado")) cargados++;
                else repetidos++;
            }

            return "Carga completa. Cargados: " + cargados + ".";

        } catch (FileNotFoundException e) {
            return "Archivo no encontrado.";
        } catch (Exception e) {
            return "Error al leer JSON: " + e.getMessage();
        }
    }


    public String mostrarClientes(){
        if (porNombre.isEmpty()) {
            return "No hay clientes cargados en el archivo JSON.";
        }

        StringBuilder muestraTexto = new StringBuilder();
        int i = 0;

        for (Cliente c : porNombre.values()) {
            i++;
            muestraTexto.append("CLIENTE ").append(i).append("\n")
                    .append(c).append("\n\n");
        }
        return muestraTexto.toString();
    }


    public boolean existeCliente(String nombre) {
        String key = normalizarNombre(nombre);
        return porNombre.containsKey(key);
    }


    public String agregarCliente(Cliente c) {
        if (c == null) {
            return "Error: Debe ingresar un Cliente válido.";
        }

        String key = normalizarNombre(c.getNombre());

        if (key.isBlank()) {
            return "Error: El nombre del cliente no puede estar vacío.";
        }

        if (porNombre.containsKey(key)) {
            return "Error: ya existe un cliente con el nombre " + c.getNombre();
        }

        // Guarda el índice por Nombre (clave normalizada).
        porNombre.put(key, c);

        // Guarda el índice por Scoring (clave normalizada).
        int scoring = c.getScoring();
        porScoring.putIfAbsent(scoring, new ArrayList<>());
        porScoring.get(scoring).add(key);

        return "Cliente agregado:\nNombre: " + c.getNombre() + "\nScoring: " + scoring + "\n";
    }


    public String buscarPorNombre(String nombre) {
        String key = normalizarNombre(nombre);
        if (key.isBlank()) {
            return "Nombre de cliente no válido, por favor ingrese uno nuevamente.";
        }

        Cliente c = porNombre.get(key);
        if (c != null) {
            return "Cliente encontrado:\n" + c;
        }
        return "Cliente no encontrado: " + nombre;
    }


    public String buscarPorScoring(int scoring) {
        List<String> keys = porScoring.get(scoring);

        if (keys == null || keys.isEmpty()) {
            return "No se encontraron clientes con el scoring " + scoring;
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("Clientes con scoring ").append(scoring).append(":\n");

        int contador = 0;
        for (String key : keys) {
            Cliente c = porNombre.get(key);
            if (c != null) {
                contador++;
                resultado.append("CLIENTE ").append(contador).append("\n")
                        .append(c).append("\n");
            }
        }
        return resultado.toString();
    }


    public String cantidadClientes() {
        return "La cantidad de clientes es: " + porNombre.size() + "\n";
    }


    public String eliminarCliente(String nombre) {
        String key = normalizarNombre(nombre);
        if (key.isBlank()) {
            return "Nombre no válido.";
        }

        Cliente eliminado = porNombre.remove(key);
        if (eliminado == null) {
            return "Cliente no encontrado para eliminar.";
        }

        int scoring = eliminado.getScoring();
        List<String> lista = porScoring.get(scoring);

        if (lista != null) {
            lista.remove(key);
            if (lista.isEmpty()) {
                porScoring.remove(scoring);
            }
        }

        return "Cliente eliminado: " + eliminado.getNombre() + ", Scoring: " + scoring;
    }



    private final Queue<SolicitudSeguimiento> colaSolicitudes = new LinkedList<>();

    public String encolarSolicitud(String seguidor, String seguido) {

        String seguidorN = normalizarNombre(seguidor);
        String seguidoN = normalizarNombre(seguido);

        if (seguidorN.isBlank() || seguidoN.isBlank()) {
            return "Error: El nombre no puede estar vacío.";
        }

        if (seguidorN.equals(seguidoN)) {
            return "Error: Un cliente no puede seguirse a sí mismo.";
        }

        if (!porNombre.containsKey(seguidorN)) {
            return "Error: No existe el seguidor, " + seguidor + ".";
        }
        if (!porNombre.containsKey(seguidoN)) {
            return "Error: No existe el cliente a seguir, " + seguido + ".";
        }

        Cliente cSeguidor = porNombre.get(seguidorN);
        Cliente cSeguido = porNombre.get(seguidoN);

        if (cSeguidor.yaSigue(seguidoN)) {
            return "Error: " + cSeguidor.getNombre() + " ya sigue a " + seguidoN + ".";
        }

        colaSolicitudes.add(new SolicitudSeguimiento(seguidorN, seguidoN));
        return "Solicitud de seguimiento de " + cSeguidor.getNombre() + " a " + cSeguido.getNombre()
                + ". Solicitudes Pendientes: " + colaSolicitudes.size();
    }



    public String procesarSiguienteSolicitud() {

        if (colaSolicitudes.isEmpty()) {
            return "No hay solicitudes pendientes.";
        }

        SolicitudSeguimiento sol = colaSolicitudes.poll(); // Saca la primera solicitud de la Cola.
        String seguidor = sol.getSeguidor();
        String seguido = sol.getSeguido();

        // Puede pasar que entre acolar y procesar la solicitud alguien haya sido eliminado.
        Cliente cSeguidor = porNombre.get(seguidor);
        Cliente cSeguido = porNombre.get(seguido);

        if (cSeguidor == null || cSeguido == null) {
            return "Error, Solicitud descartada: uno de los clientes ya no existe. Solicitudes Pendientes: " + colaSolicitudes.size();
        }

        if (cSeguidor.yaSigue(seguido)) {
            return "Error, Solicitud no aplicada ya que " + cSeguidor.getNombre() + " ya sigue a " + cSeguido.getNombre()
                    + ". Solicitudes Pendientes: " + colaSolicitudes.size();
        }

        // Cliente sigue al seguido.
        cSeguidor.seguir(seguido);

        return "Solicitud procesada: " + cSeguidor.getNombre() + " ahora sigue a " + cSeguido.getNombre()
                + ". Solicitudes Pendientes: " + colaSolicitudes.size();
    }


    public String procesarTodasSolicitudes() {

        if (colaSolicitudes.isEmpty()) {
            return "No hay solicitudes pendientes.";
        }

        int procesadas = 0;
        int aplicadas = 0;

        int total = colaSolicitudes.size();

        for (int i = 0; i < total; i++) {
            String revisar = procesarSiguienteSolicitud();
            procesadas++;

            if (revisar.contains("Solicitud procesada")) {
                aplicadas++;
            }
        }

        return "Solicitudes Procesadas: " + procesadas + ". Aplicadas: " + aplicadas + ". Pendientes: " + colaSolicitudes.size();
    }


    public String verSolicitudesPendientes() {

        if (colaSolicitudes.isEmpty()) {
            return "No hay solicitudes pendientes.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Solicitudes pendientes: ").append(colaSolicitudes.size()).append(":\n");

        int i = 0;

        for (SolicitudSeguimiento sol : colaSolicitudes) {
            i++;

            Cliente seguidor = porNombre.get(sol.getSeguidor());
            Cliente seguido = porNombre.get(sol.getSeguido());

            String a = (seguidor != null) ? seguidor.getNombre() : sol.getSeguidor();
            String b = (seguido != null) ? seguido.getNombre() : sol.getSeguido();

            sb.append(i).append(") ").append(a).append(" -> ").append(b).append("\n");
        }

        return sb.toString();
    }



    private String normalizarNombre(String s) {
        if (s == null) {
            return "";
        }

        return s.trim().replaceAll("[^\\p{L}]", "").toLowerCase();
    }

    public int leerEntero(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            if (sc.hasNextInt()) {
                int v = sc.nextInt();
                sc.nextLine();          // Saca el salto de línea pendiente.
                return v;
            }

            sc.nextLine();              // Descarta cualquiér entrada inválida.
            System.out.println("Entrada inválida. Ingrese un número.");
        }
    }

    public String leerTextoNoVacio(Scanner sc, String texto) {
        while (true) {
            System.out.print(texto);
            String s = sc.nextLine();

            if (s != null && !s.isBlank()) {
                return s;
            }

            System.out.println("Entrada inválida. Ingrese texto.");
        }
    }
}