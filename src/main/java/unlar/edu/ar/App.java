package unlar.edu.ar;

import java.util.Scanner;
import java.util.Locale;
import unlar.edu.ar.objets.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        Metodos calculadora = new Metodos();

        System.out.println("--- Calculo Numerico UNLAR ---");
        System.out.print("Decime la funcion (Ej: 3,5*x-6): ");
        System.out.println("\n--- GUÍA DE SINTAXIS PARA FUNCIONES ---");
        System.out.println("->. Multiplicación: Usar siempre '*' (Ej: 3*x)");
        System.out.println("->. Potencia: Usar '^' (Ej: x^2)");
        System.out.println("->. Seno: Usar 'sin(x)' o 'sen(x)'");
        System.out.println("->. Logaritmo Natural: Usar 'log(x)', Logaritmo Base 10: 'log10(x)'");
        System.out.println("->. Constante e: Usar 'e' (Ej: e^x)");
        System.out.println("->. Paréntesis: Usar para agrupar (Ej: (x+2)/(x-1))");
        System.out.println("----------------------------------------");
        String formula = sc.nextLine();

        Funcion miFuncion = new FuncionDinamica(formula);

        System.out.print("Intervalo x (a): ");
        double a = Double.parseDouble(sc.next().replace(",", "."));

        System.out.print("Intervalo y (b): ");
        double b = Double.parseDouble(sc.next().replace(",", "."));

        System.out.print("El Error (E) con punto decimal: ");
        double errorParada = Double.parseDouble(sc.next().replace(",", "."));

        if (!calculadora.cumpleBolzano(a, b, miFuncion)) {
            System.out.println("No hay cambio de signo en el intervalo");
            return;
        }

        System.out.println("\n1. Bisección | 2. Regla Falsa");
        int modo = sc.nextInt();

        imprimirTabla(a, b, errorParada, modo, calculadora, miFuncion);
    }

    private static void imprimirTabla(double a, double b, double tol, int op, Metodos calc, Funcion f) {
        System.out.printf("\n%-3s | %-7s | %-7s | %-7s | %-8s | %-8s | %-9s | %-8s\n", 
                          "n", "An", "Bn", "Xn", "F(a)", "F(x)", "E(Xn-Xn1)", "Ern%");
        
        double xAnt = 0;
        for (int n = 1; n <= 50; n++) {
            double xn = calc.calcularXn(a, b, op, f);
            double fx = f.evaluar(xn);
            double err = (n == 1) ? 0 : Math.abs(xn - xAnt);
            double eRel = (n == 1) ? 0 : (err / xn) * 100;

            // Formato de 4 decimales
            System.out.printf("%-3d | %-7.4f | %-7.4f | %-7.4f | %-8.4f | %-8.4f | %-9.4f | %-7.2f%%\n", 
                              n, a, b, xn, f.evaluar(a), fx, err, eRel);

            if (n > 1 && err < tol) {
                System.out.println("\nRaiz aproximada encontrada: " + xn);
                break;
            }

            if (f.evaluar(a) * fx > 0) a = xn; else b = xn;
            xAnt = xn;
        }
    }
}