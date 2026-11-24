package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EstadoProcesoTest {

    private ProcesoElectoral crearProceso() {
        return new ProcesoElectoral(
            "PE-01",
            "Elecciones presidenciales",
            TipoEleccion.PRESIDENCIAL,
            LocalDate.of(2026, 5, 31)
        );
    }

    @Test
    void estadoInicialProgramado() {
        assertEquals("PROGRAMADO", crearProceso().getEstado());
    }

    @Test
    void estadoInicialOtroProceso() {
        ProcesoElectoral proceso = new ProcesoElectoral(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.of(2026, 3, 8)
        );

        assertEquals("PROGRAMADO", proceso.getEstado());
    }

    @Test
    void iniciarVotacionValida() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();

        assertEquals("EN_VOTACION", proceso.getEstado());
    }

    @Test
    void iniciarVotacionDuplicada() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();

        assertThrows(IllegalStateException.class, proceso::iniciarVotacion);

        assertEquals("EN_VOTACION", proceso.getEstado());
    }

    @Test
    void iniciarEscrutinioValido() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();

        assertEquals("ESCRUTINIO", proceso.getEstado());
    }

    @Test
    void iniciarEscrutinioDesdeProgramado() {
        ProcesoElectoral proceso = crearProceso();

        assertThrows(IllegalStateException.class, proceso::iniciarEscrutinio);

        assertEquals("PROGRAMADO", proceso.getEstado());
    }

    @Test
    void iniciarEscrutinioDuplicado() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();

        assertThrows(IllegalStateException.class, proceso::iniciarEscrutinio);

        assertEquals("ESCRUTINIO", proceso.getEstado());
    }

    @Test
    void finalizarValido() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();
        proceso.finalizar();

        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void finalizarDesdeProgramado() {
        ProcesoElectoral proceso = crearProceso();

        assertThrows(IllegalStateException.class, proceso::finalizar);

        assertEquals("PROGRAMADO", proceso.getEstado());
    }

    @Test
    void finalizarDesdeVotacion() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();

        assertThrows(IllegalStateException.class, proceso::finalizar);

        assertEquals("EN_VOTACION", proceso.getEstado());
    }

    @Test
    void finalizarDuplicado() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();
        proceso.finalizar();

        assertThrows(IllegalStateException.class, proceso::finalizar);

        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void noRetrocederDesdeEscrutinio() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();

        assertThrows(IllegalStateException.class, proceso::iniciarVotacion);

        assertEquals("ESCRUTINIO", proceso.getEstado());
    }

    @Test
    void noReiniciarProcesoFinalizado() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();
        proceso.finalizar();

        assertThrows(IllegalStateException.class, proceso::iniciarVotacion);

        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void noReabrirEscrutinioFinalizado() {
        ProcesoElectoral proceso = crearProceso();

        proceso.iniciarVotacion();
        proceso.iniciarEscrutinio();
        proceso.finalizar();

        assertThrows(IllegalStateException.class, proceso::iniciarEscrutinio);

        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void cambiarEstadoNulo() {
        ProcesoElectoral proceso = crearProceso();

        assertThrows(IllegalArgumentException.class, () ->
            proceso.cambiarEstado(null)
        );

        assertEquals("PROGRAMADO", proceso.getEstado());
    }

    @Test
    void cambiarEstadoValido() {
        ProcesoElectoral proceso = crearProceso();

        proceso.cambiarEstado(new EstadoEnVotacion());

        assertEquals("EN_VOTACION", proceso.getEstado());
    }

    @Test
    void nombreEstadoProgramado() {
        assertEquals("PROGRAMADO", new EstadoProgramado().getNombre());
    }

    @Test
    void nombreEstadoEnVotacion() {
        assertEquals("EN_VOTACION", new EstadoEnVotacion().getNombre());
    }

    @Test
    void nombreEstadoEscrutinio() {
        assertEquals("ESCRUTINIO", new EstadoEscrutinio().getNombre());
    }

    @Test
    void nombreEstadoFinalizado() {
        assertEquals("FINALIZADO", new EstadoFinalizado().getNombre());
    }

    @Test
    void cicloDeVidaCompleto() {
        ProcesoElectoral proceso = crearProceso();

        assertEquals("PROGRAMADO", proceso.getEstado());

        proceso.iniciarVotacion();
        assertEquals("EN_VOTACION", proceso.getEstado());

        proceso.iniciarEscrutinio();
        assertEquals("ESCRUTINIO", proceso.getEstado());

        proceso.finalizar();
        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void procesosConEstadosIndependientes() {
        ProcesoElectoral primero = crearProceso();
        ProcesoElectoral segundo = crearProceso();

        primero.iniciarVotacion();

        assertEquals("EN_VOTACION", primero.getEstado());
        assertEquals("PROGRAMADO", segundo.getEstado());
    }
}
