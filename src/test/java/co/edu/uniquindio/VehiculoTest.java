package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    private static final Logger LOG = Logger.getLogger(VehiculoTest.class.getName());

    @DisplayName("Validar creación de Transportes de tropasa")
    @Test
     void testCrearTransportesTropas (){
        LOG.info("Iniciando prueba Transportes Tropas.");

        TransporteTropas transporteTropas = new TransporteTropas("01", "modelo1", 2020, 15000, EstadoOperativo.DISPONIBLE, 30);
        assertEquals("01", transporteTropas.getId());
        assertEquals("modelo1", transporteTropas.getModelo());
        assertEquals(2020, transporteTropas.getAnioFabricacion());
        assertEquals(15000, transporteTropas.getKilometraje());
        assertEquals(EstadoOperativo.DISPONIBLE, transporteTropas.getEstadoOperativo());
        assertEquals(30, transporteTropas.getCapacidadSoldados());
        LOG.info(transporteTropas.toString());
        LOG.info("Finalizando prueba Transportes Tropas.");

    }
    @DisplayName("Validar creación de Vehiculo Blindado")
    @Test
    void testCrearVehiculoBlindado() {
        LOG.info("Iniciando prueba Blindado.");

        Blindado vehiculoBlindado = new Blindado("10", "modelo10", 2021, 20000, EstadoOperativo.DISPONIBLE, 5);
        assertEquals("10", vehiculoBlindado.getId());
        assertEquals("modelo10", vehiculoBlindado.getModelo());
        assertEquals(2021, vehiculoBlindado.getAnioFabricacion());
        assertEquals(20000, vehiculoBlindado.getKilometraje());
        assertEquals(EstadoOperativo.DISPONIBLE, vehiculoBlindado.getEstadoOperativo());
        assertEquals(5, vehiculoBlindado.getNivelBlindaje());
        LOG.info(vehiculoBlindado.toString());
        LOG.info("Finalizando prueba Blindado...");
    }

    @DisplayName("Validar creación de Vehiculo Apoyo")
    @Test
    void testCrearVehiculoApoyo() {
        LOG.info("Iniciando prueba vehiculo apoyo.");

        Apoyo vehiculoApoyo = new Apoyo("11", "modelo11", 2024, 50000, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
        assertEquals("11", vehiculoApoyo.getId());
        assertEquals("modelo11", vehiculoApoyo.getModelo());
        assertEquals(2024, vehiculoApoyo.getAnioFabricacion());
        assertEquals(50000, vehiculoApoyo.getKilometraje());
        assertEquals(EstadoOperativo.DISPONIBLE, vehiculoApoyo.getEstadoOperativo());
        assertEquals(TipoFuncion.MEDICO, vehiculoApoyo.getTipoFuncion());
        LOG.info(vehiculoApoyo.toString());
        LOG.info("Finalizando prueba Blindado...");
    }
}