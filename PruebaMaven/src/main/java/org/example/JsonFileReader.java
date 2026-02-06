package org.example;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {
    public static void main(String[] args) throws IOException {
        /*

        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader("datos.json");
            Sistema sist = gson.fromJson(reader, Sistema.class);

            if (sist == null || sist.clientes == null) {
                System.out.println("No se pudieron leer los clientes");
                return;
            }

            for (Cliente c : sist.clientes) {
                System.out.println("Nombre: " + c.nombre);
                System.out.println("Score: " + c.scoring);

                System.out.print("Siguiendo: ");
                if (c.siguiendo != null && !c.siguiendo.isEmpty()) {
                    for (int i = 0; i < c.siguiendo.size(); i++) {
                        System.out.print(c.siguiendo.get(i));
                        if (i < c.siguiendo.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                } else {
                    System.out.print("No sigue a nadie.");
                }
                System.out.println();

                System.out.print("Conexiones: ");
                if (c.conexiones != null && !c.conexiones.isEmpty()) {
                    for (int i = 0; i < c.conexiones.size(); i++) {
                        System.out.print(c.conexiones.get(i));
                        if (i < c.conexiones.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.print("No tiene ninguna conexión.");
                }
                System.out.println();
            }

        } catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado.");
        }

         */
    }
}
