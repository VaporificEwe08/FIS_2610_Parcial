package co.edu.javeriana.fis.electoral.modelo;

public class EstrategiaAbstencion implements EstrategiaAnalisis {

    @Override
    public double analizar(ComponenteElectoral componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente invalido");
        }

        long censo = componente.getCensoTotal();

        if (censo == 0) {
            return 0;
        }

        return ((censo - componente.getTotalVotos()) * 100.0) / censo;
    }
}
