package co.edu.javeriana.fis.electoral.modelo;

public class Incidente extends NovedadElectoral {

    public Incidente(String codigo, String descripcion, MesaVotacion mesa) {
        super(codigo, descripcion, mesa);
    }

    @Override
    public String getTipo() {
        return "INCIDENTE";
    }
}
