package co.edu.javeriana.fis.electoral.modelo;

public class FabricaReclamacion extends FabricaNovedad {

    @Override
    protected NovedadElectoral crearNovedad(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        return new Reclamacion(codigo, descripcion, mesa);
    }
}
