package co.edu.javeriana.fis.electoral.modelo;

public class EstrategiaTotalVotos implements EstrategiaAnalisis {

    @Override
    public double analizar(ComponenteElectoral componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente invalido");
        }

        return componente.getTotalVotos();
    }
}
