package br.com.alura.leilao.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {

    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock
    private LeilaoWebClient client;
    @Mock
    private AtualizadorDeLeiloes.ErroCarregarLeiloesListener listener;
    private AtualizadorDeLeiloes atualizadorDeLeiloes = new AtualizadorDeLeiloes();

    @Test
    public void deve_atualizarListaDeLeiloes_quandoBuscarLeiloesDaApi() {

        Mockito.doAnswer(invocation -> {

            RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
            listener.sucesso(new ArrayList<>(Arrays.asList(
                    new Leilao("Console"),
                    new Leilao("Moto usada")
            )));

            return null;
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizadorDeLeiloes.buscaLeiloes(client, adapter, listener);

        Mockito.verify(client).todos(ArgumentMatchers.any(RespostaListener.class));
        Mockito.verify(adapter).atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Moto usada")
        )));
    }

    @Test
    public void deve_apresentarMensagemFalha_quandoFalharBuscaDeLeiloes() {

        Mockito.doAnswer(invocation -> {

            RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
            listener.falha("NotFound");

            return null;
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizadorDeLeiloes.buscaLeiloes(client, adapter, listener);

        Mockito.verify(listener).falha(Mockito.anyString());
    }

}