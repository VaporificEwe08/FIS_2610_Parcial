package co.edu.javeriana.fis.electoral.modelo;

public interface EstadoProceso {
    String getNombre();

    default void iniciarVotacion(ProcesoElectoral proceso) {
        throw new IllegalStateException(
            "No se puede iniciar la votacion desde " + getNombre()
        );
    }

    default void iniciarEscrutinio(ProcesoElectoral proceso) {
        throw new IllegalStateException(
            "No se puede iniciar el escrutinio desde " + getNombre()
        );
    }

    default void finalizar(ProcesoElectoral proceso) {
        throw new IllegalStateException(
            "No se puede finalizar desde " + getNombre()
        );
    }
}
