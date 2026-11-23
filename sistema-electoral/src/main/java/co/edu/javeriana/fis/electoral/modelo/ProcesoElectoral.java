package co.edu.javeriana.fis.electoral.modelo;

import java.time.LocalDate;

public class ProcesoElectoral {

    private final String id;
    private final String nombre;
    private final TipoEleccion tipo;
    private LocalDate fecha;

    public ProcesoElectoral(
        String id,
        String nombre,
        TipoEleccion tipo,
        LocalDate fecha
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo invalido");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("Fecha invalida");
        }

        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoEleccion getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void cambiarFecha(LocalDate nuevaFecha) {
        if (nuevaFecha == null) {
            throw new IllegalArgumentException("Fecha invalida");
        }

        this.fecha = nuevaFecha;
    }
}
