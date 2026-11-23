package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DepartamentoTest {

    private Departamento crearDepartamento() {
        return new Departamento("25", "Cundinamarca");
    }

    @Test
    void constructorValido() {
        assertNotNull(crearDepartamento());
    }

    @Test
    void codigoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Departamento(null, "Cundinamarca")
        );
    }

    @Test
    void codigoVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new Departamento("", "Cundinamarca")
        );
    }

    @Test
    void nombreNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new Departamento("25", null)
        );
    }

    @Test
    void nombreVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new Departamento("25", " ")
        );
    }

    @Test
    void getCodigoCorrecto() {
        assertEquals("25", crearDepartamento().getCodigo());
    }

    @Test
    void getCodigoAlternativo() {
        assertEquals("05", new Departamento("05", "Antioquia").getCodigo());
    }

    @Test
    void getNombreCorrecto() {
        assertEquals("Cundinamarca", crearDepartamento().getNombre());
    }

    @Test
    void getNombreAlternativo() {
        assertEquals(
            "Antioquia",
            new Departamento("05", "Antioquia").getNombre()
        );
    }

    @Test
    void getMunicipiosVacio() {
        assertTrue(crearDepartamento().getMunicipios().isEmpty());
    }

    @Test
    void getMunicipiosConDatos() {
        Departamento departamento = crearDepartamento();
        Municipio municipio = new Municipio("25175", "Chia");

        departamento.agregarMunicipio(municipio);

        assertEquals(municipio, departamento.getMunicipios().get(0));
    }

    @Test
    void getMunicipiosInmutable() {
        Departamento departamento = crearDepartamento();

        assertThrows(UnsupportedOperationException.class, () ->
            departamento.getMunicipios().add(new Municipio("25175", "Chia"))
        );
    }

    @Test
    void agregarMunicipioValido() {
        Departamento departamento = crearDepartamento();

        departamento.agregarMunicipio(new Municipio("25175", "Chia"));

        assertEquals(1, departamento.getMunicipios().size());
    }

    @Test
    void agregarVariosMunicipios() {
        Departamento departamento = crearDepartamento();

        departamento.agregarMunicipio(new Municipio("25175", "Chia"));

        departamento.agregarMunicipio(new Municipio("25899", "Zipaquira"));

        assertEquals(2, departamento.getMunicipios().size());
    }

    @Test
    void agregarMunicipioNulo() {
        Departamento departamento = crearDepartamento();

        assertThrows(IllegalArgumentException.class, () ->
            departamento.agregarMunicipio(null)
        );
    }

    @Test
    void agregarMunicipioDuplicado() {
        Departamento departamento = crearDepartamento();

        departamento.agregarMunicipio(new Municipio("25175", "Chia"));

        assertThrows(IllegalArgumentException.class, () ->
            departamento.agregarMunicipio(new Municipio("25175", "Chia"))
        );

        assertEquals(1, departamento.getMunicipios().size());
    }

    @Test
    void eliminarMunicipioExistente() {
        Departamento departamento = crearDepartamento();

        departamento.agregarMunicipio(new Municipio("25175", "Chia"));

        assertTrue(departamento.eliminarMunicipio("25175"));
        assertTrue(departamento.getMunicipios().isEmpty());
    }

    @Test
    void eliminarMunicipioInexistente() {
        assertFalse(crearDepartamento().eliminarMunicipio("99999"));
    }

    @Test
    void eliminarCodigoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            crearDepartamento().eliminarMunicipio(null)
        );
    }

    @Test
    void eliminarCodigoVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            crearDepartamento().eliminarMunicipio("")
        );
    }
}
