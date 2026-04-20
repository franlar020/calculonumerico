package unlar.edu.ar.objets;

public class Metodos {

    // Bisección: (a+b)/2
    public double biseccion(double a, double b) {
        return (a + b) / 2.0;
    }

    // Regla Falsa: (f(a)*b - f(b)*a) / (f(a) - f(b))
    public double reglaFalsa(double a, double b, Funcion f) {
        return (f.evaluar(a) * b - f.evaluar(b) * a) / (f.evaluar(a) - f.evaluar(b));
    }

    // Punto Fijo: Xn+1 = g(Xn) [cite: 11]
    public double puntoFijo(double xn, Funcion g) {
        return g.evaluar(xn);
    }

    // Newton: Xn - f(Xn)/f'(Xn) [cite: 124]
    public double newton(double xn, Funcion f, Funcion fDerivada) {
        return xn - (f.evaluar(xn) / fDerivada.evaluar(xn));
    }

    // Secante: Xn - (f(Xn)*(Xn-Xn_1))/(f(Xn)-f(Xn_1)) [cite: 281]
    public double secante(double xn, double xn_1, Funcion f) {
        double fxn = f.evaluar(xn);
        double fxn_1 = f.evaluar(xn_1);
        return xn - (fxn * (xn - xn_1)) / (fxn - fxn_1);
    }

    public boolean cumpleBolzano(double a, double b, Funcion f) {
        return f.evaluar(a) * f.evaluar(b) < 0;
    }

    // Aproximación de la derivada primera
    public double derivadaNumerica(double x, Funcion f) {
        double h = 1e-6; // Un valor muy pequeño para la precisión
        return (f.evaluar(x + h) - f.evaluar(x)) / h;
    }

    public double newtonAutomatico(double xn, Funcion f) {
        return xn - (f.evaluar(xn) / derivadaNumerica(xn, f));
    }
}