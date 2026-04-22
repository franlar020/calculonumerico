package unlar.edu.ar;

import java.util.Scanner;
import java.util.Locale;
import unlar.edu.ar.objets.*;

public class App {
    public static void main(String[] args) {
        // Forzamos el uso de Locale.US para que siempre acepte el punto decimal "."
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        Metodos calc = new Metodos();

        System.out.println("--- CÁLCULO NUMÉRICO UNLAR ---");
        System.out.print("Asi se escribe la funcion (Ej: 3,5*x-6): ");
        System.out.println("\n--- GUÍA DE SINTAXIS PARA FUNCIONES ---");
        System.out.println("->. Multiplicación: Usar siempre '*' (Ej: 3*x)");
        System.out.println("->. Potencia: Usar '^' (Ej: x^2)");
        System.out.println("->. Seno: Usar 'sin(x)' o 'sen(x)'");
        System.out.println("->. Logaritmo Natural: Usar 'log(x)', Logaritmo Base 10: 'log10(x)'");
        System.out.println("->. Constante e: Usar 'e' (Ej: e^x)");
        System.out.println("->. Paréntesis: Usar para agrupar (Ej: (x+2)/(x-1))");
        System.out.println("----------------------------------------");
        System.out.print("Ingrese la función f(x)\n: ");
        String formula = sc.nextLine();
        Funcion f = new FuncionDinamica(formula);

        System.out.println("\nElija el método:");
        System.out.println("1. Bisección\n2. Regla Falsa\n3. Punto Fijo\n4. Newton\n5. Secante");
        int op = sc.nextInt();

        System.out.print("Error deseado (E) [Usar punto, ej: 0.0005]: ");
        double tol = sc.nextDouble();

        // --- AGREGÁ ESTO PARA SOLUCIONAR EL ERROR ---
        double a = 0, b = 0; // Las declaramos aquí
        if (op == 1 || op == 2) { // Solo si eligió Bisección o Regla Falsa
            System.out.print("Límite a: ");
            a = sc.nextDouble();
            System.out.print("Límite b: ");
            b = sc.nextDouble();

            switch (op) {
                case 1: // BISECCIÓN
                    imprimirBiseccion(a, b, tol, calc, f);
                    break;

                case 2: // REGLA FALSA
                    imprimirReglaFalsa(a, b, tol, calc, f);
                    break;

                case 3: // PUNTO FIJO
                    System.out.print("Ingrese g(x): ");
                    sc.nextLine(); // Limpiar buffer
                    Funcion g = new FuncionDinamica(sc.nextLine());
                    System.out.print("Ingrese valor inicial X0: ");
                    imprimirTablaPuntoFijo(sc.nextDouble(), tol, calc, g);
                    break;

                case 4: // NEWTON AUTOMÁTICO
                    System.out.print("Ingrese valor inicial X0: ");
                    double xInicioNewton = sc.nextDouble();
                    imprimirTablaNewtonAuto(xInicioNewton, tol, calc, f);
                    break;

                case 5: // SECANTE
                    System.out.print("Ingrese X0: ");
                    double x0 = sc.nextDouble();
                    System.out.print("Ingrese X1: ");
                    double x1 = sc.nextDouble();
                    imprimirTablaSecante(x0, x1, tol, calc, f);
                    break;

            }

        }
    }
    // --- MÉTODOS DE IMPRESIÓN DE TABLAS ---

