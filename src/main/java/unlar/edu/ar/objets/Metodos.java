package unlar.edu.ar.objets;

public class Metodos {
    
    public double calcularXn(double a, double b, int opcion, Funcion f) {
        if (opcion == 1) {
            return (a + b) / 2.0; // Bisección 
        } else {
            double fa = f.evaluar(a);
            double fb = f.evaluar(b);
            return (fa * b - fb * a) / (fa - fb); // Regla Falsa 
        }
    }

    public boolean cumpleBolzano(double a, double b, Funcion f) {
        return f.evaluar(a) * f.evaluar(b) < 0; 
    }
}