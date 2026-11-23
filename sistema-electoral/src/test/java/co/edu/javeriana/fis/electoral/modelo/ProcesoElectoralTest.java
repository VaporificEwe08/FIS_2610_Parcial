package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ProcesoElectoralTest {

    private ProcesoElectoral crearProceso() {
        return new ProcesoElectoral(
            "PE-01",
            "Elecciones presidenciales",
            TipoEleccion.PRESIDENCIAL,
            LocalDate.of(2026, 5, 31)
        );
    }

    @Test
    void constructorValido() {
        assertNotNull(crearProceso());
    }

    @Test
    void constructorIdNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral(
                null,
                "Elecciones",
                TipoEleccion.PRESIDENCIAL,
                LocalDate.now()
            )
        );
    }

    @Test
    void constructorIdVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral(
                "",
                "Elecciones",
                TipoEleccion.PRESIDENCIAL,
                LocalDate.now()
            )
        );
    }

    @Test
    void constructorNombreNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral(
                "PE-01",
                null,
                TipoEleccion.PRESIDENCIAL,
                LocalDate.now()
            )
        );
    }

    @Test
    void constructorNombreVacio() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral(
                "PE-01",
                " ",
                TipoEleccion.PRESIDENCIAL,
                LocalDate.now()
            )
        );
    }

    @Test
    void constructorTipoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral("PE-01", "Elecciones", null, LocalDate.now())
        );
    }

    @Test
    void constructorFechaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            new ProcesoElectoral(
                "PE-01",
                "Elecciones",
                TipoEleccion.PRESIDENCIAL,
                null
            )
        );
    }

    @Test
    void getIdCorrecto() {
        assertEquals("PE-01", crearProceso().getId());
    }

    @Test
    void getIdAlternativo() {
        ProcesoElectoral proceso = new ProcesoElectoral(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.now()
        );

        assertEquals("PE-02", proceso.getId());
    }

    @Test
    void getNombreCorrecto() {
        assertEquals("Elecciones presidenciales", crearProceso().getNombre());
    }

    @Test
    void getNombreAlternativo() {
        ProcesoElectoral proceso = new ProcesoElectoral(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.now()
        );

        assertEquals("Congreso", proceso.getNombre());
    }

    @Test
    void getTipoPresidencial() {
        assertEquals(TipoEleccion.PRESIDENCIAL, crearProceso().getTipo());
    }

    @Test
    void getTipoCongreso() {
        ProcesoElectoral proceso = new ProcesoElectoral(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.now()
        );

        assertEquals(TipoEleccion.CONGRESO, proceso.getTipo());
    }

    @Test
    void getFechaCorrecta() {
        assertEquals(LocalDate.of(2026, 5, 31), crearProceso().getFecha());
    }

    @Test
    void getFechaAlternativa() {
        LocalDate fecha = LocalDate.of(2026, 3, 8);

        ProcesoElectoral proceso = new ProcesoElectoral(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            fecha
        );

        assertEquals(fecha, proceso.getFecha());
    }

    @Test
    void cambiarFechaValida() {
        ProcesoElectoral proceso = crearProceso();
        LocalDate fecha = LocalDate.of(2026, 6, 21);

        proceso.cambiarFecha(fecha);

        assertEquals(fecha, proceso.getFecha());
    }

    @Test
    void cambiarFechaNula() {
        ProcesoElectoral proceso = crearProceso();

        assertThrows(IllegalArgumentException.class, () ->
            proceso.cambiarFecha(null)
        );

        assertEquals(LocalDate.of(2026, 5, 31), proceso.getFecha());
    }

    @Test
    void constructorLongitudMinima() {
        ProcesoElectoral proceso = new ProcesoElectoral(
            "1",
            "A",
            TipoEleccion.LOCAL,
            LocalDate.of(2026, 1, 1)
        );

        assertEquals("1", proceso.getId());
        assertEquals("A", proceso.getNombre());
    }
}
