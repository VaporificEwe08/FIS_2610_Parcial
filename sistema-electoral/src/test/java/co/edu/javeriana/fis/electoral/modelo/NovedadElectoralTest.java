package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NovedadElectoralTest {

    private final MesaVotacion mesa = new MesaVotacion(1, 300);

    @Test
    void incidenteValido() {
        assertNotNull(new Incidente("I-01", "Retraso", mesa));
    }

    @Test
    void reclamacionValida() {
        assertNotNull(new Reclamacion("R-01", "Revision", mesa));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void codigoInvalido(String codigo) {
        assertThrows(IllegalArgumentException.class, () ->
            new Incidente(codigo, "Retraso", mesa)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Reclamacion(codigo, "Revision", mesa)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void descripcionInvalida(String descripcion) {
        assertThrows(IllegalArgumentException.class, () ->
            new Incidente("I-01", descripcion, mesa)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Reclamacion("R-01", descripcion, mesa)
        );
    }

    @Test
    void incidenteMesaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new Incidente("I-01", "Retraso", null)
        );
    }

    @Test
    void reclamacionMesaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new Reclamacion("R-01", "Revision", null)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "I-01", "1" })
    void getCodigoCorrecto(String codigo) {
        assertEquals(
            codigo,
            new Incidente(codigo, "Retraso", mesa).getCodigo()
        );

        assertEquals(
            codigo,
            new Reclamacion(codigo, "Revision", mesa).getCodigo()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "Retraso", "A" })
    void getDescripcionCorrecta(String descripcion) {
        assertEquals(
            descripcion,
            new Incidente("I-01", descripcion, mesa).getDescripcion()
        );

        assertEquals(
            descripcion,
            new Reclamacion("R-01", descripcion, mesa).getDescripcion()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    void getMesaCorrecta(int numero) {
        MesaVotacion otraMesa = new MesaVotacion(numero, 300);

        assertSame(
            otraMesa,
            new Incidente("I-01", "Retraso", otraMesa).getMesa()
        );

        assertSame(
            otraMesa,
            new Reclamacion("R-01", "Revision", otraMesa).getMesa()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "I-01", "I-02" })
    void tipoIncidente(String codigo) {
        assertEquals(
            "INCIDENTE",
            new Incidente(codigo, "Retraso", mesa).getTipo()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "R-01", "R-02" })
    void tipoReclamacion(String codigo) {
        assertEquals(
            "RECLAMACION",
            new Reclamacion(codigo, "Revision", mesa).getTipo()
        );
    }
}
