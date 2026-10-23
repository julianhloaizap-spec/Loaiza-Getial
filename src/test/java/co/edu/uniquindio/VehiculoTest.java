package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class VehiculoTest {

    private TransporteTropas transporte;
    private Blindado blindado;
    private Apoyo apoyo;

    @BeforeEach
    void creacionObjetos() {
        transporte = new TransporteTropas("T-01", "Camion", 2020, 12000.0, EstadoOperativo.DISPONIBLE, 25);
        blindado = new Blindado("B-01", "Blindado", 2021, 8000.0, EstadoOperativo.DISPONIBLE, 4);
        apoyo = new Apoyo("A-01", "Ambulancia", 2022, 5000.0, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
    }

    // Caso 1: Validar la creacion de los diferentes tipos de vehiculos
    @Test
    void testCreacionVehiculos() {
        assertNotNull(transporte);
        assertEquals("T-01", transporte.getId());
        assertEquals("Camion", transporte.getModelo());
        assertEquals(2020, transporte.getAnioFabricacion());
        assertEquals(12000.0, transporte.getKilometraje());
        assertEquals(EstadoOperativo.DISPONIBLE, transporte.getEstadoOperativo());
        assertEquals(25, transporte.getCapacidadSoldados());

        assertNotNull(blindado);
        assertEquals(4, blindado.getNivelBlindaje());

        assertNotNull(apoyo);
        assertEquals(TipoFuncion.MEDICO, apoyo.getTipoFuncion());
    }

    // Caso 6: Actualizar correctamente la información de un vehiculo
    @Test
    void testActualizarInformacionVehiculo() {
        assertTrue(transporte.setKilometraje(15000.0));
        assertEquals(15000.0, transporte.getKilometraje());

        assertTrue(transporte.setEstadoOperativo(EstadoOperativo.EN_MANTENIMIENTO));
        assertEquals(EstadoOperativo.EN_MANTENIMIENTO, transporte.getEstadoOperativo());

        // No debe bajar el kilometraje
        assertFalse(transporte.setKilometraje(1000.0));
        assertEquals(15000.0, transporte.getKilometraje());
    }

    // Caso 8: Contar solo las misiones completadas
    @Test
    void testContarUnicamenteMisionesCompletadas() {
        Batallon batallon = new Batallon("01", "Cacique", "03", "Calarcá", 3);
        batallon.registrarVehiculo(transporte);

        // Misión 1: completada
        Mision m1 = new Mision("M-01", LocalDate.now(), "Zona A");
        m1.agregarPersonal("Capitán Ríos");
        batallon.registrarMision(m1);
        batallon.asignarVehiculoMision("M-01", "T-01");
        batallon.iniciarMision("M-01");
        batallon.completarMision("M-01");

        // Misión 2: incompleto
        Mision m2 = new Mision("M-02", LocalDate.now(), "Zona B");
        m2.agregarPersonal("Teniente Gómez");
        batallon.registrarMision(m2);
        batallon.asignarVehiculoMision("M-02", "T-01");
        batallon.iniciarMision("M-02");

        // Misión 3: Se deja en estado planificada
        Mision m3 = new Mision("M-03", LocalDate.now(), "Zona C");
        m3.agregarPersonal("Sargento Silva");
        batallon.registrarMision(m3);
        batallon.asignarVehiculoMision("M-03", "T-01");

        // Debe contar 1 misión completada
        assertEquals(1, transporte.contarMisionesCompletadas());
    }
}