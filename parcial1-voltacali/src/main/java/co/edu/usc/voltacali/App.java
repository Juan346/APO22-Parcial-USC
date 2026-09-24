package co.edu.usc.voltacali;

import java.util.Locale;

/** Aplicacion principal de pruevas para los cargadores electricos. */
public final class App {

    private static final int ANIO_2021 = 2021;
    private static final int ANIO_2022 = 2022;
    private static final int ANIO_2023 = 2023;
    private static final int ANIO_2024 = 2024;
    private static final int ANIO_2025 = 2025;
    private static final int ANIO_BASE_C6 = 2015;

    private static final int VOLTAJE_220 = 220;
    private static final int VOLTAJE_400 = 400;
    private static final int VOLTAJE_800 = 800;

    private static final double POTENCIA_7_4 = 7.4;
    private static final double POTENCIA_11 = 11.0;
    private static final double POTENCIA_20 = 20.0;
    private static final double POTENCIA_22 = 22.0;
    private static final double POTENCIA_30 = 30.0;
    private static final double POTENCIA_40 = 40.0;
    private static final double POTENCIA_50 = 50.0;
    private static final double POTENCIA_60 = 60.0;
    private static final double POTENCIA_120 = 120.0;
    private static final double POTENCIA_150 = 150.0;

    private static final int INCREMENTO_POTENCIA_15 = 15;
    private static final int INCREMENTO_POTENCIA_10 = 10;
    private static final int INCREMENTO_POTENCIA_5 = 5;

    private static final int ENERGIA_10_KWH = 10;
    private static final int ENERGIA_50_KWH = 50;
    private static final int ENERGIA_66_KWH = 66;

    private static final int PAUSAS_2 = 2;
    private static final int PAUSAS_3 = 3;
    private static final int TIEMPO_PAUSA_15_MIN = 15;

    private static final int DIGITO_D1 = 5;
    private static final int DIGITO_D2 = 0;
    private static final int FACTOR_DECENA = 10;

    private static final int MODULO_3 = 3;
    private static final int MODULO_4 = 4;
    private static final int MODULO_5 = 5;
    private static final int MODULO_6 = 6;
    private static final int MODULO_8 = 8;

    /**
    * Constructor privado para evitar la instanciacion de esta clase de utiliddad.
     */
    private App() {
        // Constructor de clase de utiliddad
    }

    /**
    * Punto de entrada principal a la aplicacion.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(final String[] args) {
        Locale.setDefault(Locale.US);

        final CargadorVE c1 = new CargadorVE("Force", ANIO_2023, VOLTAJE_400, CargadorVE.TipoConector.CCS2,
                CargadorVE.TipoCargador.RAPIDO_DC, 2, 2, POTENCIA_60, CargadorVE.Ubicacion.UNIVERSIDAD);
        final CargadorVE c2 = new CargadorVE("Maduro", ANIO_2022, VOLTAJE_220, CargadorVE.TipoConector.TIPO_2,
                CargadorVE.TipoCargador.MURAL, 1, 1, POTENCIA_22, CargadorVE.Ubicacion.CENTRO_COMERCIAL);
        final CargadorVE c3 = new CargadorVE("Trump", ANIO_2024, VOLTAJE_800, CargadorVE.TipoConector.CCS2,
                CargadorVE.TipoCargador.ULTRARRAPIDO, 2, 2, POTENCIA_150, CargadorVE.Ubicacion.ESTACION_SERVICIO);
        final CargadorVE c4 = new CargadorVE("Julio", ANIO_2021, VOLTAJE_220, CargadorVE.TipoConector.TIPO_2,
                CargadorVE.TipoCargador.MURAL, 1, 1, POTENCIA_11, CargadorVE.Ubicacion.RESIDENCIAL);
        final CargadorVE c5 = new CargadorVE("Terminator", ANIO_2025, POTENCIA_22);

        final CargadorVE[] flota = new CargadorVE[]{c1, c2, c3, c4, c5};

        procesarCargadorC1(c1);
        actualizarPotenciasFlota(c2, c3, c4, c5);
        imprimirEstadisticasFlota(flota);
        imprimirFiltrosYDetalles(flota, c1, c5, c3);
        ejecutarPruebasAdicionales(flota, c3);
    }

    /**
    * Ejecuta las operacines y pruevas asociadas al cargador C1.
     *
     * @param c1 Objeto cargador C1.
     */
    private static void procesarCargadorC1(final CargadorVE c1) {
        c1.setPotenciaActual(POTENCIA_40);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.aumentarPotencia(INCREMENTO_POTENCIA_15);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        final double tP03 = c1.tiempoEstimadoCarga(ENERGIA_66_KWH);
        System.out.printf("Tiempo estimado (66 kWh): %.2f h%n", tP03);

        c1.aumentarPotencia(INCREMENTO_POTENCIA_10);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.reducirPotencia(POTENCIA_30);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        final double tP06 = c1.tiempoEstimadoCarga(ENERGIA_50_KWH, PAUSAS_2, TIEMPO_PAUSA_15_MIN);
        System.out.printf("Tiempo estimado con pausas (50 kWh, 2 pausas de 15 min): %.2f h%n", tP06);

        final double tP07 = c1.tiempoEstimadoCarga(ENERGIA_50_KWH, POTENCIA_40);
        System.out.printf("Tiempo estimado programado (50 kWh a 40.0 kW): %.2f h%n", tP07);

        c1.aumentarPotencia();
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.aumentarPotencia(INCREMENTO_POTENCIA_5, PAUSAS_3);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.reducirPotencia(POTENCIA_50);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.cortarCarga();
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        final double tP12 = c1.tiempoEstimadoCarga(ENERGIA_10_KWH);
        System.out.printf("Tiempo estimado (10 kWh): %.2f h%n", tP12);
    }

