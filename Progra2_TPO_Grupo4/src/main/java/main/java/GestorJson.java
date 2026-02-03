package main.java;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class GestorJson {
    public void procesar(){
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("datos.json");
            Sistema sistema = gson.fromJson(reader, Sistema.class);
            System.out.println(sistema);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }

    }
}
