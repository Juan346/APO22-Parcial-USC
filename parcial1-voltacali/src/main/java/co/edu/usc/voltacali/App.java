package co.edu.usc.voltacali;

import java.util.Locale;

public class App {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        CargadorVE c1 = new CargadorVE("Force", 2023, 400, CargadorVE.TipoConector.CCS2,
                CargadorVE.TipoCargador.RAPIDO_DC, 2, 2, 60.0, CargadorVE.Ubicacion.UNIVERSIDAD);
        CargadorVE c2 = new CargadorVE("Maduro", 2022, 220, CargadorVE.TipoConector.TIPO_2,
                CargadorVE.TipoCargador.MURAL, 1, 1, 22.0, CargadorVE.Ubicacion.CENTRO_COMERCIAL);
        CargadorVE c3 = new CargadorVE("Trump", 2024, 800, CargadorVE.TipoConector.CCS2,
                CargadorVE.TipoCargador.ULTRARRAPIDO, 2, 2, 150.0, CargadorVE.Ubicacion.ESTACION_SERVICIO);
        CargadorVE c4 = new CargadorVE("Julio", 2021, 220, CargadorVE.TipoConector.TIPO_2,
                CargadorVE.TipoCargador.MURAL, 1, 1, 11.0, CargadorVE.Ubicacion.RESIDENCIAL);
        CargadorVE c5 = new CargadorVE("Terminator", 2025, 22.0);

        CargadorVE[] flota = new CargadorVE[]{c1, c2, c3, c4, c5};

        c1.setPotenciaActual(40);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.aumentarPotencia(15);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        double tP03 = c1.tiempoEstimadoCarga(66);
        System.out.printf("Tiempo estimado (66 kWh): %.2f h%n", tP03);

        c1.aumentarPotencia(10);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.reducirPotencia(30);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        double tP06 = c1.tiempoEstimadoCarga(50, 2, 15);
        System.out.printf("Tiempo estimado con pausas (50 kWh, 2 pausas de 15 min): %.2f h%n", tP06);

        double tP07 = c1.tiempoEstimadoCarga(50, 40.0);
        System.out.printf("Tiempo estimado programado (50 kWh a 40.0 kW): %.2f h%n", tP07);

        c1.aumentarPotencia();
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.aumentarPotencia(5, 3);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.reducirPotencia(50);
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        c1.cortarCarga();
        System.out.printf("Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        double tP12 = c1.tiempoEstimadoCarga(10);
        System.out.printf("Tiempo estimado (10 kWh): %.2f h%n", tP12);

        c2.setPotenciaActual(22);
        c3.setPotenciaActual(120);
        c4.aumentarPotencia(7.4);
        c5.aumentarPotencia(30);
        System.out.printf("Potencias finales -> C2: %.1f kW | C3: %.1f kW | C4: %.1f kW | C5: %.1f kW%n",
                c2.getPotenciaActual(), c3.getPotenciaActual(), c4.getPotenciaActual(), c5.getPotenciaActual());

        int[] conteoTipos = CargadorVE.contarPorTipo(flota);
        System.out.print("Conteo por tipo -> ");
        CargadorVE.TipoCargador[] tipos = CargadorVE.TipoCargador.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.print(tipos[i] + ": " + conteoTipos[i] + (i < tipos.length - 1 ? " | " : ""));
        }
        System.out.println();

        System.out.printf("Promedio de potencia actual de la flota: %.2f kW%n", CargadorVE.promedioPotencia(flota));

        CargadorVE mayor = CargadorVE.mayorPotencia(flota);
        if (mayor != null) {
            System.out.printf("Cargador con mayor potencia actual: %s con %.1f kW%n",
                    mayor.getFabricante(), mayor.getPotenciaActual());
        }

        System.out.println("Excesos de potencia contratada en bitácoras: " + CargadorVE.excesosDePotenciaContratada(flota));

        CargadorVE[] fConector = CargadorVE.filtrar(flota, CargadorVE.TipoConector.TIPO_2);
        CargadorVE[] fTipo = CargadorVE.filtrar(flota, CargadorVE.TipoCargador.MURAL);
        CargadorVE[] fUbicacion = CargadorVE.filtrar(flota, CargadorVE.Ubicacion.UNIVERSIDAD);

        System.out.print("Filtrado TIPO_2 (" + fConector.length + "): ");
        imprimirFabricantes(fConector);
        System.out.print(" | MURAL (" + fTipo.length + "): ");
        imprimirFabricantes(fTipo);
        System.out.print(" | UNIVERSIDAD (" + fUbicacion.length + "): ");
        imprimirFabricantes(fUbicacion);
        System.out.println();

        System.out.println("Valores por defecto de C5:");
        c5.mostrar(false);

