package br.com.alura.leilao.exception;

/**
 * @author Cristian Urbainski
 * @since 1.0 (19/09/20)
 */
public class UsuarioDeuCindoLancesException extends RuntimeException {

    public UsuarioDeuCindoLancesException() {

        super("São permitidos até 5 lances por usuário em cada leilão");
    }

}
