package co.edu.usc.voltacali;

import java.util.Vector;

/**
 * Representa un cargador de vehiculos electricos y gestiona sus operacines de potencia y bitacora.
 */
public class CargadorVE {

    /** Limite de potencia contratada por la red. */
    public static final double LIMITE_RED = 50.0;
    /** Incremento usado por defecto al aumentar potencia. */
    public static final double INCREMENTO_DEFECTO = 5.0;

    // Constantes para evitar números mágicos (MagicNumber)
    /** Voltaje nominal del constructor simplificado. */
    private static final int VOLTAJE_POR_DEFECTO = 220;
    /** Cantidad de conectores del constructor simplificado. */
    private static final int CONECTORES_POR_DEFECTO = 1;
    /** Cantidad de puestos del constructor simplificado. */
    private static final int PUESTOS_POR_DEFECTO = 1;
    /** Cantidad de minutos de una hora. */
    private static final double MINUTOS_EN_HORA = 60.0;

    /** Total de cargadores creados. */
    private static int totalCargadores;
    /** Consecutivo gloval de registros. */
    private static int contadorRegistros;

    /** Fabricante del cargador. */
    private String fabricante;
    /** Año de instalación. */
    private int anioInstalacion;
    /** Voltaje nominal. */
    private int voltajeNominal;
    /** Tipo de conector. */
    private TipoConector tipoConector;
    /** Tipo de cargador. */
    private TipoCargador tipoCargador;
    /** Número de conectores. */
    private int numeroConectores;
    /** Puestos de parqueo atendidos. */
    private int puestosParqueo;
    /** Potencia máxima del cargador. */
    private double potenciaMaxima;
    /** Ubicación física. */
    private Ubicacion ubicacion;
    /** Potencia actualmente suministrada. */
    private double potenciaActual;

    /** Registros de auditoria del cargador. */
    private final Vector<RegistroSesion> bitacora;

    /** Tipos de conector disponibles. */
    public enum TipoConector {
        /** Conector tipo 1. */
        TIPO_1,
        /** Conector tipo 2. */
        TIPO_2,
        /** Sistema de carga combinado 2. */
        CCS2,
        /** Conector CHAdeMO. */
        CHADEMO,
        /** Conector GB/T. */
        GBT
    }

    /** Tipos de cargador disponibles. */
    public enum TipoCargador {
        /** Cargador mural. */
        MURAL,
        /** Cargador de pedestal. */
        PEDESTAL,
        /** Cargador rápido de corriente directa. */
        RAPIDO_DC,
        /** Cargador ultrarrápido. */
        ULTRARRAPIDO,
        /** Cargador portátil. */
        PORTATIL,
        /** Cargador bidireccional. */
        BIDIRECCIONAL_V2G
    }

    /** Ubicaciones posibles del cargador. */
    public enum Ubicacion {
        /** Centro comercial. */
        CENTRO_COMERCIAL,
        /** Universidad. */
        UNIVERSIDAD,
        /** Estación de servicio. */
        ESTACION_SERVICIO,
        /** Parqueadero público. */
        PARQUEADERO_PUBLICO,
        /** Ubicación residencial. */
        RESIDENCIAL,
        /** Hotel. */
        HOTEL,
        /** Terminal. */
        TERMINAL,
        /** Flota corporativa. */
        FLOTA_CORPORATIVA
    }

    /**
     * Registro de sesión de auditoría para operaciones en el cargador.
     */
    public class RegistroSesion {
        /** Número consecutivo del registro. */
        private final int numeroConsecutivo;
        /** Descripción del evento. */
        private final String evento;
        /** Indica si la operación fue válida. */
        private final boolean valido;
        /** Fabricante al crear el registro. */
        private final String fabCargador;
        /** Año del cargador al crear el registro. */
        private final int anioCargador;
        /** Potencia del cargador al crear el registro. */
        private final double potCargador;

        /** Crea un registro con su evento y resultado. */
        public RegistroSesion(final String evento, final boolean valido) {
            contadorRegistros++;
            this.numeroConsecutivo = contadorRegistros;
            this.evento = evento;
            this.valido = valido;
            this.fabCargador = fabricante;
            this.anioCargador = anioInstalacion;
            this.potCargador = potenciaActual;
        }

