package org.example;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sistema {
    // Diccionario para buscar por Nombre (clave normalizada).
    private final Map<String, Cliente> porNombre = new HashMap<>();

    // Diccionario para buscar por Scoring (guarda claves normalizadas de nombre).
    private final Map<Integer, List<String>> porScoring = new HashMap<>();

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

        // Guarda el índice por Scoring (también con clave normalizada).
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

            return "Carga completa. Cargados: " + cargados + ". Repetidos: " + repetidos + ".";
        } catch (FileNotFoundException e) {
            return "Archivo no encontrado.";
        } catch (Exception e) {
            return "Error al leer JSON: " + e.getMessage();
        }
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

    public static String normalizarNombre(String s) {
        if (s == null) return "";
        return s.trim().replaceAll("[^\\p{L}]", "").toLowerCase();
    }
}