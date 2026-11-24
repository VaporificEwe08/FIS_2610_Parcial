package co.edu.javeriana.fis.electoral.modelo;

public class AnalizadorElectoral {

    private EstrategiaAnalisis estrategia;

    public AnalizadorElectoral(EstrategiaAnalisis estrategia) {
        cambiarEstrategia(estrategia);
    }

    public EstrategiaAnalisis getEstrategia() {
        return estrategia;
    }

    public void cambiarEstrategia(EstrategiaAnalisis estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("Estrategia invalida");
        }

        this.estrategia = estrategia;
    }

    public double analizar(ComponenteElectoral componente) {
        if (componente == null) {
            throw new IllegalArgumentException("Componente invalido");
        }

        return estrategia.analizar(componente);
    }
}
