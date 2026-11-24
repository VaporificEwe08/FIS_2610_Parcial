package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class ActaElectoral {

    private final String codigo;
    private final List<ResultadoElectoral> resultados;
    private final int votosBlanco;
    private final int votosNulos;

    private ActaElectoral(Builder builder) {
        this.codigo = builder.codigo;
        this.resultados = List.copyOf(builder.resultados);
        this.votosBlanco = builder.votosBlanco;
        this.votosNulos = builder.votosNulos;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<ResultadoElectoral> getResultados() {
        return resultados;
    }

    public int getVotosBlanco() {
        return votosBlanco;
    }

    public int getVotosNulos() {
        return votosNulos;
    }

    public long getTotalVotos() {
        long total = (long) votosBlanco + votosNulos;

        for (ResultadoElectoral resultado : resultados) {
            total += resultado.getVotos();
        }

        return total;
    }

    public static class Builder {

        private final String codigo;
        private final List<ResultadoElectoral> resultados;
        private int votosBlanco;
        private int votosNulos;

        public Builder(String codigo) {
            if (codigo == null || codigo.isBlank()) {
                throw new IllegalArgumentException("Codigo invalido");
            }

            this.codigo = codigo;
            this.resultados = new ArrayList<>();
        }

        public Builder agregarResultado(ResultadoElectoral resultado) {
            if (resultado == null) {
                throw new IllegalArgumentException("Resultado invalido");
            }

            for (ResultadoElectoral existente : resultados) {
                if (existente.getCandidato().getDocumento().equals(
                        resultado.getCandidato().getDocumento())) {
                    throw new IllegalArgumentException("Candidato duplicado");
                }
            }

            resultados.add(resultado);
            return this;
        }

        public Builder votosBlanco(int cantidad) {
            if (cantidad < 0) {
                throw new IllegalArgumentException("Votos en blanco invalidos");
            }

            this.votosBlanco = cantidad;
            return this;
        }

        public Builder votosNulos(int cantidad) {
            if (cantidad < 0) {
                throw new IllegalArgumentException("Votos nulos invalidos");
            }

            this.votosNulos = cantidad;
            return this;
        }

        public ActaElectoral build() {
            return new ActaElectoral(this);
        }
    }
}