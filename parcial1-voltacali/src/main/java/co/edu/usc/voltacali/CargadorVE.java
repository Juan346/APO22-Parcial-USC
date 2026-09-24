package co.edu.usc.voltacali;

public class CargadorVE {

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

    public CargadorVE(String fabricante, int anioInstalacion, int voltajeNominal, TipoConector tipoConector,
            TipoCargador tipoCargador, int numeroConectores, int puestosParqueo, double potenciaMaxima,
            double potenciaActual, Ubicacion ubicacion) {
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

    public CargadorVE(String fabricante, int anioInstalacion, double potenciaMaxima) {
        this.fabricante = fabricante;
        this.anioInstalacion = anioInstalacion;
        this.potenciaMaxima = potenciaMaxima;
        this.voltajeNominal = 220;
        this.tipoConector = TipoConector.TIPO1;
        this.tipoCargador = TipoCargador.LENTO;
        this.numeroConectores = 1;
        this.puestosParqueo = 1;
        this.potenciaActual = 0.0;
        this.ubicacion = Ubicacion.NORTE;
    }

    public CargadorVE(CargadorVE otro) {
        this.fabricante = otro.fabricante;
        this.anioInstalacion = otro.anioInstalacion;
        this.voltajeNominal = otro.voltajeNominal;
        this.tipoConector = otro.tipoConector;
        this.tipoCargador = otro.tipoCargador;
        this.numeroConectores = otro.numeroConectores;
        this.puestosParqueo = otro.puestosParqueo;
        this.potenciaMaxima = otro.potenciaMaxima;
        this.potenciaActual = otro.potenciaActual;
        this.ubicacion = otro.ubicacion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAnioInstalacion() {
        return anioInstalacion;
    }

    public void setAnioInstalacion(int anioInstalacion) {
        this.anioInstalacion = anioInstalacion;
    }

    public int getVoltajeNominal() {
        return voltajeNominal;
    }

    public void setVoltajeNominal(int voltajeNominal) {
        this.voltajeNominal = voltajeNominal;
    }

    public TipoConector getTipoConector() {
        return tipoConector;
    }

    public void setTipoConector(TipoConector tipoConector) {
        this.tipoConector = tipoConector;
    }

    public TipoCargador getTipoCargador() {
        return tipoCargador;
    }

    public void setTipoCargador(TipoCargador tipoCargador) {
        this.tipoCargador = tipoCargador;
    }

    public int getNumeroConectores() {
        return numeroConectores;
    }

    public void setNumeroConectores(int numeroConectores) {
        this.numeroConectores = numeroConectores;
    }

    public int getPuestosParqueo() {
        return puestosParqueo;
    }

    public void setPuestosParqueo(int puestosParqueo) {
        this.puestosParqueo = puestosParqueo;
    }

    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    public void setPotenciaMaxima(double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPotenciaActual() {
        return potenciaActual;
    }

    public void setPotenciaActual(double potenciaActual) {
        this.potenciaActual = potenciaActual;
    }
}