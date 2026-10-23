package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class BatallonTest {

    private Batallon batallon;
    private TransporteTropas transporte;
    private Blindado blindado;

    @BeforeEach
    void creacionObjetos() {
        batallon = new Batallon("BAT-01", "BAMUR", "octava brigada", "Armenia", 8);
        transporte = new TransporteTropas("T-100", "Camión M35", 2019, 25000.0, EstadoOperativo.DISPONIBLE, 30);
        blindado = new Blindado("B-200", "BTR-80", 2020, 15000.0, EstadoOperativo.DISPONIBLE, 5);
    }

    // Caso 2: Agregar un vehículo militar correctamente
    @Test
    void testAgregarVehiculoCorrectamente() {
        boolean registrado = batallon.registrarVehiculo(transporte);
        assertTrue(registrado);
        assertEquals(1, batallon.getListaVehiculos().size());
        assertNotNull(batallon.buscarVehiculo("T-100"));
    }

    // Caso 3: No permitir registrar vehículos con ID repetido
    @Test
    void testNoPermitirRegistrarVehiculoIdRepetido() {
        assertTrue(batallon.registrarVehiculo(transporte));

        TransporteTropas duplicado = new TransporteTropas("T-100", "Camion Nuevo", 2022, 1000.0, EstadoOperativo.DISPONIBLE, 20);
        boolean registradoDuplicado = batallon.registrarVehiculo(duplicado);

        assertFalse(registradoDuplicado);
        assertEquals(1, batallon.getListaVehiculos().size());
    }

    // Caso 4: Buscar vehículos existentes e inexistentes
    @Test
    void testBuscarVehiculosExistentesEInexistentes() {
        batallon.registrarVehiculo(transporte);

        // Búsqueda de vehículo existente
        Vehiculo encontrado = batallon.buscarVehiculo("T-100");
        assertNotNull(encontrado);
        assertEquals("T-100", encontrado.getId());

        // Búsqueda de vehículo inexistente
        Vehiculo noEncontrado = batallon.buscarVehiculo("ID no encontrado");
        assertNull(noEncontrado);
    }

    // Caso 5: Eliminar un vehículo registrado
    @Test
    void testEliminarVehiculoRegistrado() {
        batallon.registrarVehiculo(transporte);
        assertNotNull(batallon.buscarVehiculo("T-100"));

        boolean eliminado = batallon.eliminarVehiculo("T-100");
        assertTrue(eliminado);
        assertNull(batallon.buscarVehiculo("T-100"));
        assertEquals(0, batallon.getListaVehiculos().size());
    }

    // Caso 7: Registrar una mision con personal y vehiculos asignados
    @Test
    void testRegistrarMisionConPersonalYVehiculos() {
        batallon.registrarVehiculo(transporte);
        batallon.registrarVehiculo(blindado);

        Mision mision = new Mision("M-01", LocalDate.now(), "Montaña");
        mision.agregarPersonal("Yair");
        mision.agregarPersonal("Julian");

        boolean registrada = batallon.registrarMision(mision);
        assertTrue(registrada);

        assertTrue(batallon.asignarVehiculoMision("M-01", "T-100"));
        assertTrue(batallon.asignarVehiculoMision("M-01", "B-200"));

        Mision misionGuardada = batallon.buscarMision("M-01");
        assertNotNull(misionGuardada);
        assertEquals(2, misionGuardada.getPersonalAsignado().size());
        assertEquals(2, misionGuardada.getListaDetallesMision().size());
    }

    // Caso 9: Verificar que un vehículo con exactamente 50 misiones completadas no aparezca en el listado
    @Test
    void testVehiculoConExactamente50MisionesNoApareceEnListado() {
        batallon.registrarVehiculo(transporte);

        // Completar exactamente 50 misiones
        for (int i = 1; i <= 50; i++) {
            String idMision = "M-" + i;
            Mision m = new Mision(idMision, LocalDate.now(), "Ubicación " + i);
            m.agregarPersonal("Operativo");
            batallon.registrarMision(m);
            batallon.asignarVehiculoMision(idMision, "T-100");
            batallon.iniciarMision(idMision);
            batallon.completarMision(idMision);
        }

        assertEquals(50, transporte.contarMisionesCompletadas());
        ArrayList<Vehiculo> resultado = batallon.obtenerVehiculosConMasDe50Misiones();

        // No debe aparecer porque se requiere STRICTAMENTE más de 50 (> 50)
        assertFalse(resultado.contains(transporte));
        assertEquals(0, resultado.size());
    }

    // Caso 10: Obtener vehículos con más de 50 misiones completadas (51 misiones)
    @Test
    void testObtenerVehiculosConMasDe50MisionesCompletadas() {
        batallon.registrarVehiculo(transporte);

        // Completar 51 misiones
        for (int i = 1; i <= 51; i++) {
            String idMision = "M-" + i;
            Mision m = new Mision(idMision, LocalDate.now(), "Ubicación " + i);
            m.agregarPersonal("Operativo");
            batallon.registrarMision(m);
            batallon.asignarVehiculoMision(idMision, "T-100");
            batallon.iniciarMision(idMision);
            batallon.completarMision(idMision);
        }

        assertEquals(51, transporte.contarMisionesCompletadas());
        ArrayList<Vehiculo> resultado = batallon.obtenerVehiculosConMasDe50Misiones();

        // Debe incluir el vehículo
        assertTrue(resultado.contains(transporte));
        assertEquals(1, resultado.size());
    }
}