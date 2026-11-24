package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MesaVotacionTest {

    @Test
    void constructorValido() {
        assertNotNull(new MesaVotacion(1, 300));
    }

    @Test
    void numeroCero() {
        assertThrows(IllegalArgumentException.class, () ->
            new MesaVotacion(0, 300)
        );
    }

    @Test
    void numeroNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            new MesaVotacion(-1, 300)
        );
    }

    @Test
    void censoNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
            new MesaVotacion(1, -1)
        );
    }

    @Test
    void censoCero() {
        assertEquals(0, new MesaVotacion(1, 0).getCensoElectoral());
    }

    @Test
    void getNumeroCorrecto() {
        assertEquals(1, new MesaVotacion(1, 300).getNumero());
    }

    @Test
    void getNumeroAlternativo() {
        assertEquals(10, new MesaVotacion(10, 400).getNumero());
    }

    @Test
    void getCensoCorrecto() {
        assertEquals(300, new MesaVotacion(1, 300).getCensoElectoral());
    }

    @Test
    void getCensoAlternativo() {
        assertEquals(500, new MesaVotacion(2, 500).getCensoElectoral());
    }

    @Test
    void getAsignacionesVacio() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertTrue(mesa.getAsignaciones().isEmpty());
    }

    @Test
    void getAsignacionesConDatos() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        AsignacionJurado asignacion = new AsignacionJurado(
            new Jurado("1001", "Juan"),
            RolJurado.PRESIDENTE
        );

        mesa.asignarJurado(asignacion);

        assertEquals(asignacion, mesa.getAsignaciones().get(0));
    }

    @Test
    void getAsignacionesInmutable() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(UnsupportedOperationException.class, () ->
            mesa
                .getAsignaciones()
                .add(
                    new AsignacionJurado(
                        new Jurado("1001", "Juan"),
                        RolJurado.PRESIDENTE
                    )
                )
        );
    }

    @Test
    void asignarJuradoValido() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("1001", "Juan"),
                RolJurado.PRESIDENTE
            )
        );

        assertEquals(1, mesa.getAsignaciones().size());
    }

    @Test
    void asignarVariosJurados() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("1001", "Juan"),
                RolJurado.PRESIDENTE
            )
        );

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("1002", "Maria"),
                RolJurado.VICEPRESIDENTE
            )
        );

        assertEquals(2, mesa.getAsignaciones().size());
    }

    @Test
    void asignarJuradoNulo() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.asignarJurado(null)
        );
    }

    @Test
    void asignarJuradoDuplicado() {
        MesaVotacion mesa = new MesaVotacion(1, 300);
        Jurado jurado = new Jurado("1001", "Juan");

        mesa.asignarJurado(new AsignacionJurado(jurado, RolJurado.PRESIDENTE));

        assertThrows(IllegalArgumentException.class, () ->
            mesa.asignarJurado(new AsignacionJurado(jurado, RolJurado.VOCAL))
        );

        assertEquals(1, mesa.getAsignaciones().size());
    }

    @Test
    void asignarRolOcupado() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("1001", "Juan"),
                RolJurado.PRESIDENTE
            )
        );

        assertThrows(IllegalArgumentException.class, () ->
            mesa.asignarJurado(
                new AsignacionJurado(
                    new Jurado("1002", "Maria"),
                    RolJurado.PRESIDENTE
                )
            )
        );
    }

    @Test
    void asignarMultiplesVocales() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.asignarJurado(
            new AsignacionJurado(new Jurado("1001", "Juan"), RolJurado.VOCAL)
        );

        mesa.asignarJurado(
            new AsignacionJurado(new Jurado("1002", "Maria"), RolJurado.VOCAL)
        );

        assertEquals(2, mesa.getAsignaciones().size());
    }

    @Test
    void eliminarJuradoExistente() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("1001", "Juan"),
                RolJurado.PRESIDENTE
            )
        );

        assertTrue(mesa.eliminarJurado("1001"));
        assertTrue(mesa.getAsignaciones().isEmpty());
    }

    @Test
    void eliminarJuradoInexistente() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertFalse(mesa.eliminarJurado("9999"));
    }

    @Test
    void eliminarJuradoDocumentoNulo() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.eliminarJurado(null)
        );
    }

    @Test
    void eliminarJuradoDocumentoVacio() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.eliminarJurado("")
        );
    }

    @Test
    void getActaSinRegistro() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertNull(mesa.getActa());
    }

    @Test
    void getActaRegistrada() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosBlanco(10)
            .build();

        mesa.registrarActa(acta);

        assertSame(acta, mesa.getActa());
    }

    @Test
    void registrarActaValida() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .agregarResultado(
                new ResultadoElectoral(new Candidato("1001", "Juan", 1), 200)
            )
            .build();

        mesa.registrarActa(acta);

        assertEquals(200L, mesa.getActa().getTotalVotos());
    }

    @Test
    void registrarActaNula() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.registrarActa(null)
        );
    }

    @Test
    void registrarActaDuplicada() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        ActaElectoral primera = new ActaElectoral.Builder("ACT-01").build();

        ActaElectoral segunda = new ActaElectoral.Builder("ACT-02").build();

        mesa.registrarActa(primera);

        assertThrows(IllegalStateException.class, () ->
            mesa.registrarActa(segunda)
        );

        assertSame(primera, mesa.getActa());
    }

    @Test
    void registrarActaSuperaCenso() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosBlanco(301)
            .build();

        assertThrows(IllegalArgumentException.class, () ->
            mesa.registrarActa(acta)
        );

        assertNull(mesa.getActa());
    }

    @Test
    void registrarActaIgualAlCenso() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        ActaElectoral acta = new ActaElectoral.Builder("ACT-01")
            .votosBlanco(300)
            .build();

        mesa.registrarActa(acta);

        assertEquals(300L, mesa.getActa().getTotalVotos());
    }

    @Test
    void getNovedadesVacio() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertTrue(mesa.getNovedades().isEmpty());
    }

    @Test
    void getNovedadesConDatos() {
        MesaVotacion mesa = new MesaVotacion(1, 300);
        Incidente incidente = new Incidente("I-01", "Retraso", mesa);

        mesa.registrarNovedad(incidente);

        assertSame(incidente, mesa.getNovedades().get(0));
    }

    @Test
    void getNovedadesInmutable() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(UnsupportedOperationException.class, () ->
            mesa.getNovedades().add(new Incidente("I-01", "Retraso", mesa))
        );
    }

    @Test
    void registrarVariasNovedades() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.registrarNovedad(new Incidente("I-01", "Retraso", mesa));

        mesa.registrarNovedad(new Reclamacion("R-01", "Revision", mesa));

        assertEquals(2, mesa.getNovedades().size());
    }

    @Test
    void registrarNovedadNula() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.registrarNovedad(null)
        );
    }

    @Test
    void registrarNovedadDeOtraMesa() {
        MesaVotacion mesa = new MesaVotacion(1, 300);
        MesaVotacion otraMesa = new MesaVotacion(2, 300);

        Incidente incidente = new Incidente("I-01", "Retraso", otraMesa);

        assertThrows(IllegalArgumentException.class, () ->
            mesa.registrarNovedad(incidente)
        );

        assertTrue(mesa.getNovedades().isEmpty());
    }

    @Test
    void registrarCodigoNovedadDuplicado() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        mesa.registrarNovedad(new Incidente("N-01", "Retraso", mesa));

        assertThrows(IllegalArgumentException.class, () ->
            mesa.registrarNovedad(new Reclamacion("N-01", "Revision", mesa))
        );

        assertEquals(1, mesa.getNovedades().size());
    }
}
