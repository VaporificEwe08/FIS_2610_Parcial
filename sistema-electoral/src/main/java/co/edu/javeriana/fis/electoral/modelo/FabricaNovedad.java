package co.edu.javeriana.fis.electoral.modelo;

public abstract class FabricaNovedad {

    public NovedadElectoral registrar(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        NovedadElectoral novedad = crearNovedad(codigo, descripcion, mesa);

        mesa.registrarNovedad(novedad);

        return novedad;
    }

    protected abstract NovedadElectoral crearNovedad(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    );
}
