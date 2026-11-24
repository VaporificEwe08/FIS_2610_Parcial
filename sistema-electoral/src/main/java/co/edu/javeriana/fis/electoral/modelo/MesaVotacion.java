package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class MesaVotacion implements ComponenteElectoral {

    private final int numero;
    private final int censoElectoral;
    private final List<AsignacionJurado> asignaciones;
    private ActaElectoral acta;
    private final List<NovedadElectoral> novedades = new ArrayList<>();

    public MesaVotacion(int numero, int censoElectoral) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero de mesa invalido");
        }

        if (censoElectoral < 0) {
            throw new IllegalArgumentException("Censo electoral invalido");
        }

        this.numero = numero;
        this.censoElectoral = censoElectoral;
        this.asignaciones = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getCensoElectoral() {
        return censoElectoral;
    }

    public List<AsignacionJurado> getAsignaciones() {
        return List.copyOf(asignaciones);
    }

    public void asignarJurado(AsignacionJurado asignacion) {
        if (asignacion == null) {
            throw new IllegalArgumentException("Asignacion invalida");
        }

        for (AsignacionJurado existente : asignaciones) {
            if (
                existente
                    .getJurado()
                    .getDocumento()
                    .equals(asignacion.getJurado().getDocumento())
            ) {
                throw new IllegalArgumentException("Jurado duplicado");
            }

            if (
                existente.getRol() == asignacion.getRol() &&
                asignacion.getRol() != RolJurado.VOCAL
            ) {
                throw new IllegalArgumentException("Rol ocupado");
            }
        }

        asignaciones.add(asignacion);
    }

    public boolean eliminarJurado(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Documento invalido");
        }

        return asignaciones.removeIf(asignacion ->
            asignacion.getJurado().getDocumento().equals(documento)
        );
    }

    public ActaElectoral getActa() {
        return acta;
    }

    public void registrarActa(ActaElectoral acta) {
        if (acta == null) {
            throw new IllegalArgumentException("Acta invalida");
        }

        if (this.acta != null) {
            throw new IllegalStateException("La mesa ya tiene un acta");
        }

        if (acta.getTotalVotos() > censoElectoral) {
            throw new IllegalArgumentException(
                "Los votos superan el censo electoral"
            );
        }

        this.acta = acta;
    }

    public List<NovedadElectoral> getNovedades() {
        return List.copyOf(novedades);
    }

    public void registrarNovedad(NovedadElectoral novedad) {
        if (novedad == null) {
            throw new IllegalArgumentException("Novedad invalida");
        }

        if (novedad.getMesa() != this) {
            throw new IllegalArgumentException(
                "La novedad pertenece a otra mesa"
            );
        }

        for (NovedadElectoral existente : novedades) {
            if (existente.getCodigo().equals(novedad.getCodigo())) {
                throw new IllegalArgumentException("Novedad duplicada");
            }
        }

        novedades.add(novedad);
    }

    @Override
    public long getTotalVotos() {
        return acta == null ? 0 : acta.getTotalVotos();
    }

    @Override
    public long getCensoTotal() {
        return censoElectoral;
    }
}
