package co.edu.javeriana.fis.electoral.modelo;

public class Reclamacion extends NovedadElectoral {

    public Reclamacion(String codigo, String descripcion, MesaVotacion mesa) {
        super(codigo, descripcion, mesa);
    }

    @Override
    public String getTipo() {
        return "RECLAMACION";
    }
}
