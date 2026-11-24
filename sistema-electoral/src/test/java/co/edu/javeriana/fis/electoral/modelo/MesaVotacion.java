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
}
