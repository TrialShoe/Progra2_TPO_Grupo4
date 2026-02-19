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

    private final Historial historial;

    public Sistema() {
        this.historial = new Historial(this);
    }

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
                    .append(c).append("\n");
        }
        return muestraTexto.toString();
    }

    public boolean existeClienteKey(String key) {
        return porNombre.containsKey(key);
    }

    public Cliente getClienteKey(String key) {
        return porNombre.get(key);
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

        // Agregarmos la acción al historial
        historial.PilaHistorial.apilar("ADD|" + key);

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

        // Agregamos el cliente eliminado al historial.
        historial.PilaHistorial.apilar("DEL|" + eliminado.getNombre() + "|" + scoring + "|" + historial.listaAString(eliminado.getSiguiendo()) + "|" + historial.listaAString(eliminado.getConexiones()));


        if (lista != null) {
            lista.remove(key);
            if (lista.isEmpty()) {
                porScoring.remove(scoring);
            }
        }

        return "Cliente eliminado: " + eliminado.getNombre() + ", Scoring: " + scoring;
    }


    // Metodo para volver a agregar el cliente que fue eliminado. Al deshacer la acción.
    public String agregarClienteSinHistorial(Cliente c) {
        if (c == null) {
            return "Error: Agregue un cliente.";
        }

        String key = Sistema.normalizarNombre(c.getNombre());
        if (key.isBlank()) {
            return "Error: Nombre vacío.";
        }

        if (porNombre.containsKey(key)) {
            return "Error: Ya existe el cliente " + c.getNombre() + ".";
        }

        porNombre.put(key, c);

        int scoring = c.getScoring();
        porScoring.putIfAbsent(scoring, new ArrayList<>());
        porScoring.get(scoring).add(key);

        return "Accion Deshacer: se volvió a agregar el cliente " + c.getNombre() + ".";
    }



    // Metodo para eliminar el cliente que fue agregado. Al deshacer la acción.
    public String eliminarClienteSinHistorial(String nombre) {
        Cliente eliminado = porNombre.remove(nombre);

        if (eliminado == null) {
            return "Error: El cliente no existe para deshacer esta acción.";
        }

        int scoring = eliminado.getScoring();
        List<String> lista = porScoring.get(scoring);

        if (lista != null) {
            lista.remove(nombre);
            if (lista.isEmpty()) {
                porScoring.remove(scoring);
            }
        }

        return "Accion Deshacer: se eliminó el cliente " + eliminado.getNombre() + ".";
    }


    // Metodo para seguir al cliente que dejó de seguir. Al deshacer la acción.
    public String seguirSinHistorial(String seguidor, String seguido) {
        Cliente cSeguidor = getClienteKey(seguidor);
        Cliente cSeguido = getClienteKey(seguido);

        if (cSeguidor == null || cSeguido == null) {
            return "Error: No existen clientes para deshacer.";
        }

        if (cSeguidor.yaSigue(seguido)) {
            return "Error al deshacer: " + seguidor + " ya seguía a " + seguido + ".";
        }

        cSeguidor.seguirDirectoSinCola(seguido);
        return "Accion Deshacer: " + cSeguidor.getNombre() + " volvió a seguir a " + cSeguido.getNombre() + ".";
    }


    // Metodo para dejar de seguir al cliente que seguía. Al deshacer la acción.
    public String dejarDeSeguirSinHistorial(String seguidor, String seguido) {
        Cliente cSeguidor = getClienteKey(seguidor);
        Cliente cSeguido = getClienteKey(seguido);

        if (cSeguidor == null || cSeguido == null) {
            return "Error: No existen clientes para deshacer.";
        }

        boolean ok = cSeguidor.dejarDeSeguirDirectoSinCola(seguido);
        if (!ok) {
            return "Deshacer: no se aplicó (no lo seguía).";
        }

        return "Accion Deshacer: " + cSeguidor.getNombre() + " dejó de seguir a " + cSeguido.getNombre() + ".";
    }


    public void registrarAccion(String accion) {
        historial.PilaHistorial.apilar(accion);
    }






    public static String normalizarNombre(String s) {
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




    public String verUltimaAccion() {
        return historial.verUltimaAccion();
    }

    public String deshacerUltimaAccion() {
        return historial.deshacerUltimaAccion();
    }

}