package unlar.edu.ar.objets;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FuncionDinamica implements Funcion {
    private Expression e;

    public FuncionDinamica(String expresionTexto) {
        // Limpiamos el texto igual que antes
        String limpia = expresionTexto.toLowerCase()
                                      .replace("sen", "sin")
                                      .replace(",", ".");
        
        // Construimos la expresión UNA sola vez aquí
        this.e = new ExpressionBuilder(limpia)
                    .variable("x")
                    .build();
    }

    @Override
    public double evaluar(double x) {
        // Solo asignamos el valor de x y evaluamos
        return e.setVariable("x", x).evaluate();
    }
}