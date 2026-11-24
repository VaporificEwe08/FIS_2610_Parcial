package co.edu.javeriana.fis.electoral.modelo;

public class AsignacionJurado {

    private final Jurado jurado;
    private final RolJurado rol;

    public AsignacionJurado(Jurado jurado, RolJurado rol) {
        if (jurado == null) {
            throw new IllegalArgumentException("Jurado invalido");
        }

        if (rol == null) {
            throw new IllegalArgumentException("Rol invalido");
        }

        this.jurado = jurado;
        this.rol = rol;
    }

    public Jurado getJurado() {
        return jurado;
    }

    public RolJurado getRol() {
        return rol;
    }
}
