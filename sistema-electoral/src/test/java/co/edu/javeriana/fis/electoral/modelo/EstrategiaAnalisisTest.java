package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EstrategiaAnalisisTest {

    private MesaVotacion crearMesa(int censo, int votos) {
        MesaVotacion mesa = new MesaVotacion(1, censo);

        mesa.registrarActa(
            new ActaElectoral.Builder("ACT-01").votosBlanco(votos).build()
        );

        return mesa;
    }

    @Test
    void totalVotosCorrecto() {
        EstrategiaAnalisis estrategia = new EstrategiaTotalVotos();

        assertEquals(150.0, estrategia.analizar(crearMesa(300, 150)));
    }

    @Test
    void totalVotosCero() {
        EstrategiaAnalisis estrategia = new EstrategiaTotalVotos();

        assertEquals(0.0, estrategia.analizar(crearMesa(300, 0)));
    }

    @Test
    void totalVotosComponenteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new EstrategiaTotalVotos().analizar(null)
        );
    }

    @Test
    void participacionCorrecta() {
        EstrategiaAnalisis estrategia = new EstrategiaParticipacion();

        assertEquals(50.0, estrategia.analizar(crearMesa(300, 150)), 0.001);
    }

    @Test
    void participacionCompleta() {
        EstrategiaAnalisis estrategia = new EstrategiaParticipacion();

        assertEquals(100.0, estrategia.analizar(crearMesa(300, 300)), 0.001);
    }

    @Test
    void participacionSinVotos() {
        EstrategiaAnalisis estrategia = new EstrategiaParticipacion();

        assertEquals(0.0, estrategia.analizar(crearMesa(300, 0)));
    }

    @Test
    void participacionCensoCero() {
        EstrategiaAnalisis estrategia = new EstrategiaParticipacion();

        assertEquals(0.0, estrategia.analizar(crearMesa(0, 0)));
    }

    @Test
    void participacionComponenteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new EstrategiaParticipacion().analizar(null)
        );
    }

    @Test
    void abstencionCorrecta() {
        EstrategiaAnalisis estrategia = new EstrategiaAbstencion();

        assertEquals(75.0, estrategia.analizar(crearMesa(400, 100)), 0.001);
    }

    @Test
    void abstencionCompleta() {
        EstrategiaAnalisis estrategia = new EstrategiaAbstencion();

        assertEquals(100.0, estrategia.analizar(crearMesa(300, 0)), 0.001);
    }

    @Test
    void abstencionCero() {
        EstrategiaAnalisis estrategia = new EstrategiaAbstencion();

        assertEquals(0.0, estrategia.analizar(crearMesa(300, 300)));
    }

    @Test
    void abstencionCensoCero() {
        EstrategiaAnalisis estrategia = new EstrategiaAbstencion();

        assertEquals(0.0, estrategia.analizar(crearMesa(0, 0)));
    }

    @Test
    void abstencionComponenteNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new EstrategiaAbstencion().analizar(null)
        );
    }

    @Test
    void analizarTerritorioCompuesto() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        puesto.agregarMesa(crearMesa(300, 150));

        MesaVotacion segunda = new MesaVotacion(2, 200);

        segunda.registrarActa(
            new ActaElectoral.Builder("ACT-02").votosBlanco(50).build()
        );

        puesto.agregarMesa(segunda);

        assertEquals(200.0, new EstrategiaTotalVotos().analizar(puesto));

        assertEquals(
            40.0,
            new EstrategiaParticipacion().analizar(puesto),
            0.001
        );

        assertEquals(60.0, new EstrategiaAbstencion().analizar(puesto), 0.001);
    }
}