    /**
    * Ajusta las potencias del resto de la flota vehicular.
     *
     * @param c2 Cargador 2.
     * @param c3 Cargador 3.
     * @param c4 Cargador 4.
     * @param c5 Cargador 5.
     */
    private static void actualizarPotenciasFlota(final CargadorVE c2, final CargadorVE c3,
                                                 final CargadorVE c4, final CargadorVE c5) {
        c2.setPotenciaActual(POTENCIA_22);
        c3.setPotenciaActual(POTENCIA_120);
        c4.aumentarPotencia(POTENCIA_7_4);
        c5.aumentarPotencia(POTENCIA_30);
        System.out.printf("Potencias finales -> C2: %.1f kW | C3: %.1f kW | C4: %.1f kW | C5: %.1f kW%n",
                c2.getPotenciaActual(), c3.getPotenciaActual(), c4.getPotenciaActual(), c5.getPotenciaActual());
    }

    /**
    * Imprime reportes agregados sobre el estado de la flota.
     *
     * @param flota Arreglo de cargadores.
     */
    private static void imprimirEstadisticasFlota(final CargadorVE[] flota) {
        final int[] conteoTipos = CargadorVE.contarPorTipo(flota);
        System.out.print("Conteo por tipo -> ");
        final CargadorVE.TipoCargador[] tipos = CargadorVE.TipoCargador.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.print(tipos[i] + ": " + conteoTipos[i] + (i < tipos.length - 1 ? " | " : ""));
        }
        System.out.println();

        System.out.printf("Promedio de potencia actual de la flota: %.2f kW%n", CargadorVE.promedioPotencia(flota));

        final CargadorVE mayor = CargadorVE.mayorPotencia(flota);
        if (mayor != null) {
            System.out.printf("Cargador con mayor potencia actual: %s con %.1f kW%n",
                    mayor.getFabricante(), mayor.getPotenciaActual());
        }

