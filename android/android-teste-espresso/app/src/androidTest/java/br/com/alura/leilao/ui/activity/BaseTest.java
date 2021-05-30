package br.com.alura.leilao.ui.activity;

import java.io.IOException;

import org.junit.Assert;

import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/05/21)
 */
public abstract class BaseTest {

    private static final String LEILAO_NAO_FOI_SALVO = "Leilão não foi salvo: ";
    private static final String ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API = "Banco de dados não foi limpo";
    private final TesteWebClient webClient = new TesteWebClient();

    protected void tentaSalvarLeilaoNaApi(Leilao... leiloes) throws IOException {

        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                Assert.fail(LEILAO_NAO_FOI_SALVO + leilao.getDescricao());
            }
        }
    }

    protected void limpaBancoDeDadosDaApi() throws IOException {

        boolean bancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();
        if (bancoDeDadosNaoFoiLimpo) {
            Assert.fail(ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API);
        }
    }

    protected void limpaBancoDeDadosInterno() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(BuildConfig.BANCO_DE_DADOS);
    }

}
