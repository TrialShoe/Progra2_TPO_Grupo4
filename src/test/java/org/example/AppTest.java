package org.example;

import junit.framework.TestCase;
import org.example.TDAs.ArbolAVL.AVL;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest extends TestCase {

    private Sistema sistema;

    @Override
    protected void setUp() {
        sistema = new Sistema();
    }

    // ====== Tests de normalización ======

    public void testNormalizarNombre() {
        assertEquals("pedro", Sistema.normalizarNombre("  pEDRo  "));
        assertEquals("anamaría", Sistema.normalizarNombre("Ana María"));
        assertEquals("", Sistema.normalizarNombre(null));
        assertEquals("", Sistema.normalizarNombre("   "));
        assertEquals("bob", Sistema.normalizarNombre("b-o_b!"));
    }

    // ====== Tests de agregar y búsqueda de clientes ======

    public void testAgregarClienteYBusquedaNombreYScoring() {
        Cliente c = new Cliente("Pedro", 88);

        String rAdd = sistema.agregarCliente(c);
        assertTrue(rAdd.startsWith("Cliente agregado"));

        String rFind = sistema.buscarPorNombre("pEDRo");
        assertTrue(rFind.startsWith("Cliente encontrado"));
        assertTrue(rFind.contains("Nombre = Pedro"));
        assertTrue(rFind.contains("Scoring = 88"));

        String rFindScore = sistema.buscarPorScoring(88);
        assertTrue(rFindScore.contains("Scoring = 88"));
    }


    public void testAgregarCliente_RechazarDuplicados() {
        Cliente c1 = new Cliente("Bob", 10);
        Cliente c2 = new Cliente("  bOB  ", 99);

        assertTrue(sistema.agregarCliente(c1).startsWith("Cliente agregado"));

        String r2 = sistema.agregarCliente(c2);
        assertTrue(r2.startsWith("Error: ya existe un cliente con el nombre"));
    }

    // ====== Tests de follow ======

    public void testSeguirYProcesarSiguienteSolicitud_AplicarFollowYRegistrarEnHistorial() {
        Cliente alice = new Cliente("Alice", 50);
        Cliente bob = new Cliente("Bob", 60);

        sistema.agregarCliente(alice);
        sistema.agregarCliente(bob);

        // Solicitud en cola.
        String rSoli = alice.seguir("bob", sistema);
        assertTrue(rSoli.startsWith("Solicitud de seguimiento en la cola"));
        assertEquals(0, alice.getSiguiendo().size());

        // Procesa y aplica la solicitud.
        String rProc = alice.procesarSiguienteSolicitud(sistema);
        assertTrue(rProc.contains("ahora sigue a"));
        assertEquals(1, alice.getSiguiendo().size());
        assertTrue(alice.getSiguiendo().contains("bob"));

        // Última acción es: FOLLOW|alice|bob
        String ultima = sistema.verUltimaAccion();
        assertTrue(ultima.contains("FOLLOW|alice|bob"));
    }

    public void testSeguir_LimitarMaximoDosSeguidosContandoPendientes() {
        Cliente pedro = new Cliente("Pedro", 88);
        Cliente bob = new Cliente("Bob", 10);
        Cliente charlie = new Cliente("Charlie", 20);
        Cliente david = new Cliente("David", 30);

        sistema.agregarCliente(pedro);
        sistema.agregarCliente(bob);
        sistema.agregarCliente(charlie);
        sistema.agregarCliente(david);

        // Dos solicitudes FOLLOW pendientes.
        assertTrue(pedro.seguir("bob", sistema).startsWith("Solicitud de seguimiento"));
        assertTrue(pedro.seguir("charlie", sistema).startsWith("Solicitud de seguimiento"));

        // La tercera falla por el límite (2 actuales + pendientes)
        String r3 = pedro.seguir("david", sistema);
        assertTrue(r3.startsWith("Error: Pedro alcanzó el máximo de 2 clientes seguidos."));
    }

    // ====== Tests de historial (deshacer) ======

    public void testDeshacerUltimaAccion_ADD_EliminarClienteAgregado() {
        Cliente c = new Cliente("Pedro", 88);

        sistema.agregarCliente(c);

        // Verifica que exista el cliente.
        assertTrue(sistema.buscarPorNombre("pedro").startsWith("Cliente encontrado"));

        // Deshace el ADD.
        String rUndo = sistema.deshacerUltimaAccion();
        assertTrue(rUndo.startsWith("Accion Deshacer") || rUndo.startsWith("Cliente eliminado"));

        // No debería encontrarse el cliente una vez eliminado.
        String rFind2 = sistema.buscarPorNombre("pedro");
        assertTrue(rFind2.startsWith("Cliente no encontrado"));
    }

    public void testDeshacerUltimaAccion_FOLLOW_DejarDeSeguir() {
        Cliente alice = new Cliente("Alice", 50);
        Cliente bob = new Cliente("Bob", 60);

        sistema.agregarCliente(alice);
        sistema.agregarCliente(bob);

        alice.seguir("bob", sistema);
        alice.procesarSiguienteSolicitud(sistema);

        assertTrue(alice.getSiguiendo().contains("bob"));

        // Última acción: FOLLOW|alice|bob.
        assertTrue(sistema.verUltimaAccion().contains("FOLLOW|alice|bob"));

        // Deshacer: UNFOLLOW directo sin cola.
        String rUndo = sistema.deshacerUltimaAccion();
        assertTrue(rUndo.contains("dejó de seguir") || rUndo.contains("Accion Deshacer"));

        assertFalse(alice.getSiguiendo().contains("bob"));
    }


    public void testMostrarSeguidosYConexiones() {
        Cliente alice = new Cliente("Alice", 95,
                new java.util.ArrayList<>(java.util.List.of("Bob","Charlie")),
                new java.util.ArrayList<>(java.util.List.of("Bob","Charlie","David"))
        );

        Cliente bob = new Cliente("Bob", 88);
        Cliente charlie = new Cliente("Charlie", 80);
        Cliente david = new Cliente("David", 92);

        sistema.agregarCliente(alice);
        sistema.agregarCliente(bob);
        sistema.agregarCliente(charlie);
        sistema.agregarCliente(david);

        String seguidos = sistema.mostrarSeguidos("aLiCe");
        assertTrue(seguidos.startsWith("Seguidos de"));
        assertTrue(seguidos.contains("Bob"));
        assertTrue(seguidos.contains("Charlie"));

        String conexiones = sistema.mostrarConexiones("ALICE");
        assertTrue(conexiones.startsWith("Conexiones de"));
        assertTrue(conexiones.contains("Bob"));
        assertTrue(conexiones.contains("Charlie"));
        assertTrue(conexiones.contains("David"));
    }


    public void testAVL_ImprimirNivel() {
        AVL<RankCliente> avl = new AVL<>();

        // Insertamos 3 nodos: nivel 1 debe tener a y c.
        avl.agregar(new RankCliente("b", 2));
        avl.agregar(new RankCliente("a", 1));
        avl.agregar(new RankCliente("c", 3));

        // Capturamos System.out.
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            avl.imprimirPorNivel(1);
        } finally {
            System.setOut(original);
        }

        String printed = buffer.toString();

        // Valida que estén los dos hijos.
        assertTrue(printed.contains("a (seguidores=1)"));
        assertTrue(printed.contains("c (seguidores=3)"));

        // Valida que NO esté la raíz en ese nivel.
        assertFalse(printed.contains("b (seguidores=2)"));
    }
}