        /** Indica si la operación registrada fue válida. */
        public boolean isValido() {
            return valido;
        }

        /** Obtiene la potencia registrada. */
        public double getPotCargador() {
            return potCargador;
        }

        /** Genera una descripción legible del registro. */
        public String describir() {
            return "#" + numeroConsecutivo + " [" + (valido ? "VALIDO" : "INVALIDO") + "] Evento: " + evento
                    + " | Cargadr: " + fabCargador + " (" + anioCargador + ") - Potencia: " + potCargador + " kW";
        }
    }

    /** Crea un cargador con todos sus datos de configuracion. */
    @SuppressWarnings("checkstyle:ParameterNumber")
    public CargadorVE(final String fabricante, final int anioInstalacion, final int voltajeNominal,
                      final TipoConector tipoConector, final TipoCargador tipoCargador,
                      final int numeroConectores, final int puestosParqueo, final double potenciaMaxima,
                      final Ubicacion ubicacion) {
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

    /** Crea un cargador con valores predeterminados. */
    public CargadorVE(final String fabricante, final int anioInstalacion, final double potenciaMaxima) {
        this(fabricante, anioInstalacion, VOLTAJE_POR_DEFECTO, TipoConector.TIPO_2, TipoCargador.PEDESTAL,
                CONECTORES_POR_DEFECTO, PUESTOS_POR_DEFECTO, potenciaMaxima, Ubicacion.PARQUEADERO_PUBLICO);
    }

    /** Crea una copia de otro cargador. */
    public CargadorVE(final CargadorVE otro) {
        this(otro.fabricante, otro.anioInstalacion, otro.voltajeNominal, otro.tipoConector,
                otro.tipoCargador, otro.numeroConectores, otro.puestosParqueo, otro.potenciaMaxima, otro.ubicacion);
    }

    /** Obtiene el fabricante. */
    public String getFabricante() {
        return fabricante;
    }

    /** Actualiza el fabricante. */
    public void setFabricante(final String fabricante) {
        this.fabricante = fabricante;
    }

    /** Obtiene el año de instalación. */
    public int getAnioInstalacion() {
        return anioInstalacion;
    }

    /** Actualiza el año de instalación. */
    public void setAnioInstalacion(final int anioInstalacion) {
        this.anioInstalacion = anioInstalacion;
    }

    /** Obtiene el voltaje nominal. */
    public int getVoltajeNominal() {
        return voltajeNominal;
    }

    /** Actualiza el voltaje nominal. */
    public void setVoltajeNominal(final int voltajeNominal) {
        this.voltajeNominal = voltajeNominal;
    }

    /** Obtiene el tipo de conector. */
    public TipoConector getTipoConector() {
        return tipoConector;
    }

    /** Actualiza el tipo de conector. */
    public void setTipoConector(final TipoConector tipoConector) {
        this.tipoConector = tipoConector;
    }

    /** Obtiene el tipo de cargador. */
    public TipoCargador getTipoCargador() {
        return tipoCargador;
    }

    /** Actualiza el tipo de cargador. */
    public void setTipoCargador(final TipoCargador tipoCargador) {
        this.tipoCargador = tipoCargador;
    }

    /** Obtiene el número de conectores. */
    public int getNumeroConectores() {
        return numeroConectores;
    }

    /** Actualiza el número de conectores. */
    public void setNumeroConectores(final int numeroConectores) {
        this.numeroConectores = numeroConectores;
    }

    /** Obtiene los puestos de parqueo. */
    public int getPuestosParqueo() {
        return puestosParqueo;
    }

    /** Actualiza los puestos de parqueo. */
    public void setPuestosParqueo(final int puestosParqueo) {
        this.puestosParqueo = puestosParqueo;
    }

    /** Obtiene la potencia máxima. */
    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    /** Actualiza la potencia máxima. */
    public void setPotenciaMaxima(final double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    /** Obtiene la ubicación. */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /** Actualiza la ubicación. */
    public void setUbicacion(final Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    /** Obtiene la potencia actual. */
    public double getPotenciaActual() {
        return potenciaActual;
    }

    /** Actualiza la potencia actual y registra el cambio. */
    public void setPotenciaActual(final double potenciaActual) {
        this.potenciaActual = potenciaActual;
        this.bitacora.add(new RegistroSesion("asignar potencia manual: " + potenciaActual, true));
    }

    /** Obtiene la bitácora de operaciones. */
    public Vector<RegistroSesion> getBitacora() {
        return bitacora;
    }

    /** Aumenta la potencia usando el incremento predeterminado. */
    public void aumentarPotencia() {
        aumentarPotencia(INCREMENTO_DEFECTO);
    }

    /** Aumenta la potencia en un incremento. */
    public void aumentarPotencia(final double incremento) {
        if (this.potenciaActual + incremento > this.potenciaMaxima) {
            System.out.println(
                    "error: No se puede aumentar la potencia. Superaría la maxima (" + this.potenciaMaxima + " kW).");
            this.bitacora.add(new RegistroSesion("Intento de aumento fallido (supera potencia maxima)", false));
        } else {
            this.potenciaActual += incremento;
            this.bitacora.add(new RegistroSesion("Aumento de potencia en " + incremento + " kw", true));
        }
    }

    /** Repite varias veces el aumento de potencia. */
    public void aumentarPotencia(final double incremento, final int veces) {
        for (int i = 0; i < veces; i++) {
            aumentarPotencia(incremento);
        }
    }

    /** Reduce la potencia en un decremento. */
    public void reducirPotencia(final double decremento) {
        if (this.potenciaActual - decremento < 0) {
            System.out.println("Error: No se puede reducir la potencia a valores negativos.");
            this.bitacora.add(new RegistroSesion("Intento de reducción fallido (potencia negativ)", false));
        } else {
            this.potenciaActual -= decremento;
            this.bitacora.add(new RegistroSesion("Reducción de potencia en " + decremento + " kw", true));
        }
    }

    /** Detiene la carga y rejistra la operacion. */
    public void cortarCarga() {
        this.potenciaActual = 0.0;
        this.bitacora.add(new RegistroSesion("carga cortada a 0.0 kw", true));
        System.out.println("carga cortada.");
    }

    /** Calcula el tiempo de carga con la potencia actual. */
    public double tiempoEstimadoCarga(final double energiaKWh) {
        if (this.potenciaActual <= 0) {
            System.out.println("crror: La potencia actual es 0 kw.");
            return -1.0;
        }
        return energiaKWh / this.potenciaActual;
    }

    /** Calcula el tiempo de carga con una potencia programada. */
    public double tiempoEstimadoCarga(final double energiaKWh, final double potenciaProgramada) {
        if (potenciaProgramada <= 0) {
            System.out.println("Error: La potencia programada es 0 kW o menor.");
            return -1.0;
        }
        return energiaKWh / potenciaProgramada;
    }

    /** kalkula el tiempo de carga incluyendo pausas. */
    public double tiempoEstimadoCarga(final double energiaKWh, final int pausas, final double minutosPorPausa) {
        final double tiempoBase = tiempoEstimadoCarga(energiaKWh);
        if (tiempoBase == -1.0) {
            return -1.0;
        }
        final double tiempoPausasHoras = (pausas * minutosPorPausa) / MINUTOS_EN_HORA;
        return tiempoBase + tiempoPausasHoras;
    }

    /** Cuenta los cargadores agrupados por tipo. */
    public static int[] contarPorTipo(final CargadorVE[] cargadores) {
        final int[] conteo = new int[TipoCargador.values().length];
        if (cargadores == null) {
            return conteo;
        }
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getTipoCargador() != null) {
                final int indice = cargador.getTipoCargador().ordinal();
                conteo[indice]++;
            }
        }
        return conteo;
    }

    /** Fltra cargadores por tipo de conector. */
    public static CargadorVE[] filtrar(final CargadorVE[] cargadores, final TipoConector conector) {
        if (cargadores == null) {
            return new CargadorVE[0];
        }
        int contador = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getTipoConector() == conector) {
                contador++;
            }
        }

