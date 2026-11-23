package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class Departamento {

    private final String codigo;
    private final String nombre;
    private final List<Municipio> municipios;

    public Departamento(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.municipios = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Municipio> getMunicipios() {
        return List.copyOf(municipios);
    }

    public void agregarMunicipio(Municipio municipio) {
        if (municipio == null) {
            throw new IllegalArgumentException("Municipio invalido");
        }

        for (Municipio existente : municipios) {
            if (existente.getCodigo().equals(municipio.getCodigo())) {
                throw new IllegalArgumentException("Municipio duplicado");
            }
        }

        municipios.add(municipio);
    }

    public boolean eliminarMunicipio(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        return municipios.removeIf(municipio ->
            municipio.getCodigo().equals(codigo)
        );
    }
}
