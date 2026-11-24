package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FabricaNovedadTest {

    @ParameterizedTest
    @ValueSource(strings = { "I-01", "I-02" })
    void crearIncidente(String codigo) {
        FabricaIncidente fabrica = new FabricaIncidente();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        NovedadElectoral novedad = fabrica.crearNovedad(
            codigo,
            "Retraso",
            mesa
        );

        assertInstanceOf(Incidente.class, novedad);
        assertEquals(codigo, novedad.getCodigo());
    }

    @ParameterizedTest
    @ValueSource(strings = { "R-01", "R-02" })
    void crearReclamacion(String codigo) {
        FabricaReclamacion fabrica = new FabricaReclamacion();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        NovedadElectoral novedad = fabrica.crearNovedad(
            codigo,
            "Revision",
            mesa
        );

        assertInstanceOf(Reclamacion.class, novedad);
        assertEquals(codigo, novedad.getCodigo());
    }

    @Test
    void registrarIncidente() {
        FabricaNovedad fabrica = new FabricaIncidente();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        NovedadElectoral novedad = fabrica.registrar("I-01", "Retraso", mesa);

        assertInstanceOf(Incidente.class, novedad);
        assertSame(novedad, mesa.getNovedades().get(0));
    }

    @Test
    void registrarReclamacion() {
        FabricaNovedad fabrica = new FabricaReclamacion();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        NovedadElectoral novedad = fabrica.registrar("R-01", "Revision", mesa);

        assertInstanceOf(Reclamacion.class, novedad);
        assertSame(novedad, mesa.getNovedades().get(0));
    }

    @Test
    void registrarCodigoInvalido() {
        FabricaNovedad fabrica = new FabricaIncidente();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            fabrica.registrar("", "Retraso", mesa)
        );

        assertTrue(mesa.getNovedades().isEmpty());
    }

    @Test
    void registrarMesaNula() {
        FabricaNovedad fabrica = new FabricaReclamacion();

        assertThrows(IllegalArgumentException.class, () ->
            fabrica.registrar("R-01", "Revision", null)
        );
    }

    @Test
    void registrarNovedadDuplicada() {
        FabricaNovedad fabrica = new FabricaIncidente();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        fabrica.registrar("I-01", "Retraso", mesa);

        assertThrows(IllegalArgumentException.class, () ->
            fabrica.registrar("I-01", "Otro incidente", mesa)
        );

        assertEquals(1, mesa.getNovedades().size());
    }
}
