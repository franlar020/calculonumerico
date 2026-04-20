# 🚀 Proyecto: Cálculo Numérico - UNLaR

Este proyecto fue desarrollado para la cátedra de **Cálculo Numérico** (Sede Capital - Ing. Cobresí). Proporciona una herramienta interactiva en Java para la aproximación de raíces en ecuaciones no lineales utilizando métodos cerrados y abiertos.

## 🛠️ Funcionalidades
El programa se ejecuta a través de la **consola (terminal)** y permite resolver ecuaciones mediante:

* **Métodos Cerrados:**
    * **Bisección:** Divide el intervalo a la mitad sucesivamente.
    * **Regla Falsa:** Utiliza interpolación lineal para converger a la raíz.
* **Métodos Abiertos:**
    * **Punto Fijo:** Requiere ingresar la función despejada $g(x)$.
    * **Newton-Raphson:** ¡Ahora con **Derivada Automática**! No hace falta que ingreses $f'(x)$.
    * **Secante:** Utiliza dos puntos iniciales sin requerir derivadas.

El sistema genera tablas detalladas que incluyen $n$, $X_n$, $F(x)$, errores absolutos ($E$) y errores relativos porcentuales ($Er\%$).

---

## 💻 Ejecución en Consola
Para correr el programa en Visual Studio Code o cualquier terminal:

1. Asegurate de tener instalada la dependencia **exp4j** en tu `pom.xml`.
2. Compilá el proyecto.
3. Ejecutá la clase `App.java`.
4. **Importante:** Al ingresar números decimales, utilizá el **punto (.)** (ejemplo: `0.0005`), aunque el programa cuenta con un autocrrector para comas (`,`).

---

## 📝 Guía de Escritura de Funciones
Para que el motor reconozca las ecuaciones, seguí estas reglas de sintaxis:

### 1. Operaciones Básicas
* **Multiplicación:** Usar siempre `*` (Ejemplo: `3*x`).
* **Potencia:** Usar `^` (Ejemplo: `x^2`).
* **División:** Usar `/` y paréntesis para agrupar (Ejemplo: `(x+2)/(x-1)`).

### 2. Funciones Especiales
* **Seno:** Escribir `sen(x)` o `sin(x)`. El programa opera siempre en **modo radián**.
* **Coseno:** Escribir `cos(x)`.
* **Logaritmo Natural ($\ln$):** Escribir `log(x)`.
* **Logaritmo Base 10:** Escribir `log10(x)`.
* **Número de Euler ($e$):** Escribir `e^x`.

---

## 📍 Formato de Ingreso del Error ($E$)
Es fundamental ingresar el error en **formato decimal estándar** para evitar fallos:

* ✅ **CORRECTO:** `0.003`, `0.0001`, `0.05`
* ❌ **EVITAR:** Notación científica como `3x10^-3` o `1e-4`.

| Valor Teórico | Ingreso en el programa |
| :--- | :--- |
| $3 \times 10^{-3}$ | `0.003` |
| $1 \times 10^{-4}$ | `0.0001` |
| $5 \times 10^{-2}$ | `0.05` |

---

## 💡 Tips de la Cátedra Aplicados
* **Precisión:** El programa muestra **4 decimales** en todos los cálculos, tal como pide el profesor.
* **Derivada Automática:** En el método de Newton, el código calcula internamente la pendiente de la tangente (derivación numérica).
* **Teorema de Bolzano:** Se verifica automáticamente en los métodos de Bisección y Regla Falsa.
* **Cifras Significativas:** Una vez obtenida la raíz, recordá aplicar la técnica de la línea vertical sobre el error para el redondeo final en tu hoja.

---
Desarrollado por **Francisco Antonio Gonzalez** – Estudiante de Ingeniería de Sistemas en Información (UNLaR).