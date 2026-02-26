package org.example;

public class Amistad {
    private final String clienteA;
    private final String clienteB;

    public Amistad(String clienteA, String clienteB) {
        this.clienteA = clienteA;
        this.clienteB = clienteB;
    }

    public String getClienteA() { return clienteA; }
    public String getClienteB() { return clienteB; }

    @Override
    public String toString() {
        return clienteA + " <-> " + clienteB + "\n";
    }
}