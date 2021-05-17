package br.com.alura.leilao.ui;

import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
public class AtualizadorDeLeiloes {

    public AtualizadorDeLeiloes() {

    }

    public void buscaLeiloes(LeilaoWebClient client, final ListaLeilaoAdapter adapter,
            final ErroCarregarLeiloesListener listener) {

        client.todos(new RespostaListener<List<Leilao>>() {
            @Override
            public void sucesso(List<Leilao> leiloes) {

                adapter.atualiza(leiloes);
            }

            @Override
            public void falha(String mensagem) {

                listener.falha(mensagem);
            }
        });
    }

    public interface ErroCarregarLeiloesListener {

        void falha(String mensagem);
    }

}
