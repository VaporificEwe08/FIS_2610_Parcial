package co.edu.javeriana.fis.electoral.modelo;

import java.util.ArrayList;
import java.util.List;

public class PartidoPolitico {

    private final String codigo;
    private final String nombre;
    private final List<Candidato> candidatos;

    public PartidoPolitico(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre invalido");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.candidatos = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Candidato> getCandidatos() {
        return List.copyOf(candidatos);
    }

    public void agregarCandidato(Candidato candidato) {
        if (candidato == null) {
            throw new IllegalArgumentException("Candidato invalido");
        }

        for (Candidato existente : candidatos) {
            if (existente.getDocumento().equals(candidato.getDocumento())) {
                throw new IllegalArgumentException("Candidato duplicado");
            }

            if (
                existente.getNumeroTarjeton() == candidato.getNumeroTarjeton()
            ) {
                throw new IllegalArgumentException(
                    "Numero de tarjeton duplicado"
                );
            }
        }

        candidatos.add(candidato);
    }

    public boolean eliminarCandidato(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Documento invalido");
        }

        return candidatos.removeIf(candidato ->
            candidato.getDocumento().equals(documento)
        );
    }
}
