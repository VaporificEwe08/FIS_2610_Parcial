package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SistemaElectoralFacadeTest {

    private final SistemaElectoralFacade sistema = new SistemaElectoralFacade();

    private ProcesoElectoral crearProceso() {
        return sistema.registrarProceso(
            "PE-01",
            "Elecciones",
            TipoEleccion.PRESIDENCIAL,
            LocalDate.of(2026, 5, 31)
        );
    }

    private MesaVotacion crearMesa() {
        return new MesaVotacion(1, 400);
    }

    @Test
    void registrarProcesoValido() {
        ProcesoElectoral proceso = crearProceso();

        assertEquals("PE-01", proceso.getId());
        assertEquals(1, sistema.getProcesos().size());
    }

    @Test
    void registrarProcesoDuplicado() {
        crearProceso();

        assertThrows(IllegalArgumentException.class, this::crearProceso);
    }

    @Test
    void registrarProcesoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarProceso(
                null,
                "Elecciones",
                TipoEleccion.PRESIDENCIAL,
                LocalDate.now()
            )
        );
    }

    @Test
    void buscarProcesoExistente() {
        ProcesoElectoral proceso = crearProceso();

        assertSame(proceso, sistema.buscarProceso("PE-01"));
    }

    @Test
    void buscarProcesoInexistente() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.buscarProceso("PE-99")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " " })
    void buscarProcesoIdInvalido(String id) {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.buscarProceso(id)
        );
    }

    @Test
    void registrarDepartamentoValido() {
        Departamento departamento = sistema.registrarDepartamento(
            "25",
            "Cundinamarca"
        );

        assertEquals("25", departamento.getCodigo());
        assertEquals(1, sistema.getDepartamentos().size());
    }

    @Test
    void registrarDepartamentoDuplicado() {
        sistema.registrarDepartamento("25", "Cundinamarca");

        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarDepartamento("25", "Otro")
        );
    }

    @Test
    void registrarDepartamentoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarDepartamento("", "Cundinamarca")
        );
    }

    @Test
    void registrarPartidoValido() {
        PartidoPolitico partido = sistema.registrarPartido(
            "PP-01",
            "Partido A"
        );

        assertEquals("PP-01", partido.getCodigo());
        assertEquals(1, sistema.getPartidos().size());
    }

    @Test
    void registrarPartidoDuplicado() {
        sistema.registrarPartido("PP-01", "Partido A");

        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarPartido("PP-01", "Partido B")
        );
    }

    @Test
    void registrarPartidoInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarPartido("PP-01", null)
        );
    }

    @Test
    void getProcesosVacio() {
        assertTrue(sistema.getProcesos().isEmpty());
    }

    @Test
    void getProcesosConDatos() {
        ProcesoElectoral proceso = crearProceso();

        assertSame(proceso, sistema.getProcesos().get(0));
    }

    @Test
    void getProcesosInmutable() {
        ProcesoElectoral proceso = crearProceso();

        assertThrows(UnsupportedOperationException.class, () ->
            sistema.getProcesos().add(proceso)
        );
    }

    @Test
    void getDepartamentosVacio() {
        assertTrue(sistema.getDepartamentos().isEmpty());
    }

    @Test
    void getDepartamentosConDatos() {
        Departamento departamento = sistema.registrarDepartamento(
            "25",
            "Cundinamarca"
        );

        assertSame(departamento, sistema.getDepartamentos().get(0));
    }

    @Test
    void getDepartamentosInmutable() {
        assertThrows(UnsupportedOperationException.class, () ->
            sistema
                .getDepartamentos()
                .add(new Departamento("25", "Cundinamarca"))
        );
    }

    @Test
    void getPartidosVacio() {
        assertTrue(sistema.getPartidos().isEmpty());
    }

    @Test
    void getPartidosConDatos() {
        PartidoPolitico partido = sistema.registrarPartido(
            "PP-01",
            "Partido A"
        );

        assertSame(partido, sistema.getPartidos().get(0));
    }

    @Test
    void getPartidosInmutable() {
        assertThrows(UnsupportedOperationException.class, () ->
            sistema.getPartidos().add(new PartidoPolitico("PP-01", "Partido A"))
        );
    }

    @Test
    void iniciarVotacionValida() {
        ProcesoElectoral proceso = crearProceso();

        sistema.iniciarVotacion("PE-01");

        assertEquals("EN_VOTACION", proceso.getEstado());
    }

    @Test
    void iniciarVotacionInvalida() {
        crearProceso();

        sistema.iniciarVotacion("PE-01");

        assertThrows(IllegalStateException.class, () ->
            sistema.iniciarVotacion("PE-01")
        );
    }

    @Test
    void iniciarEscrutinioValido() {
        ProcesoElectoral proceso = crearProceso();

        sistema.iniciarVotacion("PE-01");
        sistema.iniciarEscrutinio("PE-01");

        assertEquals("ESCRUTINIO", proceso.getEstado());
    }

    @Test
    void iniciarEscrutinioInvalido() {
        crearProceso();

        assertThrows(IllegalStateException.class, () ->
            sistema.iniciarEscrutinio("PE-01")
        );
    }

    @Test
    void finalizarProcesoValido() {
        ProcesoElectoral proceso = crearProceso();

        sistema.iniciarVotacion("PE-01");
        sistema.iniciarEscrutinio("PE-01");
        sistema.finalizarProceso("PE-01");

        assertEquals("FINALIZADO", proceso.getEstado());
    }

    @Test
    void finalizarProcesoInvalido() {
        crearProceso();

        assertThrows(IllegalStateException.class, () ->
            sistema.finalizarProceso("PE-01")
        );
    }

    @Test
    void registrarActaValida() {
        MesaVotacion mesa = crearMesa();

        Candidato candidato = new Candidato("1001", "Juan", 1);

        ActaElectoral acta = sistema.registrarActa(
            mesa,
            "ACT-01",
            List.of(new ResultadoElectoral(candidato, 100)),
            10,
            5
        );

        assertEquals(115L, acta.getTotalVotos());
        assertSame(acta, mesa.getActa());
    }

    @Test
    void registrarActaVacia() {
        MesaVotacion mesa = crearMesa();

        ActaElectoral acta = sistema.registrarActa(
            mesa,
            "ACT-01",
            List.of(),
            0,
            0
        );

        assertEquals(0L, acta.getTotalVotos());
    }

    @Test
    void registrarActaMesaNula() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarActa(null, "ACT-01", List.of(), 0, 0)
        );
    }

    @Test
    void registrarActaResultadosNulos() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarActa(crearMesa(), "ACT-01", null, 0, 0)
        );
    }

    @Test
    void registrarActaSuperaCenso() {
        MesaVotacion mesa = crearMesa();

        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarActa(mesa, "ACT-01", List.of(), 401, 0)
        );

        assertNull(mesa.getActa());
    }

    @Test
    void registrarActaDuplicada() {
        MesaVotacion mesa = crearMesa();

        sistema.registrarActa(mesa, "ACT-01", List.of(), 100, 0);

        assertThrows(IllegalStateException.class, () ->
            sistema.registrarActa(mesa, "ACT-02", List.of(), 200, 0)
        );
    }

    @Test
    void registrarIncidenteValido() {
        MesaVotacion mesa = crearMesa();

        NovedadElectoral novedad = sistema.registrarIncidente(
            "I-01",
            "Retraso",
            mesa
        );

        assertInstanceOf(Incidente.class, novedad);
        assertEquals(1, mesa.getNovedades().size());
    }

    @Test
    void registrarIncidenteInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarIncidente("", "Retraso", crearMesa())
        );
    }

    @Test
    void registrarReclamacionValida() {
        MesaVotacion mesa = crearMesa();

        NovedadElectoral novedad = sistema.registrarReclamacion(
            "R-01",
            "Revision",
            mesa
        );

        assertInstanceOf(Reclamacion.class, novedad);
        assertEquals(1, mesa.getNovedades().size());
    }

    @Test
    void registrarReclamacionInvalida() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.registrarReclamacion("R-01", "", crearMesa())
        );
    }

    @Test
    void consultarTotalVotosValido() {
        MesaVotacion mesa = crearMesa();

        sistema.registrarActa(mesa, "ACT-01", List.of(), 100, 0);

        assertEquals(100L, sistema.consultarTotalVotos(mesa));
    }

    @Test
    void consultarTotalVotosCero() {
        assertEquals(0L, sistema.consultarTotalVotos(crearMesa()));
    }

    @Test
    void consultarTotalVotosNulo() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.consultarTotalVotos(null)
        );
    }

    @Test
    void consultarParticipacionValida() {
        MesaVotacion mesa = crearMesa();

        sistema.registrarActa(mesa, "ACT-01", List.of(), 100, 0);

        assertEquals(25.0, sistema.consultarParticipacion(mesa), 0.001);
    }

    @Test
    void consultarParticipacionCensoCero() {
        assertEquals(
            0.0,
            sistema.consultarParticipacion(new MesaVotacion(1, 0))
        );
    }

    @Test
    void consultarParticipacionNula() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.consultarParticipacion(null)
        );
    }

    @Test
    void consultarAbstencionValida() {
        MesaVotacion mesa = crearMesa();

        sistema.registrarActa(mesa, "ACT-01", List.of(), 100, 0);

        assertEquals(75.0, sistema.consultarAbstencion(mesa), 0.001);
    }

    @Test
    void consultarAbstencionCensoCero() {
        assertEquals(0.0, sistema.consultarAbstencion(new MesaVotacion(1, 0)));
    }

    @Test
    void consultarAbstencionNula() {
        assertThrows(IllegalArgumentException.class, () ->
            sistema.consultarAbstencion(null)
        );
    }

    @Test
    void escenarioElectoralCompleto() {
        ProcesoElectoral proceso = crearProceso();

        Departamento departamento = sistema.registrarDepartamento(
            "25",
            "Cundinamarca"
        );

        Municipio municipio = new Municipio("25175", "Chia");

        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        MesaVotacion mesa = crearMesa();

        departamento.agregarMunicipio(municipio);
        municipio.agregarPuesto(puesto);
        puesto.agregarMesa(mesa);

        PartidoPolitico partido = sistema.registrarPartido(
            "PP-01",
            "Partido A"
        );

        Candidato candidato = new Candidato("1001", "Juan", 1);

        partido.agregarCandidato(candidato);

        mesa.asignarJurado(
            new AsignacionJurado(
                new Jurado("2001", "Maria"),
                RolJurado.PRESIDENTE
            )
        );

        sistema.iniciarVotacion("PE-01");

        sistema.registrarIncidente("I-01", "Retraso", mesa);

        sistema.iniciarEscrutinio("PE-01");

        sistema.registrarActa(
            mesa,
            "ACT-01",
            List.of(new ResultadoElectoral(candidato, 180)),
            10,
            10
        );

        sistema.registrarReclamacion("R-01", "Revision", mesa);

        sistema.finalizarProceso("PE-01");

        assertEquals("FINALIZADO", proceso.getEstado());
        assertEquals(200L, sistema.consultarTotalVotos(departamento));
        assertEquals(400L, departamento.getCensoTotal());
        assertEquals(50.0, sistema.consultarParticipacion(departamento), 0.001);
        assertEquals(50.0, sistema.consultarAbstencion(departamento), 0.001);
        assertEquals(2, mesa.getNovedades().size());
        assertEquals(1, mesa.getAsignaciones().size());
        assertEquals(1, partido.getCandidatos().size());
    }

    @Test
    void registrarProcesosDiferentes() {
        crearProceso();

        sistema.registrarProceso(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.of(2026, 3, 8)
        );

        assertEquals(2, sistema.getProcesos().size());
    }

    @Test
    void buscarSegundoProceso() {
        crearProceso();

        ProcesoElectoral segundo = sistema.registrarProceso(
            "PE-02",
            "Congreso",
            TipoEleccion.CONGRESO,
            LocalDate.of(2026, 3, 8)
        );

        assertSame(segundo, sistema.buscarProceso("PE-02"));
    }

    @Test
    void registrarDepartamentosDiferentes() {
        sistema.registrarDepartamento("25", "Cundinamarca");
        sistema.registrarDepartamento("05", "Antioquia");

        assertEquals(2, sistema.getDepartamentos().size());
    }

    @Test
    void registrarPartidosDiferentes() {
        sistema.registrarPartido("PP-01", "Partido A");
        sistema.registrarPartido("PP-02", "Partido B");

        assertEquals(2, sistema.getPartidos().size());
    }
}
