package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class AsignacionJuradoTest {

    @Test
    void constructorValido() {
        assertNotNull(
            new AsignacionJurado(
                new Jurado("1001", "Juan"),
                RolJurado.PRESIDENTE
            )
        );
    }

    @Test
    void constructorJuradoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new AsignacionJurado(null, RolJurado.PRESIDENTE)
        );
    }

    @Test
    void constructorRolNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new AsignacionJurado(new Jurado("1001", "Juan"), null)
        );
    }

    @Test
    void getJuradoCorrecto() {
        Jurado jurado = new Jurado("1001", "Juan");

        AsignacionJurado asignacion = new AsignacionJurado(
            jurado,
            RolJurado.PRESIDENTE
        );

        assertSame(jurado, asignacion.getJurado());
    }

    @Test
    void getJuradoAlternativo() {
        Jurado jurado = new Jurado("1002", "Maria");

        AsignacionJurado asignacion = new AsignacionJurado(
            jurado,
            RolJurado.VOCAL
        );

        assertSame(jurado, asignacion.getJurado());
    }

    @ParameterizedTest
    @EnumSource(RolJurado.class)
    void getRolCorrecto(RolJurado rol) {
        AsignacionJurado asignacion = new AsignacionJurado(
            new Jurado("1001", "Juan"),
            rol
        );

        assertEquals(rol, asignacion.getRol());
    }
}
