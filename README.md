# 🧮 Proyecto: Cálculo Numérico - UNLaR

Este proyecto fue desarrollado para la cátedra de **Cálculo Numérico** (Sede Capital - Ing. Cobresí). El objetivo es proporcionar una herramienta interactiva en Java para la aproximación de raíces en ecuaciones no lineales utilizando **Métodos Cerrados**.

## 🚀 Funcionalidades
El programa permite resolver ecuaciones mediante:
* **Método de la Bisección:** Divide el intervalo a la mitad sucesivamente.
* **Método de la Regla Falsa:** Utiliza interpolación lineal para converger a la raíz.

El sistema genera una tabla detallada que incluye $n$, $A_n$, $B_n$, $X_n$, $F(a)$, $F(x)$, el error absoluto ($E$) 
y el error relativo porcentual.

---

## ⌨️ Guía de Escritura de Funciones
Para que el motor de expresiones reconozca las funciones, debés seguir estas reglas de sintaxis:

### 1. Operaciones Básicas
* **Multiplicación:** Usar siempre el asterisco `*`. (Ejemplo: `3*x` en lugar de `3x`).
* **Potencia:** Usar el símbolo `^`. (Ejemplo: `x^2` para $x$ al cuadrado).
* **División:** Usar `/` y agrupar con paréntesis si es necesario. (Ejemplo: `(x+1)/(x-1)`).

### 2. Funciones Especiales
* **Seno:** Escribir `sin(x)` o `sen(x)`. El programa opera siempre en **modo radián**.
* **Coseno:** Escribir `cos(x)`.
* **Logaritmo Natural ($\ln$):** Escribir `log(x)`.
* **Logaritmo Base 10:** Escribir `log10(x)`.
* **Número de Euler ($e$):** Escribir `e^x` para funciones exponenciales.

### 3. Ejemplos de entrada
| Función Matemática | Cómo escribirla en el programa |
| :--- | :--- |
| $x^3 - \text{sen}(x) - 3$ | `x^3 - sin(x) - 3` |
| $\cos(x) - 3x$ | `cos(x) - 3*x` |
| $5x^3 + x^2 - x + 2$ | `5*x^3 + x^2 - x + 2` |
| $e^{2x} - 7$ | `e^(2*x) - 7` |

---

## 📋 Tips de la Cátedra aplicados
* **Precisión:** El programa está configurado para mostrar **4 decimales** en todos los cálculos de la tabla.
* **Condición de Parada:** El método se detiene cuando el error absoluto $|X_n - X_{n-1}|$ es menor a la tolerancia ingresada.
* **Teorema de Bolzano:** Asegurarse de que el intervalo $[a, b]$ cumpla que $F(a) \cdot F(b) < 0$ para garantizar la existencia de la raíz.
* **Cifras Significativas:** Una vez obtenida la raíz aproximada, aplicar la técnica de la línea vertical sobre el error para determinar la precisión real antes del redondeo final.

---
Desarrollado por **Francisco Antonio Gonzalez** - Estudiante de Ingenieria de Sistemas en Informacion (UNLaR).
