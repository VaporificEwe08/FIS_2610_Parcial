package co.edu.javeriana.fis.electoral.modelo;

public class MesaVotacion {

    private final int numero;
    private final int censoElectoral;

    public MesaVotacion(int numero, int censoElectoral) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero de mesa invalido");
        }

        if (censoElectoral < 0) {
            throw new IllegalArgumentException("Censo electoral invalido");
        }

        this.numero = numero;
        this.censoElectoral = censoElectoral;
    }

    public int getNumero() {
        return numero;
    }

    public int getCensoElectoral() {
        return censoElectoral;
    }
}
