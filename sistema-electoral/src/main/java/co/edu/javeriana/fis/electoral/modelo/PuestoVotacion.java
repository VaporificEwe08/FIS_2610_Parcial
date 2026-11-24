package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class PuestoVotacion implements ComponenteElectoral {

    private final String codigo;
    private final String nombre;
    private final String direccion;
    private final List<MesaVotacion> mesas;

    public PuestoVotacion(String codigo, String nombre, String direccion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException("Direccion invalida");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.mesas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<MesaVotacion> getMesas() {
        return List.copyOf(mesas);
    }

    public void agregarMesa(MesaVotacion mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa invalida");
        }

        for (MesaVotacion existente : mesas) {
            if (existente.getNumero() == mesa.getNumero()) {
                throw new IllegalArgumentException("Mesa duplicada");
            }
        }

        mesas.add(mesa);
    }

    public boolean eliminarMesa(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero invalido");
        }

        return mesas.removeIf(mesa -> mesa.getNumero() == numero);
    }

    @Override
    public long getTotalVotos() {
        long total = 0;

        for (MesaVotacion mesa : mesas) {
            total += mesa.getTotalVotos();
        }

        return total;
    }

    @Override
    public long getCensoTotal() {
        long total = 0;

        for (MesaVotacion mesa : mesas) {
            total += mesa.getCensoTotal();
        }

        return total;
    }
}
