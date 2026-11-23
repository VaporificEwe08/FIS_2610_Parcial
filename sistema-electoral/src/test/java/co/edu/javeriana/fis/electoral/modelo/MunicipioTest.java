package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MunicipioTest {

    @Test
    void constructorValido() {
        assertNotNull(new Municipio("25001", "Agua de Dios"));
    }

    @Test
    void codigoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Municipio(null, "Chia")
        );
    }

    @Test
    void codigoVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new Municipio("", "Chia")
        );
    }

    @Test
    void nombreNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Municipio("25175", null)
        );
    }

    @Test
    void nombreVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new Municipio("25175", " ")
        );
    }

    @Test
    void getCodigoCorrecto() {
        assertEquals("25175", new Municipio("25175", "Chia").getCodigo());
    }

    @Test
    void getCodigoAlternativo() {
        assertEquals("25899", new Municipio("25899", "Zipaquira").getCodigo());
    }

    @Test
    void getNombreCorrecto() {
        assertEquals("Chia", new Municipio("25175", "Chia").getNombre());
    }

    @Test
    void getNombreAlternativo() {
        assertEquals(
            "Zipaquira",
            new Municipio("25899", "Zipaquira").getNombre()
        );
    }

    @Test
    void longitudMinima() {
        Municipio municipio = new Municipio("1", "A");

        assertEquals("1", municipio.getCodigo());
        assertEquals("A", municipio.getNombre());
    }
}
