package br.com.alura.leilao.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.alura.leilao.exception.LanceMenorMaiorLanceException;
import br.com.alura.leilao.exception.UsuarioDeuCindoLancesException;
import br.com.alura.leilao.exception.UsuarioLancesSeguidosException;

import junit.framework.Assert;

/**
 * @author Cristian Urbainski
 * @since 1.0 (18/09/20)
 */
public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private Leilao leilao;
    private Usuario user1;
    private Usuario user2;
    private Usuario user3;

    @Before
    public void initLeilao() {

        user1 = new Usuario("User 1");
        user2 = new Usuario("User 2");
        user3 = new Usuario("User 3");

        leilao = new Leilao("Carro usado");
    }

    @Test
    public void deve_devolverTresMaioresLances_quantoTemMaisTresLances() {

        leilao.addLance(new Lance(user1, 200.0));
        leilao.addLance(new Lance(user2, 300.0));
        leilao.addLance(new Lance(user3, 400.0));
        leilao.addLance(new Lance(new Usuario("Usuario 4"), 500.0));
        leilao.addLance(new Lance(new Usuario("Usuario 5"), 650.0));
        leilao.addLance(new Lance(new Usuario("Usuario 6"), 800.0));

        List<Lance> tresMaioresLances = leilao.getTresMaioresLances();

        Assert.assertNotNull(tresMaioresLances);
        Assert.assertEquals(3, tresMaioresLances.size());
        Assert.assertEquals(800.0, tresMaioresLances.get(0).getValor(), DELTA);
        Assert.assertEquals(650.0, tresMaioresLances.get(1).getValor(), DELTA);
        Assert.assertEquals(500.0, tresMaioresLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_quantoRecebeExatoTresLances() {

        leilao.addLance(new Lance(user1, 200.0));
        leilao.addLance(new Lance(user2, 300.0));
        leilao.addLance(new Lance(user3, 400.0));

        List<Lance> tresMaioresLances = leilao.getTresMaioresLances();

        Assert.assertNotNull(tresMaioresLances);
        Assert.assertEquals(3, tresMaioresLances.size());
        Assert.assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
        Assert.assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
        Assert.assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_quantoRecebeDoisLances() {

        leilao.addLance(new Lance(user1, 300.0));
        leilao.addLance(new Lance(user2, 500.0));

        List<Lance> tresMaioresLances = leilao.getTresMaioresLances();

        Assert.assertNotNull(tresMaioresLances);
        Assert.assertEquals(2, tresMaioresLances.size());
        Assert.assertEquals(500.0, tresMaioresLances.get(0).getValor(), DELTA);
        Assert.assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_quantoRecebeUmLances() {

        leilao.addLance(new Lance(user1, 500.0));

        List<Lance> tresMaioresLances = leilao.getTresMaioresLances();

        Assert.assertNotNull(tresMaioresLances);
        Assert.assertEquals(1, tresMaioresLances.size());
        Assert.assertEquals(500.0, tresMaioresLances.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_devolverTresMaioresLances_quantoNaoTemLances() {

        List<Lance> tresMaioresLances = leilao.getTresMaioresLances();

        Assert.assertNotNull(tresMaioresLances);
        Assert.assertEquals(0, tresMaioresLances.size());
    }

    @Test
    public void deve_devolverMaiorLance_quandoRecebeLances() {

        leilao.addLance(new Lance(user1, 100.0));
        leilao.addLance(new Lance(user2, 250.0));
        leilao.addLance(new Lance(user3, 500.0));

        Assert.assertEquals(500.0, leilao.getMaiorLance(), DELTA);
    }

    @Test
    public void deve_devolverMaiorLanceFormatado_quandoRecebeLances() {

        leilao.addLance(new Lance(user1, 100.0));
        leilao.addLance(new Lance(user2, 250.0));
        leilao.addLance(new Lance(user3, 500.0));

        Assert.assertEquals("R$ 500,00", leilao.getMaiorLanceFormatado());
    }

    @Test
    public void deve_devolverMaiorLanceZero_quandoNaoTemLances() {

        Assert.assertEquals(0.0, leilao.getMaiorLance(), DELTA);
    }

    @Test
    public void deve_devolverMaiorLanceFormatadoZero_quandoNaoTemLances() {

        Assert.assertEquals("R$ 0,00", leilao.getMaiorLanceFormatado());
    }

    @Test
    public void deve_devolverMenorLance_quandoRecebeLances() {

        leilao.addLance(new Lance(user1, 100.0));
        leilao.addLance(new Lance(user2, 250.0));
        leilao.addLance(new Lance(user3, 500.0));

        Assert.assertEquals(100.0, leilao.getMenorLance(), DELTA);
    }

    @Test
    public void deve_devolverMenorLanceFormatado_quandoRecebeLances() {

        leilao.addLance(new Lance(user1, 100.0));
        leilao.addLance(new Lance(user2, 250.0));
        leilao.addLance(new Lance(user3, 500.0));

        Assert.assertEquals("R$ 100,00", leilao.getMenorLanceFormatado());
    }

    @Test
    public void deve_devolverMenorLanceZero_quandoNaoTemLances() {

        Assert.assertEquals(0.0, leilao.getMenorLance(), DELTA);
    }

    @Test
    public void deve_devolverMenorLanceFormatadoZero_quandoNaoTemLances() {

        Assert.assertEquals("R$ 0,00", leilao.getMenorLanceFormatado());
    }

    @Test(expected = LanceMenorMaiorLanceException.class)
    public void naoDeve_adicionarLance_quandoForMenorQueMaiorLance() {

        leilao.addLance(new Lance(user1, 500.0));
        leilao.addLance(new Lance(user2, 400.0));
    }

    @Test(expected = UsuarioLancesSeguidosException.class)
    public void naoDeve_adicionarLance_quandoMesmoUsuarioFazerDoisLancesSeguidos() {

        leilao.addLance(new Lance(user1, 500.0));
        leilao.addLance(new Lance(user1, 600.0));
    }

    @Test(expected = UsuarioDeuCindoLancesException.class)
    public void naoDeve_adicionarLance_quandoUsuarioJaTiverDadoCindoLances() {

        leilao.addLance(new Lance(user1, 100.0));
        leilao.addLance(new Lance(user2, 200.0));
        leilao.addLance(new Lance(user1, 300.0));
        leilao.addLance(new Lance(user2, 400.0));
        leilao.addLance(new Lance(user1, 500.0));
        leilao.addLance(new Lance(user2, 600.0));
        leilao.addLance(new Lance(user1, 700.0));
        leilao.addLance(new Lance(user2, 800.0));
        leilao.addLance(new Lance(user1, 900.0));
        leilao.addLance(new Lance(user2, 1000.0));
        leilao.addLance(new Lance(user1, 1100.0));
    }

    @Test
    public void deve_devolverDescricao_sempre() {

        Assert.assertEquals("Carro usado", leilao.getDescricao());
    }

    @Test
    public void deve_devolverTamanhoListaLances_sempre() {

        leilao.addLance(new Lance(user1, 2500.00));
        leilao.addLance(new Lance(user2, 2600.00));
        leilao.addLance(new Lance(user3, 2800.00));

        Assert.assertEquals(3, leilao.getLances().size());
    }

}