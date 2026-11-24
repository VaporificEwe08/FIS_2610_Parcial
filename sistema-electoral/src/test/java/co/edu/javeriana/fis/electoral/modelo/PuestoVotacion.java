package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PuestoVotacionTest {

    private PuestoVotacion crearPuesto() {
        return new PuestoVotacion("PV-01", "Colegio Central", "Calle 10");
    }

    @Test
    void constructorValido() {
        assertNotNull(crearPuesto());
    }

    @Test
    void codigoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion(null, "Colegio", "Calle 10")
        );
    }

    @Test
    void codigoVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion("", "Colegio", "Calle 10")
        );
    }

    @Test
    void nombreNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion("PV-01", null, "Calle 10")
        );
    }

    @Test
    void nombreVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion("PV-01", "", "Calle 10")
        );
    }

    @Test
    void direccionNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion("PV-01", "Colegio", null)
        );
    }

    @Test
    void direccionVacia() {
        assertThrows(IllegalArgumentException.class, () ->
            new PuestoVotacion("PV-01", "Colegio", "")
        );
    }

    @Test
    void getCodigoCorrecto() {
        assertEquals("PV-01", crearPuesto().getCodigo());
    }

    @Test
    void getCodigoAlternativo() {
        assertEquals(
            "PV-02",
            new PuestoVotacion("PV-02", "Escuela", "Calle 20").getCodigo()
        );
    }

    @Test
    void getNombreCorrecto() {
        assertEquals("Colegio Central", crearPuesto().getNombre());
    }

    @Test
    void getNombreAlternativo() {
        assertEquals(
            "Escuela",
            new PuestoVotacion("PV-02", "Escuela", "Calle 20").getNombre()
        );
    }

    @Test
    void getDireccionCorrecta() {
        assertEquals("Calle 10", crearPuesto().getDireccion());
    }

    @Test
    void getDireccionAlternativa() {
        assertEquals(
            "Calle 20",
            new PuestoVotacion("PV-02", "Escuela", "Calle 20").getDireccion()
        );
    }

    @Test
    void getMesasVacio() {
        assertTrue(crearPuesto().getMesas().isEmpty());
    }

    @Test
    void getMesasConDatos() {
        PuestoVotacion puesto = crearPuesto();
        MesaVotacion mesa = new MesaVotacion(1, 300);

        puesto.agregarMesa(mesa);

        assertEquals(mesa, puesto.getMesas().get(0));
    }

    @Test
    void getMesasInmutable() {
        assertThrows(UnsupportedOperationException.class, () ->
            crearPuesto().getMesas().add(new MesaVotacion(1, 300))
        );
    }

    @Test
    void agregarMesaValida() {
        PuestoVotacion puesto = crearPuesto();

        puesto.agregarMesa(new MesaVotacion(1, 300));

        assertEquals(1, puesto.getMesas().size());
    }

    @Test
    void agregarVariasMesas() {
        PuestoVotacion puesto = crearPuesto();

        puesto.agregarMesa(new MesaVotacion(1, 300));
        puesto.agregarMesa(new MesaVotacion(2, 400));

        assertEquals(2, puesto.getMesas().size());
    }

    @Test
    void agregarMesaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            crearPuesto().agregarMesa(null)
        );
    }

    @Test
    void agregarMesaDuplicada() {
        PuestoVotacion puesto = crearPuesto();

        puesto.agregarMesa(new MesaVotacion(1, 300));

        assertThrows(IllegalArgumentException.class, () ->
            puesto.agregarMesa(new MesaVotacion(1, 400))
        );

        assertEquals(1, puesto.getMesas().size());
    }

    @Test
    void eliminarMesaExistente() {
        PuestoVotacion puesto = crearPuesto();

        puesto.agregarMesa(new MesaVotacion(1, 300));

        assertTrue(puesto.eliminarMesa(1));
        assertTrue(puesto.getMesas().isEmpty());
    }

    @Test
    void eliminarMesaInexistente() {
        assertFalse(crearPuesto().eliminarMesa(99));
    }

    @Test
    void eliminarMesaCero() {
        assertThrows(IllegalArgumentException.class, () ->
            crearPuesto().eliminarMesa(0)
        );
    }

    @Test
    void eliminarMesaNegativa() {
        assertThrows(IllegalArgumentException.class, () ->
            crearPuesto().eliminarMesa(-1)
        );
    }
}
