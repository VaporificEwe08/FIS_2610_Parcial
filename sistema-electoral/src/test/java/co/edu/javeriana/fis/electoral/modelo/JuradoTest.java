package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class JuradoTest {

    @Test
    void constructorValido() {
        assertNotNull(new Jurado("1001", "Juan Perez"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void documentoInvalido(String documento) {
        assertThrows(IllegalArgumentException.class, () ->
            new Jurado(documento, "Juan Perez")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void nombreInvalido(String nombre) {
        assertThrows(IllegalArgumentException.class, () ->
            new Jurado("1001", nombre)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "1001", "2002" })
    void getDocumentoCorrecto(String documento) {
        assertEquals(
            documento,
            new Jurado(documento, "Juan Perez").getDocumento()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "Juan Perez", "Maria Gomez" })
    void getNombreCorrecto(String nombre) {
        assertEquals(nombre, new Jurado("1001", nombre).getNombre());
    }

    @Test
    void longitudMinima() {
        Jurado jurado = new Jurado("1", "A");

        assertEquals("1", jurado.getDocumento());
        assertEquals("A", jurado.getNombre());
    }
}
