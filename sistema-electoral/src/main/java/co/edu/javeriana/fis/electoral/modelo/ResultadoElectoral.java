package co.edu.javeriana.fis.electoral.modelo;

public class ResultadoElectoral {

    private final Candidato candidato;
    private final int votos;

    public ResultadoElectoral(Candidato candidato, int votos) {
        if (candidato == null) {
            throw new IllegalArgumentException("Candidato invalido");
        }

        if (votos < 0) {
            throw new IllegalArgumentException("Cantidad de votos invalida");
        }

        this.candidato = candidato;
        this.votos = votos;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public int getVotos() {
        return votos;
    }
}