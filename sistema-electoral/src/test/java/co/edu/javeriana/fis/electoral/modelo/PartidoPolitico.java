package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PartidoPoliticoTest {

    private PartidoPolitico crearPartido() {
        return new PartidoPolitico("PP-01", "Partido Ejemplo");
    }

    @Test
    void constructorValido() {
        assertNotNull(crearPartido());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void codigoInvalido(String codigo) {
        assertThrows(IllegalArgumentException.class, () ->
            new PartidoPolitico(codigo, "Partido Ejemplo")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void nombreInvalido(String nombre) {
        assertThrows(IllegalArgumentException.class, () ->
            new PartidoPolitico("PP-01", nombre)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "PP-01", "PP-02" })
    void getCodigoCorrecto(String codigo) {
        assertEquals(
            codigo,
            new PartidoPolitico(codigo, "Partido Ejemplo").getCodigo()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "Partido A", "Partido B" })
    void getNombreCorrecto(String nombre) {
        assertEquals(nombre, new PartidoPolitico("PP-01", nombre).getNombre());
    }

    @Test
    void getCandidatosVacio() {
        assertTrue(crearPartido().getCandidatos().isEmpty());
    }

    @Test
    void getCandidatosConDatos() {
        PartidoPolitico partido = crearPartido();
        Candidato candidato = new Candidato("1001", "Juan", 1);

        partido.agregarCandidato(candidato);

        assertEquals(candidato, partido.getCandidatos().get(0));
    }

    @Test
    void getCandidatosInmutable() {
        assertThrows(UnsupportedOperationException.class, () ->
            crearPartido()
                .getCandidatos()
                .add(new Candidato("1001", "Juan", 1))
        );
    }

    @Test
    void agregarCandidatoValido() {
        PartidoPolitico partido = crearPartido();

        partido.agregarCandidato(new Candidato("1001", "Juan", 1));

        assertEquals(1, partido.getCandidatos().size());
    }

    @Test
    void agregarVariosCandidatos() {
        PartidoPolitico partido = crearPartido();

        partido.agregarCandidato(new Candidato("1001", "Juan", 1));
        partido.agregarCandidato(new Candidato("1002", "Maria", 2));

        assertEquals(2, partido.getCandidatos().size());
    }

    @Test
    void agregarCandidatoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            crearPartido().agregarCandidato(null)
        );
    }

    @Test
    void agregarDocumentoDuplicado() {
        PartidoPolitico partido = crearPartido();

        partido.agregarCandidato(new Candidato("1001", "Juan", 1));

        assertThrows(IllegalArgumentException.class, () ->
            partido.agregarCandidato(new Candidato("1001", "Maria", 2))
        );

        assertEquals(1, partido.getCandidatos().size());
    }

    @Test
    void agregarTarjetonDuplicado() {
        PartidoPolitico partido = crearPartido();

        partido.agregarCandidato(new Candidato("1001", "Juan", 1));

        assertThrows(IllegalArgumentException.class, () ->
            partido.agregarCandidato(new Candidato("1002", "Maria", 1))
        );

        assertEquals(1, partido.getCandidatos().size());
    }

    @Test
    void eliminarCandidatoExistente() {
        PartidoPolitico partido = crearPartido();

        partido.agregarCandidato(new Candidato("1001", "Juan", 1));

        assertTrue(partido.eliminarCandidato("1001"));
        assertTrue(partido.getCandidatos().isEmpty());
    }

    @Test
    void eliminarCandidatoInexistente() {
        assertFalse(crearPartido().eliminarCandidato("9999"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void eliminarDocumentoInvalido(String documento) {
        assertThrows(IllegalArgumentException.class, () ->
            crearPartido().eliminarCandidato(documento)
        );
    }
}