        System.out.println("Excesos de potencia contratada en bitácoras: "
                + CargadorVE.excesosDePotenciaContratada(flota));
    }

    /**
    * Aplica filtros e imprime detalles puntuales sobre cargadores especificos.
     *
     * @param flota Arreglo de cargadores.
     * @param c1 Cargador 1.
     * @param c5 Cargador 5.
     * @param c3 Cargador 3.
     */
    private static void imprimirFiltrosYDetalles(final CargadorVE[] flota, final CargadorVE c1,
                                                final CargadorVE c5, final CargadorVE c3) {
        final CargadorVE[] fConector = CargadorVE.filtrar(flota, CargadorVE.TipoConector.TIPO_2);
        final CargadorVE[] fTipo = CargadorVE.filtrar(flota, CargadorVE.TipoCargador.MURAL);
        final CargadorVE[] fUbicacion = CargadorVE.filtrar(flota, CargadorVE.Ubicacion.UNIVERSIDAD);

        System.out.print("Filtrado TIPO_2 (" + fConector.length + "): ");
        imprimirFabricantes(fConector);
        System.out.print(" | MURAL (" + fTipo.length + "): ");
        imprimirFabricantes(fTipo);
        System.out.print(" | UNIVERSIDAD (" + fUbicacion.length + "): ");
        imprimirFabricantes(fUbicacion);
        System.out.println();

        System.out.println("Valores por defecto de C5:");
        c5.mostrar(false);

        final CargadorVE copiaC3 = new CargadorVE(c3);
        System.out.printf("Copia de C3 -> Fabricante: %s | Potencia: %.1f kW "
                        + "| Bitácora: %d registros | Total cargadores: %d%n",
                copiaC3.getFabricante(), copiaC3.getPotenciaActual(),
                copiaC3.getBitacora().size(), CargadorVE.getTotalCargadores());

        System.out.println("Información detallada de C1:");
        c1.mostrar(true);

        int totalRegistros = 0;
        for (final CargadorVE c : flota) {
            if (c != null) {
                totalRegistros += c.getBitacora().size();
            }
        }
        totalRegistros += copiaC3.getBitacora().size();
        System.out.println("Valor total de registros generados: " + totalRegistros);

        final double promPrueba = CargadorVE.promedioPotencia(new CargadorVE[]{c1, null, c3});
        final int[] conteoNull = CargadorVE.contarPorTipo(null);
        System.out.printf("Validación robustez -> Promedio array con null: %.2f kW | Conteo array null: %d elementos%n",
                promPrueba, conteoNull.length);
    }

    /**
    * Ejecuta la serie de pruevas avanzadas con un nuevo cargador C6 y una flota extendida.
     *
     * @param flota Flota base de cargadores.
     * @param c3 Cargador copiado previamente.
     */
    private static void ejecutarPruebasAdicionales(final CargadorVE[] flota, final CargadorVE c3) {
        final int d1 = DIGITO_D1;
        final int d2 = DIGITO_D2;
        final int valorN = FACTOR_DECENA * d1 + d2;

        final String fabC6 = "USC-" + valorN;
        final int anioC6 = ANIO_BASE_C6 + d2;
        final int voltC6 = VOLTAJE_220;
        final CargadorVE.TipoConector conectorC6 = CargadorVE.TipoConector.values()[valorN % MODULO_5];
        final CargadorVE.TipoCargador tipoC6 = CargadorVE.TipoCargador.values()[valorN % MODULO_6];
        final int numConC6 = (d1 % MODULO_3) + 1;
        final int puestosC6 = (d2 % MODULO_4) + 1;
        final double potMaxC6 = POTENCIA_20 + valorN;
        final CargadorVE.Ubicacion ubicacionC6 = CargadorVE.Ubicacion.values()[valorN % MODULO_8];

        final CargadorVE c6 = new CargadorVE(fabC6, anioC6, voltC6, conectorC6, tipoC6,
                numConC6, puestosC6, potMaxC6, ubicacionC6);

        System.out.printf("X01: N=%d, d1=%d, d2=%d%n", valorN, d1, d2);
        c6.mostrar(false);

        c6.setPotenciaActual(c6.getPotenciaMaxima() / 2.0);
        final double potInicialX02 = c6.getPotenciaActual();
        c6.aumentarPotencia(d2 + INCREMENTO_POTENCIA_5, d1 + 1);
        final double potFinalX02 = c6.getPotenciaActual();

        final boolean fueRechazado = (potInicialX02 + (d2 + INCREMENTO_POTENCIA_5) * (d1 + 1)) > c6.getPotenciaMaxima();
        System.out.printf("X02: Potencia final C6: %.1f kW | Paso rechazado: %b%n", potFinalX02, fueRechazado);

        final double tX03 = c6.tiempoEstimadoCarga(valorN + FACTOR_DECENA);
        System.out.printf("X03: Tiempo estimado C6 (%d kWh): %.2f h%n", valorN + FACTOR_DECENA, tX03);

        final CargadorVE[] flotaExtendida = new CargadorVE[flota.length + 1];
        System.arraycopy(flota, 0, flotaExtendida, 0, flota.length);
        flotaExtendida[flotaExtendida.length - 1] = c6;

        final CargadorVE.TipoCargador[] tipos = CargadorVE.TipoCargador.values();
        final int[] conteoTiposExt = CargadorVE.contarPorTipo(flotaExtendida);
        System.out.print("X05: Conteo por tipo (flotaExtendida) -> ");
        for (int i = 0; i < tipos.length; i++) {
            System.out.print(tipos[i] + ": " + conteoTiposExt[i] + (i < tipos.length - 1 ? " | " : ""));
        }
        System.out.println();

        System.out.printf("X05: Promedio potencia (flotaExtendida): %.2f kW%n",
                CargadorVE.promedioPotencia(flotaExtendida));

        final CargadorVE mayorExt = CargadorVE.mayorPotencia(flotaExtendida);
        if (mayorExt != null) {
            System.out.printf("X05: Mayor potencia (flotaExtendida): %s con %.1f kW%n",
                    mayorExt.getFabricante(), mayorExt.getPotenciaActual());
        }

        System.out.println("X05: Excesos potencia contratada (flotaExtendida): "
                + CargadorVE.excesosDePotenciaContratada(flotaExtendida));

        final CargadorVE copiaC3 = new CargadorVE(c3);
        int totalRegistrosExt = 0;
        for (final CargadorVE c : flotaExtendida) {
            if (c != null) {
                totalRegistrosExt += c.getBitacora().size();
            }
        }
        totalRegistrosExt += copiaC3.getBitacora().size();

        System.out.printf("X06: Total cargadores instanciados: %d | Total registros generados: %d%n",
                CargadorVE.getTotalCargadores(), totalRegistrosExt);
        System.out.println("X06: Bitácora completa y detalles de C6:");
        c6.mostrar(true);
    }

    /**
    * Imprime en consola los nombres de los fabricantes de un arreglo de cargadores.
     *
     * @param lista Arreglo de cargadores a imprimir.
     */
    private static void imprimirFabricantes(final CargadorVE[] lista) {
        if (lista == null) {
            return;
        }
        for (int i = 0; i < lista.length; i++) {
            System.out.print(lista[i].getFabricante() + (i < lista.length - 1 ? ", " : ""));
        }
    }
}
