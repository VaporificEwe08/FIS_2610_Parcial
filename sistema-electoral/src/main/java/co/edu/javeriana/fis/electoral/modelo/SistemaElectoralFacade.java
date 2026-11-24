package co.edu.javeriana.fis.electoral.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SistemaElectoralFacade {

    private final List<ProcesoElectoral> procesos = new ArrayList<>();
    private final List<Departamento> departamentos = new ArrayList<>();
    private final List<PartidoPolitico> partidos = new ArrayList<>();

    public ProcesoElectoral registrarProceso(
        String id,
        String nombre,
        TipoEleccion tipo,
        LocalDate fecha
    ) {
        ProcesoElectoral proceso = new ProcesoElectoral(
            id,
            nombre,
            tipo,
            fecha
        );

        for (ProcesoElectoral existente : procesos) {
            if (existente.getId().equals(id)) {
                throw new IllegalArgumentException("Proceso duplicado");
            }
        }

        procesos.add(proceso);

        return proceso;
    }

    public ProcesoElectoral buscarProceso(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID invalido");
        }

        for (ProcesoElectoral proceso : procesos) {
            if (proceso.getId().equals(id)) {
                return proceso;
            }
        }

        throw new IllegalArgumentException("Proceso no encontrado");
    }

    public Departamento registrarDepartamento(String codigo, String nombre) {
        Departamento departamento = new Departamento(codigo, nombre);

        for (Departamento existente : departamentos) {
            if (existente.getCodigo().equals(codigo)) {
                throw new IllegalArgumentException("Departamento duplicado");
            }
        }

        departamentos.add(departamento);

        return departamento;
    }

    public PartidoPolitico registrarPartido(String codigo, String nombre) {
        PartidoPolitico partido = new PartidoPolitico(codigo, nombre);

        for (PartidoPolitico existente : partidos) {
            if (existente.getCodigo().equals(codigo)) {
                throw new IllegalArgumentException("Partido duplicado");
            }
        }

        partidos.add(partido);

        return partido;
    }

    public List<ProcesoElectoral> getProcesos() {
        return List.copyOf(procesos);
    }

    public List<Departamento> getDepartamentos() {
        return List.copyOf(departamentos);
    }

    public List<PartidoPolitico> getPartidos() {
        return List.copyOf(partidos);
    }

    public void iniciarVotacion(String id) {
        buscarProceso(id).iniciarVotacion();
    }

    public void iniciarEscrutinio(String id) {
        buscarProceso(id).iniciarEscrutinio();
    }

    public void finalizarProceso(String id) {
        buscarProceso(id).finalizar();
    }

    public ActaElectoral registrarActa(
        MesaVotacion mesa,
        String codigo,
        List<ResultadoElectoral> resultados,
        int votosBlanco,
        int votosNulos
    ) {
        if (mesa == null || resultados == null) {
            throw new IllegalArgumentException("Datos invalidos");
        }

        ActaElectoral.Builder builder = new ActaElectoral.Builder(codigo)
            .votosBlanco(votosBlanco)
            .votosNulos(votosNulos);

        for (ResultadoElectoral resultado : resultados) {
            builder.agregarResultado(resultado);
        }

        ActaElectoral acta = builder.build();

        mesa.registrarActa(acta);

        return acta;
    }

    public NovedadElectoral registrarIncidente(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        return new FabricaIncidente().registrar(codigo, descripcion, mesa);
    }

    public NovedadElectoral registrarReclamacion(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        return new FabricaReclamacion().registrar(codigo, descripcion, mesa);
    }

    public long consultarTotalVotos(ComponenteElectoral componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente invalido");
        }

        return componente.getTotalVotos();
    }

    public double consultarParticipacion(ComponenteElectoral componente) {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaParticipacion()
        );

        return analizador.analizar(componente);
    }

    public double consultarAbstencion(ComponenteElectoral componente) {
        AnalizadorElectoral analizador = new AnalizadorElectoral(
            new EstrategiaAbstencion()
        );

        return analizador.analizar(componente);
    }
}
