# APO22-Parcial-USC

# Parcial 1: Sistema de Gestión de Cargadores para Vehículos Eléctricos

* **Estudiante:** Juan Jose Alvarez
* **Código:** 1114621350
* **Asignatura:** Algoritmos y Programación II (APO22)
* **Institución:** Universidad Santiago de Cali - Facultad de Ingeniería

---

## 1. Datos Asignados por Cédula

Reemplaza los datos según los dos últimos dígitos de tu cédula ($N$):

* **Últimos dos dígitos de la cédula ($N$):** [Ej: 47]
* **Penúltimo dígito ($d_1$):** [Ej: 4]
* **Último dígito ($d_2$):** [Ej: 7]
* **Valor de $r$ ($N \pmod 4$):** [Ej: 3]
* **Ruta asignada:** [Ej: Ruta 3]

---

## 2. Tabla de Atributos de C6 (Parte F)

Esta es la configuración del sexto cargador ($C6$) calculada a partir de los dígitos de la cédula:

| Atributo | Regla / Fórmula | Valor Calculado |
| :--- | :--- | :--- |
| **Fabricante** | `"USC-" + N` | `USC-[Tu_N]` |
| **Año de Instalación** | $2015 + d_2$ | `[Año]` |
| **Voltaje Nominal** | 220 si $N$ es par; 400 si es impar | `[Voltaje] V` |
| **Tipo de Conector** | `TipoConector.values()[N % 5]` | `[Conector]` |
| **Tipo de Cargador** | `TipoCargador.values()[N % 6]` | `[Tipo]` |
| **Número de Conectores**| $(d_1 \pmod 3) + 1$ | `[Num]` |
| **Puestos de Parqueo** | $(d_2 \pmod 4) + 1$ | `[Puestos]` |
| **Potencia Máxima** | $20 + N$ | `[Potencia] kW` |
| **Ubicación** | `Ubicacion.values()[N % 8]` | `[Ubicación]` |

---


### Compilar el proyecto:
```bash
mvn clean compile