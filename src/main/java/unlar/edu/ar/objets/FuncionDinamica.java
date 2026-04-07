package unlar.edu.ar.objets;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FuncionDinamica implements Funcion {
    private String expresionTexto;

    public FuncionDinamica(String expresionTexto) {
        this.expresionTexto = expresionTexto.toLowerCase()
                                        .replace("sen", "sin")
                                        .replace(",", ".");
    }

    @Override
    public double evaluar(double x) {
        Expression e = new ExpressionBuilder(expresionTexto)
            .variable("x")
            .build()
            .setVariable("x", x);
        return e.evaluate();
    }
}