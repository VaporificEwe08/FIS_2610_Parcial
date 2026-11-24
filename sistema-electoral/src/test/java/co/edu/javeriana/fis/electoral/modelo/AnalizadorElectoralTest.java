package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AnalizadorElectoralTest {

    private MesaVotacion crearMesa() {
        MesaVotacion mesa = new MesaVotacion(1, 400);

        mesa.registrarActa(
            new ActaElectoral.Builder("ACT-01").votosBlanco(100).build()
        );

        return mesa;
    }

    @Test
    void constructorValido() {
        assertNotNull(new AnalizadorElectoral(new EstrategiaTotalVotos()));
    }

    @Test
    void constructorEstrategiaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new AnalizadorElectoral(null)
        );
    }

    @Test
    void getEstrategiaCorrecta() {
        EstrategiaAnalisis estrategia = new EstrategiaTotalVotos();

        AnalizadorElectoral analizador = new AnalizadorElectoral(estrategia);

        assertSame(estrategia, analizador.getEstrategia());
    }

    @Test
    void getEstrategiaAlternativa() {
        EstrategiaAnalisis estrategia = new EstrategiaAbstencion();

        AnalizadorElectoral analizador = new AnalizadorElectoral(estrategia);

        assertSame(estrategia, analizador.getEstrategia());
    }

    @Test
    void cambiarEstrategiaValida() {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaTotalVotos()
        );

        EstrategiaAnalisis nueva = new EstrategiaParticipacion();

        analizador.cambiarEstrategia(nueva);

        assertSame(nueva, analizador.getEstrategia());
    }

    @Test
    void cambiarEstrategiaNula() {
        EstrategiaAnalisis original = new EstrategiaTotalVotos();

        AnalizadorElectoral analizador = new AnalizadorElectoral(original);

        assertThrows(IllegalArgumentException.class, () ->
            analizador.cambiarEstrategia(null)
        );

        assertSame(original, analizador.getEstrategia());
    }

    @Test
    void analizarTotalVotos() {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaTotalVotos()
        );

        assertEquals(100.0, analizador.analizar(crearMesa()));
    }

    @Test
    void analizarParticipacion() {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaParticipacion()
        );

        assertEquals(25.0, analizador.analizar(crearMesa()), 0.001);
    }

    @Test
    void analizarComponenteNulo() {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaTotalVotos()
        );

        assertThrows(IllegalArgumentException.class, () ->
            analizador.analizar(null)
        );
    }

    @Test
    void cambiarEstrategiasDinamicamente() {
        MesaVotacion mesa = crearMesa();

        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaTotalVotos()
        );

        assertEquals(100.0, analizador.analizar(mesa));

        analizador.cambiarEstrategia(new EstrategiaParticipacion());

        assertEquals(25.0, analizador.analizar(mesa), 0.001);

        analizador.cambiarEstrategia(new EstrategiaAbstencion());

        assertEquals(75.0, analizador.analizar(mesa), 0.001);
    }
}
