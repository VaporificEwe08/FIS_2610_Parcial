package co.edu.javeriana.fis.electoral.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class ActaElectoralTest {

    private ResultadoElectoral crearResultado() {
        return new ResultadoElectoral(
            new Candidato("1001", "Juan", 1), 100);
    }

    @Test
    void builderValido() {
        assertNotNull(new ActaElectoral.Builder("ACT-01"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void builderCodigoInvalido(String codigo) {
        assertThrows(IllegalArgumentException.class,
            () -> new ActaElectoral.Builder(codigo));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACT-01", "ACT-02"})
    void getCodigoCorrecto(String codigo) {
        ActaElectoral acta = new ActaElectoral.Builder(codigo).build();

        assertEquals(codigo, acta.getCodigo());
    }

    @Test
    void getResultadosVacio() {
        ActaElectoral acta =
            new ActaElectoral.Builder("ACT-01").build();

        assertTrue(acta.getResultados().isEmpty());
    }

    @Test
    void getResultadosConDatos() {
        ResultadoElectoral resultado = crearResultado();

        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(resultado)
            .build();

        assertEquals(resultado, acta.getResultados().get(0));
    }

    @Test
    void getResultadosInmutable() {
        ActaElectoral acta =
            new ActaElectoral.Builder("ACT-01").build();

        assertThrows(UnsupportedOperationException.class,
            () -> acta.getResultados().add(crearResultado()));
    }

    @Test
    void agregarResultadoValido() {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(crearResultado())
            .build();

        assertEquals(1, acta.getResultados().size());
    }

    @Test
    void agregarVariosResultados() {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(crearResultado())
            .agregarResultado(new ResultadoElectoral(
                new Candidato("1002", "Maria", 2), 50))
            .build();

        assertEquals(2, acta.getResultados().size());
    }

    @Test
    void agregarResultadoNulo() {
        ActaElectoral.Builder builder =
            new ActaElectoral.Builder("ACT-01");

        assertThrows(IllegalArgumentException.class,
            () -> builder.agregarResultado(null));
    }

    @Test
    void agregarCandidatoDuplicado() {
        ActaElectoral.Builder builder =
            new ActaElectoral.Builder("ACT-01")
                .agregarResultado(crearResultado());

        assertThrows(IllegalArgumentException.class,
            () -> builder.agregarResultado(crearResultado()));

        assertEquals(1, builder.build().getResultados().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 20})
    void votosBlancoValidos(int cantidad) {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosBlanco(cantidad)
            .build();

        assertEquals(cantidad, acta.getVotosBlanco());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    void votosBlancoInvalidos(int cantidad) {
        assertThrows(IllegalArgumentException.class, () ->
            new ActaElectoral.Builder("ACT-01").votosBlanco(cantidad));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15})
    void votosNulosValidos(int cantidad) {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosNulos(cantidad)
            .build();

        assertEquals(cantidad, acta.getVotosNulos());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    void votosNulosInvalidos(int cantidad) {
        assertThrows(IllegalArgumentException.class, () ->
            new ActaElectoral.Builder("ACT-01").votosNulos(cantidad));
    }

    @Test
    void getTotalVotosCorrecto() {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(crearResultado())
            .votosBlanco(10)
            .votosNulos(5)
            .build();

        assertEquals(115L, acta.getTotalVotos());
    }

    @Test
    void getTotalVotosCero() {
        ActaElectoral acta =
            new ActaElectoral.Builder("ACT-01").build();

        assertEquals(0L, acta.getTotalVotos());
    }

    @Test
    void getTotalVotosSinDesbordamiento() {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosBlanco(Integer.MAX_VALUE)
            .votosNulos(Integer.MAX_VALUE)
            .build();

        assertEquals(4294967294L, acta.getTotalVotos());
    }

    @Test
    void buildActaCompleta() {
        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(crearResultado())
            .votosBlanco(10)
            .votosNulos(5)
            .build();

        assertEquals("ACT-01", acta.getCodigo());
        assertEquals(1, acta.getResultados().size());
        assertEquals(10, acta.getVotosBlanco());
        assertEquals(5, acta.getVotosNulos());
    }

    @Test
    void buildActaIndependienteDelBuilder() {
        ActaElectoral.Builder builder =
            new ActaElectoral.Builder("ACT-01");

        ActaElectoral primera = builder.build();

        builder.agregarResultado(crearResultado());
        builder.votosBlanco(10);

        ActaElectoral segunda = builder.build();

        assertEquals(0L, primera.getTotalVotos());
        assertEquals(110L, segunda.getTotalVotos());
    }
}