package co.edu.usc.voltacali;

public class CargadorVE {

    /* Atributos */
    private String fabricante;
    private int anioInstalacion;
    private int voltajeNominal;
    private TipoConector tipoConector;
    private TipoCargador tipoCargador;
    private int numeroConectores;
    private int puestosParqueo;
    private double potenciaMaxima;
    private Ubicacion ubicacion;
    private double potenciaActual;

    /* Enums */
    public enum TipoConector {
        TIPO1,
        TIPO2
    }

    public enum TipoCargador {
        LENTO,
        RAPIDO,
        ULTRARAPIDO
    }

    public enum Ubicacion {
        NORTE,
        SUR,
        CENTRO,
        OESTE
    }

    /* Constructor */
    public CargadorVE(String fabricante, int anioInstalacion, int voltajeNominal, TipoConector tipoConector, TipoCargador tipoCargador, int numeroConectores, int puestosParqueo, double potenciaMaxima, double potenciaActual, Ubicacion ubicacion) {
        this.fabricante = fabricante;
        this.anioInstalacion = anioInstalacion;
        this.voltajeNominal = voltajeNominal;
        this.tipoConector = tipoConector;
        this.tipoCargador = tipoCargador;
        this.numeroConectores = numeroConectores;
        this.puestosParqueo = puestosParqueo;
        this.potenciaMaxima = potenciaMaxima;
        this.potenciaActual = potenciaActual;
        this.ubicacion = ubicacion;
    }
}