        CargadorVE copiaC3 = new CargadorVE(c3);
        System.out.printf("Copia de C3 -> Fabricante: %s | Potencia: %.1f kW | Bitácora: %d registros | Total cargadores: %d%n",
                copiaC3.getFabricante(), copiaC3.getPotenciaActual(), copiaC3.getBitacora().size(), CargadorVE.getTotalCargadores());

        System.out.println("Información detallada de C1:");
        c1.mostrar(true);

        int totalRegistros = 0;
        for (CargadorVE c : flota) {
            if (c != null) {
                totalRegistros += c.getBitacora().size();
            }
        }
        totalRegistros += copiaC3.getBitacora().size();
        System.out.println("Valor total de registros generados: " + totalRegistros);

        double promPrueba = CargadorVE.promedioPotencia(new CargadorVE[]{c1, null, c3});
        int[] conteoNull = CargadorVE.contarPorTipo(null);
        System.out.printf("Validación robustez -> Promedio array con null: %.2f kW | Conteo array null: %d elementos%n",
                promPrueba, conteoNull.length);

        int d1 = 5;
        int d2 = 0;
        int N = 10 * d1 + d2;

        String fabC6 = "USC-" + N;
        int anioC6 = 2015 + d2;
        int voltC6 = (N % 2 == 0) ? 220 : 400;
        CargadorVE.TipoConector conectorC6 = CargadorVE.TipoConector.values()[N % 5];
        CargadorVE.TipoCargador tipoC6 = CargadorVE.TipoCargador.values()[N % 6];
        int numConC6 = (d1 % 3) + 1;
        int puestosC6 = (d2 % 4) + 1;
        double potMaxC6 = 20.0 + N;
        CargadorVE.Ubicacion ubicacionC6 = CargadorVE.Ubicacion.values()[N % 8];

        CargadorVE c6 = new CargadorVE(fabC6, anioC6, voltC6, conectorC6, tipoC6, numConC6, puestosC6, potMaxC6, ubicacionC6);

        System.out.printf("X01: N=%d, d1=%d, d2=%d%n", N, d1, d2);
        c6.mostrar(false);

        c6.setPotenciaActual(c6.getPotenciaMaxima() / 2.0);
        double potInicialX02 = c6.getPotenciaActual();
        c6.aumentarPotencia(d2 + 5, d1 + 1);
        double potFinalX02 = c6.getPotenciaActual();

        boolean fueRechazado = (potInicialX02 + (d2 + 5) * (d1 + 1)) > c6.getPotenciaMaxima();
        System.out.printf("X02: Potencia final C6: %.1f kW | Paso rechazado: %b%n", potFinalX02, fueRechazado);

        double tX03 = c6.tiempoEstimadoCarga(N + 10);
        System.out.printf("X03: Tiempo estimado C6 (%d kWh): %.2f h%n", N + 10, tX03);

        CargadorVE[] flotaExtendida = new CargadorVE[flota.length + 1];
        for (int i = 0; i < flota.length; i++) {
            flotaExtendida[i] = flota[i];
        }
        flotaExtendida[flotaExtendida.length - 1] = c6;

        int[] conteoTiposExt = CargadorVE.contarPorTipo(flotaExtendida);
        System.out.print("X05: Conteo por tipo (flotaExtendida) -> ");
        for (int i = 0; i < tipos.length; i++) {
            System.out.print(tipos[i] + ": " + conteoTiposExt[i] + (i < tipos.length - 1 ? " | " : ""));
        }
        System.out.println();

        System.out.printf("X05: Promedio potencia (flotaExtendida): %.2f kW%n", CargadorVE.promedioPotencia(flotaExtendida));

        CargadorVE mayorExt = CargadorVE.mayorPotencia(flotaExtendida);
        if (mayorExt != null) {
            System.out.printf("X05: Mayor potencia (flotaExtendida): %s con %.1f kW%n", mayorExt.getFabricante(), mayorExt.getPotenciaActual());
        }

        System.out.println("X05: Excesos potencia contratada (flotaExtendida): " + CargadorVE.excesosDePotenciaContratada(flotaExtendida));

        int totalRegistrosExt = 0;
        for (CargadorVE c : flotaExtendida) {
            if (c != null) {
                totalRegistrosExt += c.getBitacora().size();
            }
        }
        totalRegistrosExt += copiaC3.getBitacora().size();

        System.out.printf("X06: Total cargadores instanciados: %d | Total registros generados: %d%n", CargadorVE.getTotalCargadores(), totalRegistrosExt);
        System.out.println("X06: Bitácora completa y detalles de C6:");
        c6.mostrar(true);
    }

    private static void imprimirFabricantes(CargadorVE[] lista) {
        if (lista == null) return;
        for (int i = 0; i < lista.length; i++) {
            System.out.print(lista[i].getFabricante() + (i < lista.length - 1 ? ", " : ""));
        }
    }
}