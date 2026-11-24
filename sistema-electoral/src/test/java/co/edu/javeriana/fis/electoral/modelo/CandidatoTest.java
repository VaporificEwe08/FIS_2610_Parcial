package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CandidatoTest {

    @Test
    void constructorValido() {
        assertNotNull(new Candidato("1001", "Juan Perez", 1));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void documentoInvalido(String documento) {
        assertThrows(IllegalArgumentException.class, () ->
            new Candidato(documento, "Juan Perez", 1)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void nombreInvalido(String nombre) {
        assertThrows(IllegalArgumentException.class, () ->
            new Candidato("1001", nombre, 1)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1 })
    void tarjetonInvalido(int numero) {
        assertThrows(IllegalArgumentException.class, () ->
            new Candidato("1001", "Juan Perez", numero)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "1001", "2002" })
    void getDocumentoCorrecto(String documento) {
        assertEquals(
            documento,
            new Candidato(documento, "Juan Perez", 1).getDocumento()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "Juan Perez", "Maria Gomez" })
    void getNombreCorrecto(String nombre) {
        assertEquals(nombre, new Candidato("1001", nombre, 1).getNombre());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 100 })
    void getNumeroTarjetonCorrecto(int numero) {
        assertEquals(
            numero,
            new Candidato("1001", "Juan Perez", numero).getNumeroTarjeton()
        );
    }

    @Test
    void numeroTarjetonMaximo() {
        Candidato candidato = new Candidato(
            "1001",
            "Juan Perez",
            Integer.MAX_VALUE
        );

        assertEquals(Integer.MAX_VALUE, candidato.getNumeroTarjeton());
    }
}
