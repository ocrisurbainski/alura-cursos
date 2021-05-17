package br.com.alura.leilao.exception;

/**
 * @author Cristian Urbainski
 * @since 1.0 (19/09/20)
 */
public class UsuarioLancesSeguidosException extends RuntimeException {

    public UsuarioLancesSeguidosException() {

        super("Um usuário não pode dar dois lances seguidos");
    }

}