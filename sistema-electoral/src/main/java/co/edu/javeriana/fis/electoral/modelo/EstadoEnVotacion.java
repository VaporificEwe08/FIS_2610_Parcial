package co.edu.javeriana.fis.electoral.modelo;

public class EstadoEnVotacion implements EstadoProceso {

    @Override
    public String getNombre() {
        return "EN_VOTACION";
    }

    @Override
    public void iniciarEscrutinio(ProcesoElectoral proceso) {
        proceso.cambiarEstado(new EstadoEscrutinio());
    }
}
