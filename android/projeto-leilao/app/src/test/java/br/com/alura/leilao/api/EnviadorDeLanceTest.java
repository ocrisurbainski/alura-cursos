package br.com.alura.leilao.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.api.EnviadorDeLance.LanceProcessadoListener;
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorMaiorLanceException;
import br.com.alura.leilao.exception.UsuarioDeuCindoLancesException;
import br.com.alura.leilao.exception.UsuarioLancesSeguidosException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

/**
 * @author Cristian Urbainski
 * @since 1.0 (24/09/20)
 */
@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    private Leilao leilao;
    @Mock
    private LeilaoWebClient client;
    @Mock
    private LanceProcessadoListener listener;
    @Mock
    private AvisoDialogManager avisoDialogManager;

    @Test
    public void deve_mostraAvisoLanceMenorQueUltimoLance_quandoLanceMenorQueUltimoLance() {

        Mockito.doThrow(LanceMenorMaiorLanceException.class).when(leilao).addLance(ArgumentMatchers.any(Lance.class));

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);

        enviadorDeLance.envia(leilao, new Lance(new Usuario("User 1"), 200.0));

        Mockito.verify(avisoDialogManager).mostraAvisoLanceMenorQueUltimoLance();
    }

    @Test
    public void deve_mostraAvisoLanceSeguidoDoMesmoUsuario_quandoUsuarioLancesSeguidosException() {

        Mockito.doThrow(UsuarioLancesSeguidosException.class).when(leilao).addLance(ArgumentMatchers.any(Lance.class));

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);

        enviadorDeLance.envia(leilao, new Lance(new Usuario("User 1"), 200.0));

        Mockito.verify(avisoDialogManager).mostraAvisoLanceSeguidoDoMesmoUsuario();
    }

    @Test
    public void deve_mostraAvisoUsuarioJaDeuCincoLances_quandoUsuarioDeuCindoLances() {

        Mockito.doThrow(UsuarioDeuCindoLancesException.class).when(leilao).addLance(ArgumentMatchers.any(Lance.class));

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);

        enviadorDeLance.envia(leilao, new Lance(new Usuario("User 1"), 200.0));

        Mockito.verify(avisoDialogManager).mostraAvisoUsuarioJaDeuCincoLances();
    }

    @Test
    public void deve_mostraToastFalhaNoEnvio_quantoEnviaLeilaoFalhar() {

        Mockito.doAnswer(invocation -> {

            RespostaListener<Void> listener = invocation.getArgument(2);
            listener.falha("NotFound");

            return null;
        }).when(client).propoe(
                ArgumentMatchers.any(Lance.class),
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(RespostaListener.class));

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(client, listener, avisoDialogManager);

        enviadorDeLance.envia(leilao, new Lance(new Usuario("User 1"), 200.0));

        Mockito.verify(avisoDialogManager).mostraToastFalhaNoEnvio();
    }
}