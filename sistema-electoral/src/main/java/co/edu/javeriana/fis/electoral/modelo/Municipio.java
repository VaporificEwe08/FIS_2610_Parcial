package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class Municipio {

    private final String codigo;
    private final String nombre;
    private final List<PuestoVotacion> puestos;

    public Municipio(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.puestos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<PuestoVotacion> getPuestos() {
        return List.copyOf(puestos);
    }

    public void agregarPuesto(PuestoVotacion puesto) {
        if (puesto == null) {
            throw new IllegalArgumentException("Puesto invalido");
        }

        for (PuestoVotacion existente : puestos) {
            if (existente.getCodigo().equals(puesto.getCodigo())) {
                throw new IllegalArgumentException("Puesto duplicado");
            }
        }

        puestos.add(puesto);
    }

    public boolean eliminarPuesto(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        return puestos.removeIf(puesto -> puesto.getCodigo().equals(codigo));
    }
}
