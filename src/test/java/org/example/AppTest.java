package org.example;

import junit.framework.TestCase;

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

    // ====== Tests de alta/búsqueda de clientes ======

    public void testAgregarCliente_AregarYBusqueda() {
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

    // ====== Tests de follow (basado en escenarios de TesteoApp) ======

    public void testSeguirYProcesarSiguienteSolicitud_AplicarFollowYRegistrarEnHistorial() {
        Cliente alice = new Cliente("Alice", 50);
        Cliente bob = new Cliente("Bob", 60);

        sistema.agregarCliente(alice);
        sistema.agregarCliente(bob);

        // Solicitud en cola (no aplica directamente)
        String rSoli = alice.seguir("bob", sistema);
        assertTrue(rSoli.startsWith("Solicitud de seguimiento en la cola"));
        assertEquals(0, alice.getSiguiendo().size());

        // Procesa y aplica
        String rProc = alice.procesarSiguienteSolicitud(sistema);
        assertTrue(rProc.contains("ahora sigue a"));
        assertEquals(1, alice.getSiguiendo().size());
        assertTrue(alice.getSiguiendo().contains("bob"));

        // Historial: última acción debería ser FOLLOW|alice|bob
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

        // Dos solicitudes FOLLOW pendientes (todavía no aplicadas)
        assertTrue(pedro.seguir("bob", sistema).startsWith("Solicitud de seguimiento"));
        assertTrue(pedro.seguir("charlie", sistema).startsWith("Solicitud de seguimiento"));

        // La tercera debe fallar por el límite (2 actuales + pendientes)
        String r3 = pedro.seguir("david", sistema);
        assertTrue(r3.startsWith("Error: Pedro alcanzó el máximo de 2 clientes seguidos."));
    }

    // ====== Tests de historial (deshacer) ======

    public void testDeshacerUltimaAccion_ADD_EliminarClienteAgregado() {
        Cliente c = new Cliente("Pedro", 88);

        sistema.agregarCliente(c);

        // Verifica que está
        assertTrue(sistema.buscarPorNombre("pedro").startsWith("Cliente encontrado"));

        // Deshace el ADD (se debería eliminar)
        String rUndo = sistema.deshacerUltimaAccion();
        assertTrue(rUndo.startsWith("Accion Deshacer") || rUndo.startsWith("Cliente eliminado"));

        // Ya no debería encontrarse
        String rFind2 = sistema.buscarPorNombre("pedro");
        assertTrue(rFind2.startsWith("Cliente no encontrado"));
    }

    public void testDeshacerUltimaAccion_FOLLOW_RevertirElSeguimiento() {
        Cliente alice = new Cliente("Alice", 50);
        Cliente bob = new Cliente("Bob", 60);

        sistema.agregarCliente(alice);
        sistema.agregarCliente(bob);

        alice.seguir("bob", sistema);
        alice.procesarSiguienteSolicitud(sistema);

        assertTrue(alice.getSiguiendo().contains("bob"));

        // Última acción: FOLLOW|alice|bob
        assertTrue(sistema.verUltimaAccion().contains("FOLLOW|alice|bob"));

        // Deshacer debería hacer UNFOLLOW directo sin cola (dejarDeSeguirSinHistorial)
        String rUndo = sistema.deshacerUltimaAccion();
        assertTrue(rUndo.contains("dejó de seguir") || rUndo.contains("Accion Deshacer"));

        assertFalse(alice.getSiguiendo().contains("bob"));
    }
}