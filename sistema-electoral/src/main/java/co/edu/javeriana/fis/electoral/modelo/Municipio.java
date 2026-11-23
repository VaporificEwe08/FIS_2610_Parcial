package co.edu.javeriana.fis.electoral.modelo;

public class Municipio {

    private final String codigo;
    private final String nombre;

    public Municipio(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
}
