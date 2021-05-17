package br.com.alura.leilao.api;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorMaiorLanceException;
import br.com.alura.leilao.exception.UsuarioDeuCindoLancesException;
import br.com.alura.leilao.exception.UsuarioLancesSeguidosException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

public class EnviadorDeLance {

    private final LeilaoWebClient client;
    private final LanceProcessadoListener listener;
    private final AvisoDialogManager avisoDialogManager;

    public EnviadorDeLance(LeilaoWebClient client,
            LanceProcessadoListener listener,
            AvisoDialogManager avisoDialogManager) {

        this.client = client;
        this.listener = listener;
        this.avisoDialogManager = avisoDialogManager;
    }

    public void envia(final Leilao leilao, Lance lance) {

        try {

            leilao.addLance(lance);

            client.propoe(lance, leilao.getId(), new RespostaListener<Void>() {
                @Override
                public void sucesso(Void resposta) {

                    listener.processado(leilao);
                }

                @Override
                public void falha(String mensagem) {

                    avisoDialogManager.mostraToastFalhaNoEnvio();
                }
            });

        } catch (LanceMenorMaiorLanceException exception) {

            avisoDialogManager.mostraAvisoLanceMenorQueUltimoLance();

        } catch (UsuarioLancesSeguidosException exception) {

            avisoDialogManager.mostraAvisoLanceSeguidoDoMesmoUsuario();

        } catch (UsuarioDeuCindoLancesException exception) {

            avisoDialogManager.mostraAvisoUsuarioJaDeuCincoLances();

        }
    }

    public interface LanceProcessadoListener {

        void processado(Leilao leilao);
    }

}