package br.com.alura.leilao.exception;

/**
 * @author Cristian Urbainski
 * @since 1.0 (19/09/20)
 */
public class LanceMenorMaiorLanceException extends RuntimeException {

    public LanceMenorMaiorLanceException(Double novoLanceValor, Double maiorLanceValor) {

        super(String.format(
                "Valor do novo lance: %1$,.2f não pode ser menor o maior lance: %2$,.2f",
                novoLanceValor, maiorLanceValor));
    }

}