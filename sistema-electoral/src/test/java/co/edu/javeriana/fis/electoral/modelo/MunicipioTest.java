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

    @Test
    void getPuestosVacio() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertTrue(municipio.getPuestos().isEmpty());
    }

    @Test
    void getPuestosConDatos() {
        Municipio municipio = new Municipio("25175", "Chia");
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        municipio.agregarPuesto(puesto);

        assertEquals(puesto, municipio.getPuestos().get(0));
    }

    @Test
    void getPuestosInmutable() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertThrows(UnsupportedOperationException.class, () ->
            municipio
                .getPuestos()
                .add(new PuestoVotacion("PV-01", "Colegio", "Calle 10"))
        );
    }

    @Test
    void agregarPuestoValido() {
        Municipio municipio = new Municipio("25175", "Chia");

        municipio.agregarPuesto(
            new PuestoVotacion("PV-01", "Colegio", "Calle 10")
        );

        assertEquals(1, municipio.getPuestos().size());
    }

    @Test
    void agregarVariosPuestos() {
        Municipio municipio = new Municipio("25175", "Chia");

        municipio.agregarPuesto(
            new PuestoVotacion("PV-01", "Colegio", "Calle 10")
        );

        municipio.agregarPuesto(
            new PuestoVotacion("PV-02", "Escuela", "Calle 20")
        );

        assertEquals(2, municipio.getPuestos().size());
    }

    @Test
    void agregarPuestoNulo() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertThrows(IllegalArgumentException.class, () ->
            municipio.agregarPuesto(null)
        );
    }

    @Test
    void agregarPuestoDuplicado() {
        Municipio municipio = new Municipio("25175", "Chia");

        municipio.agregarPuesto(
            new PuestoVotacion("PV-01", "Colegio", "Calle 10")
        );

        assertThrows(IllegalArgumentException.class, () ->
            municipio.agregarPuesto(
                new PuestoVotacion("PV-01", "Escuela", "Calle 20")
            )
        );

        assertEquals(1, municipio.getPuestos().size());
    }

    @Test
    void eliminarPuestoExistente() {
        Municipio municipio = new Municipio("25175", "Chia");

        municipio.agregarPuesto(
            new PuestoVotacion("PV-01", "Colegio", "Calle 10")
        );

        assertTrue(municipio.eliminarPuesto("PV-01"));
        assertTrue(municipio.getPuestos().isEmpty());
    }

    @Test
    void eliminarPuestoInexistente() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertFalse(municipio.eliminarPuesto("PV-99"));
    }

    @Test
    void eliminarPuestoCodigoNulo() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertThrows(IllegalArgumentException.class, () ->
            municipio.eliminarPuesto(null)
        );
    }

    @Test
    void eliminarPuestoCodigoVacio() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertThrows(IllegalArgumentException.class, () ->
            municipio.eliminarPuesto("")
        );
    }
}
