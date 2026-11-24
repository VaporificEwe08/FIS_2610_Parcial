package co.edu.javeriana.fis.electoral.modelo;

public abstract class NovedadElectoral {

    private final String codigo;
    private final String descripcion;
    private final MesaVotacion mesa;

    protected NovedadElectoral(
        String codigo,
        String descripcion,
        MesaVotacion mesa
    ) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Descripcion invalida");
        }

        if (mesa == null) {
            throw new IllegalArgumentException("Mesa invalida");
        }

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.mesa = mesa;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public MesaVotacion getMesa() {
        return mesa;
    }

    public abstract String getTipo();
}
