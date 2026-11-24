package co.edu.javeriana.fis.electoral.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComponenteElectoralTest {

    private MesaVotacion crearMesa(int numero, int censo, int votos) {
        MesaVotacion mesa = new MesaVotacion(numero, censo);

        ActaElectoral acta = new ActaElectoral.Builder("ACT-" + numero)
            .votosBlanco(votos)
            .build();

        mesa.registrarActa(acta);

        return mesa;
    }

    private PuestoVotacion crearPuesto() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        puesto.agregarMesa(crearMesa(1, 300, 100));
        puesto.agregarMesa(crearMesa(2, 200, 50));

        return puesto;
    }

    private Municipio crearMunicipio() {
        Municipio municipio = new Municipio("25175", "Chia");

        municipio.agregarPuesto(crearPuesto());

        return municipio;
    }

    private Departamento crearDepartamento() {
        Departamento departamento = new Departamento("25", "Cundinamarca");

        departamento.agregarMunicipio(crearMunicipio());

        return departamento;
    }

    @Test
    void mesaTotalVotosSinActa() {
        MesaVotacion mesa = new MesaVotacion(1, 300);

        assertEquals(0L, mesa.getTotalVotos());
    }

    @Test
    void mesaTotalVotosConActa() {
        assertEquals(100L, crearMesa(1, 300, 100).getTotalVotos());
    }

    @Test
    void mesaCensoTotalCorrecto() {
        assertEquals(300L, new MesaVotacion(1, 300).getCensoTotal());
    }

    @Test
    void mesaCensoTotalCero() {
        assertEquals(0L, new MesaVotacion(1, 0).getCensoTotal());
    }

    @Test
    void puestoTotalVotosVacio() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        assertEquals(0L, puesto.getTotalVotos());
    }

    @Test
    void puestoTotalVotosConMesas() {
        assertEquals(150L, crearPuesto().getTotalVotos());
    }

    @Test
    void puestoCensoTotalVacio() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        assertEquals(0L, puesto.getCensoTotal());
    }

    @Test
    void puestoCensoTotalConMesas() {
        assertEquals(500L, crearPuesto().getCensoTotal());
    }

    @Test
    void municipioTotalVotosVacio() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertEquals(0L, municipio.getTotalVotos());
    }

    @Test
    void municipioTotalVotosConPuestos() {
        assertEquals(150L, crearMunicipio().getTotalVotos());
    }

    @Test
    void municipioCensoTotalVacio() {
        Municipio municipio = new Municipio("25175", "Chia");

        assertEquals(0L, municipio.getCensoTotal());
    }

    @Test
    void municipioCensoTotalConPuestos() {
        assertEquals(500L, crearMunicipio().getCensoTotal());
    }

    @Test
    void departamentoTotalVotosVacio() {
        Departamento departamento = new Departamento("25", "Cundinamarca");

        assertEquals(0L, departamento.getTotalVotos());
    }

    @Test
    void departamentoTotalVotosConMunicipios() {
        assertEquals(150L, crearDepartamento().getTotalVotos());
    }

    @Test
    void departamentoCensoTotalVacio() {
        Departamento departamento = new Departamento("25", "Cundinamarca");

        assertEquals(0L, departamento.getCensoTotal());
    }

    @Test
    void departamentoCensoTotalConMunicipios() {
        assertEquals(500L, crearDepartamento().getCensoTotal());
    }

    @Test
    void consultarComponentesPolimorficamente() {
        ComponenteElectoral[] componentes = {
            crearPuesto(),
            crearMunicipio(),
            crearDepartamento(),
        };

        for (ComponenteElectoral componente : componentes) {
            assertEquals(150L, componente.getTotalVotos());
            assertEquals(500L, componente.getCensoTotal());
        }
    }

    @Test
    void consolidarVariosMunicipios() {
        Departamento departamento = crearDepartamento();

        Municipio municipio = new Municipio("25899", "Zipaquira");

        PuestoVotacion puesto = new PuestoVotacion(
            "PV-02",
            "Escuela",
            "Calle 20"
        );

        puesto.agregarMesa(crearMesa(3, 400, 200));

        municipio.agregarPuesto(puesto);
        departamento.agregarMunicipio(municipio);

        assertEquals(350L, departamento.getTotalVotos());
        assertEquals(900L, departamento.getCensoTotal());
    }

    @Test
    void consolidarCensoSinDesbordamiento() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        puesto.agregarMesa(new MesaVotacion(1, Integer.MAX_VALUE));
        puesto.agregarMesa(new MesaVotacion(2, Integer.MAX_VALUE));

        assertEquals(4294967294L, puesto.getCensoTotal());
    }

    @Test
    void consolidarVotosSinDesbordamiento() {
        PuestoVotacion puesto = new PuestoVotacion(
            "PV-01",
            "Colegio",
            "Calle 10"
        );

        puesto.agregarMesa(crearMesa(1, Integer.MAX_VALUE, Integer.MAX_VALUE));

        puesto.agregarMesa(crearMesa(2, Integer.MAX_VALUE, Integer.MAX_VALUE));

        assertEquals(4294967294L, puesto.getTotalVotos());
    }
}