        final CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getTipoConector() == conector) {
                resultado[pos] = cargador;
                pos++;
            }
        }
        return resultado;
    }

    /** Filtra cargadores por tipo de cargador. */
    public static CargadorVE[] filtrar(final CargadorVE[] cargadores, final TipoCargador tipo) {
        if (cargadores == null) {
            return new CargadorVE[0];
        }
        int contador = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getTipoCargador() == tipo) {
                contador++;
            }
        }

        final CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getTipoCargador() == tipo) {
                resultado[pos] = cargador;
                pos++;
            }
        }
        return resultado;
    }

    /** Filtra cargadores por ubicación. */
    public static CargadorVE[] filtrar(final CargadorVE[] cargadores, final Ubicacion ubicacion) {
        if (cargadores == null) {
            return new CargadorVE[0];
        }
        int contador = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getUbicacion() == ubicacion) {
                contador++;
            }
        }

        final CargadorVE[] resultado = new CargadorVE[contador];
        int pos = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null && cargador.getUbicacion() == ubicacion) {
                resultado[pos] = cargador;
                pos++;
            }
        }
        return resultado;
    }

    /** Obtiene el total de cargadores creados. */
    public static int getTotalCargadores() {
        return totalCargadores;
    }

    /** Obtiene el cargador con mayor potencia actual. */
    public static CargadorVE mayorPotencia(final CargadorVE[] cargadores) {
        if (cargadores == null) {
            return null;
        }
        CargadorVE mayor = null;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null) {
                if (mayor == null || cargador.getPotenciaActual() > mayor.getPotenciaActual()) {
                    mayor = cargador;
                }
            }
        }
        return mayor;
    }

    /** Calcula el promedio de potencia actual. */
    public static double promedioPotencia(final CargadorVE[] cargadores) {
        if (cargadores == null) {
            return 0.0;
        }
        double suma = 0.0;
        int contador = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null) {
                suma += cargador.getPotenciaActual();
                contador++;
            }
        }
        if (contador == 0) {
            return 0.0;
        }
        return suma / contador;
    }

    /** Cuenta registros que superan el límite contratado. */
    public static int excesosDePotenciaContratada(final CargadorVE[] cargadores) {
        if (cargadores == null) {
            return 0;
        }
        int conteoExcesos = 0;
        for (final CargadorVE cargador : cargadores) {
            if (cargador != null) {
                final Vector<RegistroSesion> listaBitacora = cargador.getBitacora();
                for (final RegistroSesion reg : listaBitacora) {
                    if (reg.isValido() && reg.getPotCargador() > LIMITE_RED) {
                        conteoExcesos++;
                    }
                }
            }
        }
        return conteoExcesos;
    }

    /** Muestra un resumen del cargador. */
    public void mostrar() {
        mostrar(false);
    }

    /** Muestra la información del cargador, opcionalmente con su bitácora. */
    public void mostrar(final boolean detallado) {
        System.out.println("=== CargadorVE ===");
        System.out.println("fabricante: " + fabricante);
        System.out.println("año Instalación: " + anioInstalacion);
        System.out.println("voltage Nominal: " + voltajeNominal + " V");
        System.out.println("tipo Conector: " + tipoConector);
        System.out.println("tipo Cargador: " + tipoCargador);
        System.out.println("Numero Conectores: " + numeroConectores);
        System.out.println("puestos parqueo: " + puestosParqueo);
        System.out.println("potencia maxima: " + potenciaMaxima + " kW");
        System.out.println("potencia Actual: " + potenciaActual + " kW");
        System.out.println("Ubicación: " + ubicacion);

        if (detallado) {
            System.out.println("--- Bitcora ---");
            if (bitacora.isEmpty()) {
                System.out.println("(Sin registros)");
            } else {
                for (final RegistroSesion reg : bitacora) {
                    System.out.println(reg.describir());
                }
            }
        }
    }
}
