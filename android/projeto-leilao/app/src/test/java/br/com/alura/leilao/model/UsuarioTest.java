package br.com.alura.leilao.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristian Urbainski
 * @since 1.0 (18/09/20)
 */
public class UsuarioTest {

    @Test
    public void test_nome() {

        Usuario usuario = new Usuario("Juvenal");

        Assert.assertEquals("Juvenal", usuario.getNome());
    }

}