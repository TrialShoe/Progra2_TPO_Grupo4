package org.example;

public class Amistad {
    private final String clienteA;
    private final String clienteB;

    public Amistad(String a, String b) {
        this.clienteA = a;
        this.clienteB = b;
    }

    public String getClienteA() { return clienteA; }
    public String getClienteB() { return clienteB; }

    @Override
    public String toString() {
        return clienteA + " <-> " + clienteB;
    }
}