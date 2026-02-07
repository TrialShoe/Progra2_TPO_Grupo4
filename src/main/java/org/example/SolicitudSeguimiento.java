package org.example;

public class SolicitudSeguimiento {
    private final String seguidor;
    private final String seguido;

    public SolicitudSeguimiento(String seguidor, String seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }

    public String getSeguidor() {
        return seguidor;
    }

    public String getSeguido() {
        return seguido;
    }
}
