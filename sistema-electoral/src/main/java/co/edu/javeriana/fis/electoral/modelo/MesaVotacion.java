package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class MesaVotacion {

    private final int numero;
    private final int censoElectoral;
    private final List<AsignacionJurado> asignaciones;
    private ActaElectoral acta;

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
}