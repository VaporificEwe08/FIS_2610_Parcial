package co.edu.javeriana.fis.electoral.modelo;

public class Candidato {

    private final String documento;
    private final String nombre;
    private final int numeroTarjeton;

    public Candidato(String documento, String nombre, int numeroTarjeton) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Documento invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        if (numeroTarjeton <= 0) {
            throw new IllegalArgumentException("Numero de tarjeton invalido");
        }

        this.documento = documento;
        this.nombre = nombre;
        this.numeroTarjeton = numeroTarjeton;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroTarjeton() {
        return numeroTarjeton;
    }
}