    private static void imprimirTablaNewton(double xn, double tol, Metodos calc, Funcion f, Funcion fDer) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-9s | %-9s | %-8s\n", "n", "Xn", "F(x)", "F'(x)", "En", "Ern%");
        double xAnt = 0;
        for (int n = 0; n <= 30; n++) {
            double fx = f.evaluar(xn);
            double fdx = fDer.evaluar(xn);
            double err = (n == 0) ? 0 : Math.abs(xn - xAnt);
            double eRel = (n == 0) ? 0 : Math.abs((err / xn) * 100);

            System.out.printf("%-3d | %-9.4f | %-9.4f | %-9.4f | %-9.4f | %-7.2f%%\n", n, xn, fx, fdx, err, eRel);

            if (n > 0 && err < tol) break;
            xAnt = xn;
            xn = calc.newton(xn, f, fDer);
        }
    }

    private static void imprimirTablaSecante(double x0, double x1, double tol, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-9s | %-8s\n", "n", "Xn", "F(x)", "En", "Ern%");
        double xn_1 = x0;
        double xn = x1;
        for (int n = 0; n <= 30; n++) {
            double actual = (n == 0) ? xn_1 : xn;
            double fx = f.evaluar(actual);
            double err = (n == 0) ? 0 : Math.abs(actual - xn_1);
            double eRel = (n == 0) ? 0 : Math.abs((err / actual) * 100);

            System.out.printf("%-3d | %-9.4f | %-9.4f | %-9.4f | %-7.2f%%\n", n, actual, fx, err, eRel);

            // CONTROL DE PARADA 1: Por error
            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) {
                break;
            }

            // CONTROL DE PARADA 2: Raíz exacta
            if (Math.abs(fx) < 1e-12) {
                System.out.println("\nRaíz exacta encontrada.");
                break;
            }

            if (n >= 1) {
                double denominador = f.evaluar(xn) - f.evaluar(xn_1);
                if (Math.abs(denominador) < 1e-12) {
                    System.out.println("\nError: División por cero en la Secante.");
                    break;
                }
                double proximo = xn - (f.evaluar(xn) * (xn - xn_1)) / denominador;
                xn_1 = xn;
                xn = proximo;
            }
        }
    }

    private static void imprimirTablaPuntoFijo(double xn, double tol, Metodos calc, Funcion g) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-8s\n", "n", "Xn", "En", "Ern%");
        System.out.println("----------------------------------------------");

        double xAnterior = xn; // Guardamos el X0 inicial
        for (int n = 0; n <= 30; n++) {
            // 1. En n=0 no hay error
            double err = (n == 0) ? 0 : Math.abs(xn - xAnterior);
            double eRel = (n == 0) ? 0 : Math.abs((err / xn) * 100);

            // 2. Imprimimos la tabla
            System.out.printf("%-3d | %-9.4f | %-9.4f | %-7.2f%%\n", n, xn, err, eRel);

            // 3. Verificamos condición de parada
            if (n > 0) {
                if ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))
                    break;
            }

            // 4. Calculamos el siguiente valor y guardamos el actual como anterior
            xAnterior = xn;
            xn = calc.puntoFijo(xn, g);
        }
    }

    private static void imprimirBiseccion(double a, double b, double tol, Metodos calc, Funcion f) {
    System.out.printf("\n%-3s | %-7s | %-7s | %-7s | %-7s | %-7s | %-7s | %-10s | %-8s | %-7s\n", 
                      "n", "An", "Bn", "Xn", "F(a)", "F(b)", "F(x)", "F(a)*F(x)", "E", "Ern%");
    System.out.println("----------------------------------------------------------------------------------------------------------");

    double xAnt = 0;
    for (int n = 0; n <= 30; n++) {
        double xn = (a + b) / 2; // Fórmula simple de Bisección
        double fa = f.evaluar(a);
        double fx = f.evaluar(xn);
        double prod = fa * fx;

        double err = (n == 0) ? Math.abs(b - a) : Math.abs(xn - xAnt);
        double eRel = (n == 0) ? 100 : Math.abs((err / xn) * 100);

        System.out.printf("%-3d | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-10.4f | %-8.4f | %-7.2f%%\n", 
                          n, a, b, xn, fa, f.evaluar(b), fx, prod, err, eRel);

        if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) {
            System.out.println("\nRaíz encontrada por Bisección.");
            break;
        }

        if (prod < 0) b = xn; else a = xn;
        xAnt = xn;
    }
}

private static void imprimirReglaFalsa(double a, double b, double tol, Metodos calc, Funcion f) {
    System.out.printf("\n%-3s | %-7s | %-7s | %-7s | %-7s | %-7s | %-7s | %-10s | %-8s | %-7s\n",
            "n", "An", "Bn", "Xn", "F(a)", "F(b)", "F(x)", "F(a)*F(x)", "E", "Ern%");
    System.out.println(
            "----------------------------------------------------------------------------------------------------------");

double xAnt = 0;
    
    for (int n = 0; n <= 30; n++) {
        double fa = f.evaluar(a);
        double fb = f.evaluar(b);
        
        // 1. Calculamos Xn
        double xn = b - (fb * (b - a)) / (fb - fa); 
        double fx = f.evaluar(xn);
        double prod = fa * fx;

        // 2. Calculamos errores
        double err = (n == 0) ? Math.abs(b - a) : Math.abs(xn - xAnt);
        double eRel = (n == 0) ? 100 : Math.abs((err / xn) * 100);

        // 3. Imprimimos la fila (Asegúrate de que este printf esté adentro del for)
        System.out.printf("%-3d | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-10.4f | %-8.4f | %-7.2f%%\n", 
                          n, a, b, xn, fa, fb, fx, prod, err, eRel);

        // 4. Lógica de parada
        if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) {
            System.out.println("\nRaíz encontrada por Regla Falsa.");
            break;
        }

        // --- 5. ESTO ES LO QUE TE FALTA: ACTUALIZAR LOS LÍMITES ---
        if (prod < 0) {
            b = xn; // La raíz está a la izquierda, movemos el límite derecho
        } else {
            a = xn; // La raíz está a la derecha, movemos el límite izquierdo
        }
        
        xAnt = xn; // Guardamos para la próxima vuelta
    }
    }


    private static void imprimirTablaNewtonAuto(double xn, double tol, Metodos calc, Funcion f) {
    // Encabezado con F'(x) aproximada
    System.out.printf("\n%-3s | %-9s | %-9s | %-9s | %-9s | %-8s\n", "n", "Xn", "F(x)", "F'(x) aprox", "En", "Ern%");
    System.out.println("---------------------------------------------------------------------------------------");
    
    double xAnt = 0;
    for (int n = 0; n <= 30; n++) {
        double fx = f.evaluar(xn);
        
        // Calculamos la derivada de forma automática usando un h muy chiquito
        double h = 1e-6;
        double fdx = (f.evaluar(xn + h) - fx) / h; 
        
        double err = (n == 0) ? 0 : Math.abs(xn - xAnt);
        double eRel = (n == 0) ? 0 : Math.abs((err / xn) * 100);

        System.out.printf("%-3d | %-9.4f | %-9.4f | %-9.4f | %-9.4f | %-7.2f%%\n", 
                          n, xn, fx, fdx, err, eRel);

        if (n > 0 && err < tol) {
            System.out.println("\nRaíz encontrada con Newton Auto: " + String.format("%.4f", xn));
            break;
        }

        xAnt = xn;
        // Fórmula de Newton: xn+1 = xn - f(xn)/f'(xn)
        xn = xn - (fx / fdx);
    }
    }
}