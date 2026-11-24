package co.edu.javeriana.fis.electoral.modelo;

public class EstadoEscrutinio implements EstadoProceso {

    @Override
    public String getNombre() {
        return "ESCRUTINIO";
    }

    @Override
    public void finalizar(ProcesoElectoral proceso) {
        proceso.cambiarEstado(new EstadoFinalizado());
    }
}
