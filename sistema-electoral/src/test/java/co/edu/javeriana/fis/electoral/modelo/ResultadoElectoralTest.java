package co.edu.javeriana.fis.electoral.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ResultadoElectoralTest {

    private Candidato crearCandidato() {
        return new Candidato("1001", "Juan", 1);
    }

    @Test
    void constructorValido() {
        assertNotNull(new ResultadoElectoral(crearCandidato(), 100));
    }

    @Test
    void candidatoNulo() {
        assertThrows(IllegalArgumentException.class,
            () -> new ResultadoElectoral(null, 100));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Integer.MIN_VALUE})
    void votosInvalidos(int votos) {
        assertThrows(IllegalArgumentException.class,
            () -> new ResultadoElectoral(crearCandidato(), votos));
    }

    @Test
    void getCandidatoCorrecto() {
        Candidato candidato = crearCandidato();

        assertSame(candidato,
            new ResultadoElectoral(candidato, 100).getCandidato());
    }

    @Test
    void getCandidatoAlternativo() {
        Candidato candidato = new Candidato("1002", "Maria", 2);

        assertSame(candidato,
            new ResultadoElectoral(candidato, 200).getCandidato());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100, Integer.MAX_VALUE})
    void getVotosCorrecto(int votos) {
        assertEquals(votos,
            new ResultadoElectoral(crearCandidato(), votos).getVotos());
    }
}