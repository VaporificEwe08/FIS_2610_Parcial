package co.edu.javeriana.fis.electoral.modelo;

public class FabricaIncidente extends FabricaNovedad {

    @Override
    protected NovedadElectoral crearNovedad(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        return new Incidente(codigo, descripcion, mesa);
    }
}
