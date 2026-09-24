package co.edu.usc.voltacali;

public class CargadorVE {

    public static final double INCREMENTO_DEFECTO = 1.0;

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
    private String bitacora;

    /* ENUMS */

    public enum TipoConector {
        TIPO_1,
        TIPO_2,
        CCS2,
        CHADEMO,
        GBT
    }

    public enum TipoCargador {
        MURAL,
        PEDESTAL,
        RAPIDO_DC,
        ULTRARRAPIDO,
        PORTATIL,
        BIDIRECCIONAL_V2G
    }

    public enum Ubicacion {
        CENTRO_COMERCIAL,
        UNIVERSIDAD,
        ESTACION_SERVICIO,
        PARQUEADERO_PUBLICO,
        RESIDENCIAL,
        HOTEL,
        TERMINAL,
        FLOTA_CORPORATIVA
    }

    /* CONSTRUCTORES */

    public CargadorVE(String fabricante, int anioInstalacion, int voltajeNominal, TipoConector tipoConector,
            TipoCargador tipoCargador, int numeroConectores, int puestosParqueo, double potenciaMaxima,
            Ubicacion ubicacion) {
        this.fabricante = fabricante;
        this.anioInstalacion = anioInstalacion;
        this.voltajeNominal = voltajeNominal;
        this.tipoConector = tipoConector;
        this.tipoCargador = tipoCargador;
        this.numeroConectores = numeroConectores;
        this.puestosParqueo = puestosParqueo;
        this.potenciaMaxima = potenciaMaxima;
        this.potenciaActual = 0.0;
        this.ubicacion = ubicacion;
        this.bitacora = "";
    }

    public CargadorVE(String fabricante, int anioInstalacion, double potenciaMaxima) {
        this(fabricante, anioInstalacion, 220, TipoConector.TIPO_2, TipoCargador.PEDESTAL, 1, 1, potenciaMaxima, Ubicacion.PARQUEADERO_PUBLICO);
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
        this.potenciaActual = 0.0;
        this.ubicacion = otro.ubicacion;
        this.bitacora = "";
    }

    /* GETTERS Y SETTERS */

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

    public String getBitacora() {
        return bitacora;
    }

    /* PARTE B: COMPORTAMIENTO BASE Y MÉTODOS DE POTENCIA */

    public void aumentarPotencia() {
        aumentarPotencia(INCREMENTO_DEFECTO);
    }

    public void aumentarPotencia(double incremento) {
        if (this.potenciaActual + incremento > this.potenciaMaxima) {
            System.out.println("Error: No se puede aumentar la potencia. Superaría la potencia máxima permida (" + this.potenciaMaxima + " kW).");
        } else {
            this.potenciaActual += incremento;
        }
    }

    public void aumentarPotencia(double incremento, int veces) {
        double totalIncremento = incremento * veces;
        if (this.potenciaActual + totalIncremento > this.potenciaMaxima) {
            System.out.println("Error: No se puede aplicar el incremento acumulado. Superaría la potencia máxima permida (" + this.potenciaMaxima + " kW).");
        } else {
            this.potenciaActual += totalIncremento;
        }
    }

    public void reducirPotencia(double decremento) {
        if (this.potenciaActual - decremento < 0) {
            System.out.println("Error: No se puede reducir la potencia. El resultado no puede ser negativo.");
        } else {
            this.potenciaActual -= decremento;
        }
    }

    public void cortarCarga() {
        this.potenciaActual = 0.0;
        System.out.println("Carga cortada. Potencia actual establecida en 0.0 kW.");
    }

    /* TIEMPO ESTIMADO CARGA (SOBRECARGAS) */

    public double tiempoEstimadoCarga(double energiaKWh) {
        if (this.potenciaActual <= 0) {
            System.out.println("Error: La potencia actual es 0 kW. No se puede calcular el tiempo estimado.");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }

    public double tiempoEstimadoCarga(double energiaKWh, double potenciaProgramada) {
        if (potenciaProgramada <= 0) {
            System.out.println("Error: La potencia programada es 0 kW o menor. No se puede calcular el tiempo estimado.");
            return -1.0;
        }
        return energiaKWh / potenciaProgramada;
    }

    public double tiempoEstimadoCarga(double energiaKWh, int pausas, double minutosPorPausa) {
        double tiempoBase = tiempoEstimadoCarga(energiaKWh);
        if (tiempoBase == -1.0) {
            return -1.0;
        }
        double tiempoPausasHoras = (pausas * minutosPorPausa) / 60.0;
        return tiempoBase + tiempoPausasHoras;
    }

    /* MÉTODOS ESTÁTICOS FILTRAR */

    public static CargadorVE[] filtrar(CargadorVE[] cargadores, TipoConector conector) {
        int contador = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getTipoConector() == conector) {
                contador++;
            }
        }

        CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getTipoConector() == conector) {
                resultado[pos] = cargadores[i];
                pos++;
            }
        }
        return resultado;
    }

    public static CargadorVE[] filtrar(CargadorVE[] cargadores, TipoCargador tipo) {
        int contador = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getTipoCargador() == tipo) {
                contador++;
            }
        }

        CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getTipoCargador() == tipo) {
                resultado[pos] = cargadores[i];
                pos++;
            }
        }
        return resultado;
    }

    public static CargadorVE[] filtrar(CargadorVE[] cargadores, Ubicacion ubicacion) {
        int contador = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getUbicacion() == ubicacion) {
                contador++;
            }
        }

        CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getUbicacion() == ubicacion) {
                resultado[pos] = cargadores[i];
                pos++;
            }
        }
        return resultado;
    }

    /* MÉTODOS MOSTRAR */

    public void mostrar() {
        mostrar(false);
    }

    public void mostrar(boolean detallado) {
        System.out.println("=== CargadorVE ===");
        System.out.println("Fabricante: " + fabricante);
        System.out.println("Año Instalación: " + anioInstalacion);
        System.out.println("Voltaje Nominal: " + voltajeNominal + " V");
        System.out.println("Tipo Conector: " + tipoConector);
        System.out.println("Tipo Cargador: " + tipoCargador);
        System.out.println("Número Conectores: " + numeroConectores);
        System.out.println("Puestos Parqueo: " + puestosParqueo);
        System.out.println("Potencia Máxima: " + potenciaMaxima + " kW");
        System.out.println("Potencia Actual: " + potenciaActual + " kW");
        System.out.println("Ubicación: " + ubicacion);

        if (detallado) {
            System.out.println("Bitácora: " + (bitacora.isEmpty() ? "(Sin registros)" : bitacora));
        }
    }
}