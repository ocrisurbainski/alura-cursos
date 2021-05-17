package br.com.alura.leilao.ui.recyclerview.adapter;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.model.Leilao;

import android.content.Context;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    @Mock
    private Context context;

    @Spy
    private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(context);

    @Test
    public void deve_atualizarListaDeLeiloes_quandoReceberListaDeLeiloes() {

        Mockito.doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidade = adapter.getItemCount();

        Mockito.verify(adapter).atualizaLista();

        Assert.assertEquals(2, quantidade);
    }

}