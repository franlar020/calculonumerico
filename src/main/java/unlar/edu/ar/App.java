package unlar.edu.ar;

import java.util.Scanner;
import java.util.Locale;
import unlar.edu.ar.objets.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        Metodos calc = new Metodos();

        System.out.println("--- CÁLCULO NUMÉRICO UNLAR ---");
        System.out.println("--- GUÍA DE SINTAXIS PARA FUNCIONES ---");
        System.out.println("-> Multiplicación: '*' | Potencia: '^' | Seno: 'sin(x)' | e: 'e^x'");
        
        System.out.println("->. Multiplicación: Usar siempre '*' (Ej: 3*x)");
        System.out.println("->. Potencia: Usar '^' (Ej: x^2)");
        System.out.println("->. Seno: Usar 'sin(x)' o 'sen(x)'");
        System.out.println("->. Logaritmo Natural: Usar 'log(x)', Logaritmo Base 10: 'log10(x)'");
        System.out.println("->. Constante e: Usar 'e' (Ej: e^x)");
        System.out.println("->. Paréntesis: Usar para agrupar (Ej: (x+2)/(x-1))");
        System.out.println("----------------------------------------");
        
        System.out.print("Ingrese la función f(x): ");
        String formula = sc.nextLine();
        Funcion f = new FuncionDinamica(formula);

        System.out.println("\nElija el método:");
        System.out.println("1. Bisección\n2. Regla Falsa\n3. Punto Fijo\n4. Newton\n5. Secante");
        int op = sc.nextInt();

        System.out.print("Error deseado (E) [ej: 0.0005]: ");
        double tol = sc.nextDouble();

        // --- PEDIDO DE DATOS SEGÚN EL MÉTODO ---
        double xInic = 0, a = 0, b = 0, x1_sec = 0;

        if (op == 1 || op == 2) {
            System.out.print("Límite a: ");
            a = sc.nextDouble();
            System.out.print("Límite b: ");
            b = sc.nextDouble();
        } else if (op == 3 || op == 4) {
            System.out.print("Ingrese valor inicial X0: ");
            xInic = sc.nextDouble();
        } else if (op == 5) {
            System.out.print("Ingrese X0: ");
            xInic = sc.nextDouble(); // Reutilizamos xInic como x0
            System.out.print("Ingrese X1: ");
            x1_sec = sc.nextDouble();
        }

        switch (op) {
            case 1:
                imprimirBiseccion(a, b, tol, calc, f);
                break;
            case 2:
                imprimirReglaFalsa(a, b, tol, calc, f);
                break;
            case 3:
                System.out.print("Ingrese g(x) para Punto Fijo: ");
                sc.nextLine(); // Limpiar el buffer del Enter anterior
                Funcion g = new FuncionDinamica(sc.nextLine());
                imprimirTablaPuntoFijo(xInic, tol, calc, g);
                break;
            case 4:
                imprimirTablaNewtonAuto(xInic, tol, calc, f);
                break;
            case 5:
                imprimirTablaSecante(xInic, x1_sec, tol, calc, f);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    // --- MÉTODOS DE IMPRESIÓN ---

    private static void imprimirTablaNewtonAuto(double xn, double tol, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-11s | %-9s | %-7s\n", "n", "Xn", "F(x)", "F'(x) aprox", "En", "Ern%");
        System.out.println("-----------------------------------------------------------------------------");
        double xAnt = xn;
        for (int n = 0; n <= 30; n++) {
            double fx = f.evaluar(xn);
            double h = 1e-6;
            double fdx = (f.evaluar(xn + h) - fx) / h;
            double err = (n == 0) ? 0 : Math.abs(xn - xAnt);
            double eRel = (n == 0) ? 0 : Math.abs((err / xn) * 100);

            System.out.printf("%-3d | %-9.4f | %-9.4f | %-11.4f | %-9.4f | %-7.2f%%\n", n, xn, fx, fdx, err, eRel);

            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) {
                System.out.println("\nRaíz encontrada por Newton Automático.");
                break;
            }
            if (Math.abs(fdx) < 1e-10) break;
            xAnt = xn;
            xn = xn - (fx / fdx);
        }
    }

    private static void imprimirTablaSecante(double x0, double x1, double tol, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-9s | %-7s\n", "n", "Xn", "F(x)", "En", "Ern%");
        System.out.println("---------------------------------------------------------");
        double xAnt = x0;
        double xAct = x1;
        for (int n = 0; n <= 30; n++) {
            double fAct = f.evaluar(xAct);
            double fAnt = f.evaluar(xAnt);
            double err = Math.abs(xAct - xAnt);
            double eRel = (n == 0) ? 100 : Math.abs((err / xAct) * 100);

            System.out.printf("%-3d | %-9.4f | %-9.4f | %-9.4f | %-7.2f%%\n", n, xAct, fAct, err, eRel);

            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) {
                System.out.println("\nRaíz encontrada por Secante.");
                break;
            }
            if (Math.abs(fAct - fAnt) < 1e-12) break;
            double xSig = xAct - (fAct * (xAct - xAnt)) / (fAct - fAnt);
            xAnt = xAct;
            xAct = xSig;
        }
    }

    private static void imprimirTablaPuntoFijo(double xn, double tol, Metodos calc, Funcion g) {
        System.out.printf("\n%-3s | %-9s | %-9s | %-8s\n", "n", "Xn", "En", "Ern%");
        System.out.println("----------------------------------------------");
        double xAnt = xn;
        for (int n = 0; n <= 30; n++) {
            double err = (n == 0) ? 0 : Math.abs(xn - xAnt);
            double eRel = (n == 0) ? 0 : Math.abs((err / xn) * 100);
            System.out.printf("%-3d | %-9.4f | %-9.4f | %-7.2f%%\n", n, xn, err, eRel);
            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) break;
            xAnt = xn;
            xn = calc.puntoFijo(xn, g);
        }
    }

    private static void imprimirBiseccion(double a, double b, double tol, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-7s | %-7s | %-7s | %-7s | %-7s | %-7s | %-10s | %-8s | %-7s\n", "n", "An", "Bn", "Xn", "F(a)", "F(b)", "F(x)", "F(a)*F(x)", "E", "Ern%");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        double xAnt = 0;
        for (int n = 0; n <= 30; n++) {
            double xn = (a + b) / 2;
            double fa = f.evaluar(a);
            double fx = f.evaluar(xn);
            double prod = fa * fx;
            double err = (n == 0) ? Math.abs(b - a) : Math.abs(xn - xAnt);
            double eRel = (n == 0) ? 100 : Math.abs((err / xn) * 100);
            System.out.printf("%-3d | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-10.4f | %-8.4f | %-7.2f%%\n", n, a, b, xn, fa, f.evaluar(b), fx, prod, err, eRel);
            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) break;
            if (prod < 0) b = xn; else a = xn;
            xAnt = xn;
        }
    }

    private static void imprimirReglaFalsa(double a, double b, double tol, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-7s | %-7s | %-7s | %-7s | %-7s | %-7s | %-10s | %-8s | %-7s\n", "n", "An", "Bn", "Xn", "F(a)", "F(b)", "F(x)", "F(a)*F(x)", "E", "Ern%");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        double xAnt = 0;
        for (int n = 0; n <= 30; n++) {
            double fa = f.evaluar(a);
            double fb = f.evaluar(b);
            double xn = b - (fb * (b - a)) / (fb - fa);
            double fx = f.evaluar(xn);
            double prod = fa * fx;
            double err = (n == 0) ? Math.abs(b - a) : Math.abs(xn - xAnt);
            double eRel = (n == 0) ? 100 : Math.abs((err / xn) * 100);
            System.out.printf("%-3d | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-7.4f | %-10.4f | %-8.4f | %-7.2f%%\n", n, a, b, xn, fa, fb, fx, prod, err, eRel);
            if (n > 0 && ((tol >= 1 && eRel <= tol) || (tol < 1 && err <= tol))) break;
            if (prod < 0) b = xn; else a = xn;
            xAnt = xn;
        }
    }
}