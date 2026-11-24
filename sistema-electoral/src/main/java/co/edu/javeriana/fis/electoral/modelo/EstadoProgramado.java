package co.edu.javeriana.fis.electoral.modelo;

public class EstadoProgramado implements EstadoProceso {

    @Override
    public String getNombre() {
        return "PROGRAMADO";
    }

    @Override
    public void iniciarVotacion(ProcesoElectoral proceso) {
        proceso.cambiarEstado(new EstadoEnVotacion());
    }
}
