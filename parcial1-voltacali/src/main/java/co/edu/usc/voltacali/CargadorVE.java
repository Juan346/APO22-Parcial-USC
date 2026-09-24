package co.edu.usc.voltacali;

import java.util.Vector;

public class CargadorVE {

    public static final double LIMITE_RED = 50.0;
    public static final double INCREMENTO_DEFECTO = 5.0;
    
    private static int totalCargadores = 0;
    private static int contadorRegistros = 0;

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
    
    private Vector<RegistroSesion> bitacora;

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

    public class RegistroSesion {
        private int numeroConsecutivo;
        private String evento;
        private boolean valido;
        private String fabCargador;
        private int anioCargador;
        private double potCargador;

        public RegistroSesion(String evento, boolean valido) {
            contadorRegistros++;
            this.numeroConsecutivo = contadorRegistros;
            this.evento = evento;
            this.valido = valido;
            this.fabCargador = fabricante;
            this.anioCargador = anioInstalacion;
            this.potCargador = potenciaActual;
        }

        public boolean isValido() {
            return valido;
        }

        public double getPotCargador() {
            return potCargador;
        }

        public String describir() {
            return "#" + numeroConsecutivo + " [" + (valido ? "VÁLIDO" : "INVÁLIDO") + "] Evento: " + evento 
                 + " | Cargador: " + fabCargador + " (" + anioCargador + ") - Potencia: " + potCargador + " kW";
        }
    }

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
        this.bitacora = new Vector<>();
        
        totalCargadores++;
    }

    public CargadorVE(String fabricante, int anioInstalacion, double potenciaMaxima) {
        this(fabricante, anioInstalacion, 220, TipoConector.TIPO_2, TipoCargador.PEDESTAL, 1, 1, potenciaMaxima, Ubicacion.PARQUEADERO_PUBLICO);
    }

    public CargadorVE(CargadorVE otro) {
        this(otro.fabricante, otro.anioInstalacion, otro.voltajeNominal, otro.tipoConector, 
             otro.tipoCargador, otro.numeroConectores, otro.puestosParqueo, otro.potenciaMaxima, otro.ubicacion);
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
        this.bitacora.add(new RegistroSesion("Asignar potencia manual: " + potenciaActual, true));
    }

    public Vector<RegistroSesion> getBitacora() {
        return bitacora;
    }

    public void aumentarPotencia() {
        aumentarPotencia(INCREMENTO_DEFECTO);
    }

    public void aumentarPotencia(double incremento) {
        if (this.potenciaActual + incremento > this.potenciaMaxima) {
            System.out.println("Error: No se puede aumentar la potencia. Superaría la máxima (" + this.potenciaMaxima + " kW).");
            this.bitacora.add(new RegistroSesion("Intento de aumento fallido (supera potencia máxima)", false));
        } else {
            this.potenciaActual += incremento;
            this.bitacora.add(new RegistroSesion("Aumento de potencia en " + incremento + " kW", true));
        }
    }

    public void aumentarPotencia(double incremento, int veces) {
        for (int i = 0; i < veces; i++) {
            aumentarPotencia(incremento);
        }
    }

    public void reducirPotencia(double decremento) {
        if (this.potenciaActual - decremento < 0) {
            System.out.println("Error: No se puede reducir la potencia a valores negativos.");
            this.bitacora.add(new RegistroSesion("Intento de reducción fallido (potencia negativa)", false));
        } else {
            this.potenciaActual -= decremento;
            this.bitacora.add(new RegistroSesion("Reducción de potencia en " + decremento + " kW", true));
        }
    }

    public void cortarCarga() {
        this.potenciaActual = 0.0;
        this.bitacora.add(new RegistroSesion("Carga cortada a 0.0 kW", true));
        System.out.println("Carga cortada.");
    }

    public double tiempoEstimadoCarga(double energiaKWh) {
        if (this.potenciaActual <= 0) {
            System.out.println("Error: La potencia actual es 0 kW.");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }

    public double tiempoEstimadoCarga(double energiaKWh, double potenciaProgramada) {
        if (potenciaProgramada <= 0) {
            System.out.println("Error: La potencia programada es 0 kW o menor.");
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

    public static int[] contarPorTipo(CargadorVE[] cargadores) {
        int[] conteo = new int[TipoCargador.values().length];
        if (cargadores == null) {
            return conteo;
        }
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null && cargadores[i].getTipoCargador() != null) {
                int indice = cargadores[i].getTipoCargador().ordinal();
                conteo[indice]++;
            }
        }
        return conteo;
    }

    public static CargadorVE[] filtrar(CargadorVE[] cargadores, TipoConector conector) {
        if (cargadores == null) {
            return new CargadorVE[0];
        }
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
        if (cargadores == null) {
            return new CargadorVE[0];
        }
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
        if (cargadores == null) {
            return new CargadorVE[0];
        }
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

    public static int getTotalCargadores() {
        return totalCargadores;
    }

    public static CargadorVE mayorPotencia(CargadorVE[] cargadores) {
        if (cargadores == null) {
            return null;
        }
        CargadorVE mayor = null;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null) {
                if (mayor == null || cargadores[i].getPotenciaActual() > mayor.getPotenciaActual()) {
                    mayor = cargadores[i];
                }
            }
        }
        return mayor;
    }

    public static double promedioPotencia(CargadorVE[] cargadores) {
        if (cargadores == null) {
            return 0.0;
        }
        double suma = 0.0;
        int contador = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null) {
                suma += cargadores[i].getPotenciaActual();
                contador++;
            }
        }
        if (contador == 0) {
            return 0.0;
        }
        return suma / contador;
    }

    public static int excesosDePotenciaContratada(CargadorVE[] cargadores) {
        if (cargadores == null) {
            return 0;
        }
        int conteoExcesos = 0;
        for (int i = 0; i < cargadores.length; i++) {
            if (cargadores[i] != null) {
                Vector<RegistroSesion> listaBitacora = cargadores[i].getBitacora();
                for (int j = 0; j < listaBitacora.size(); j++) {
                    RegistroSesion reg = listaBitacora.get(j);
                    if (reg.isValido() && reg.getPotCargador() > LIMITE_RED) {
                        conteoExcesos++;
                    }
                }
            }
        }
        return conteoExcesos;
    }

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
            System.out.println("--- Bitácora ---");
            if (bitacora.isEmpty()) {
                System.out.println("(Sin registros)");
            } else {
                for (int i = 0; i < bitacora.size(); i++) {
                    System.out.println(bitacora.get(i).describir());
                }
            }
        }
    }
}