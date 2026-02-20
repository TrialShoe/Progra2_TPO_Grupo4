package org.example.TDAs;

import org.example.Sistema;

public interface ICliente {

    boolean yaSigue(String nombreSeguido);
    String seguir(String nombreSeguido, Sistema sistema);
    String dejarDeSeguir(String nombreSeguido, Sistema sistema);
    String procesarSiguienteSolicitud(Sistema sistema);
    String procesarTodasSolicitudes(Sistema sistema);
    String verSolisPendientes();
}