package br.com.alura.leilao.ui.activity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.alura.leilao.R;
import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;
import br.com.alura.leilao.ui.dialog.NovoLanceDialog;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/05/21)
 */
public class LancesLeilaoTelaTest extends BaseTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity = new ActivityTestRule<>(
            ListaLeilaoActivity.class, true, false);
    private final FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();

    @Before
    public void setup() throws IOException {

        limpaBancoDeDadosDaApi();
        limpaBancoDeDadosInterno();
    }

    @Test
    public void deve_AtualizarLanceDoLeilao_QuandoReceberUmLance() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("200", new Usuario(1, "Cristian"));

        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(200.0)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(200.0)), isDisplayed())));

        StringBuilder messageText = new StringBuilder()
                .append(formatadorDeMoeda.formata(200.0) + " - (1) Cristian\n");

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(messageText.toString()), isDisplayed())));
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuantoReceberTresLances() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"), new Usuario(2, "Joeli"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("200", new Usuario(1, "Cristian"));

        proporLance("250", new Usuario(2, "Joeli"));

        proporLance("300", new Usuario(1, "Cristian"));

        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(300.0)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(200.0)), isDisplayed())));

        StringBuilder messageText = new StringBuilder()
                .append(formatadorDeMoeda.formata(300.0) + " - (1) Cristian\n")
                .append(formatadorDeMoeda.formata(250.0) + " - (2) Joeli\n")
                .append(formatadorDeMoeda.formata(200.0) + " - (1) Cristian\n");

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(messageText.toString()), isDisplayed())));
    }

    @Test
    public void deve_ApresentarMensagem_QuantoReceberDoisLancesDoMesmoUsuario() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("200", new Usuario(1, "Cristian"));

        proporLance("250", new Usuario(1, "Cristian"));

        onView(
                allOf(
                        withId(android.R.id.message),
                        withText(AvisoDialogManager.MENSAGEM_AVISO_LANCE_SEGUIDO_MESMO_USUARIO)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_ApresentarMensagem_QuantoReceberMaisDeCincoLancesDoMesmoUsuario() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"), new Usuario(2, "Joeli"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("200", new Usuario(1, "Cristian"));

        proporLance("250", new Usuario(2, "Joeli"));

        proporLance("300", new Usuario(1, "Cristian"));

        proporLance("350", new Usuario(2, "Joeli"));

        proporLance("400", new Usuario(1, "Cristian"));

        proporLance("450", new Usuario(2, "Joeli"));

        proporLance("500", new Usuario(1, "Cristian"));

        proporLance("550", new Usuario(2, "Joeli"));

        proporLance("600", new Usuario(1, "Cristian"));

        proporLance("650", new Usuario(2, "Joeli"));

        proporLance("700", new Usuario(1, "Cristian"));

        onView(
                allOf(
                        withId(android.R.id.message),
                        withText(AvisoDialogManager.MENSAGEM_AVISO_JA_DEU_CINCO_LANCES)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_ApresentarMensagem_QuantoReceberUmLanceMenorQueUltimoLance() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"), new Usuario(2, "Joeli"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("200", new Usuario(1, "Cristian"));

        proporLance("150", new Usuario(2, "Joeli"));

        onView(
                allOf(
                        withId(android.R.id.message),
                        withText(AvisoDialogManager.MENSAGEM_AVISO_LANCE_MENOR_QUE_ULTIMO_LANCE)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void deve_ApresentarMensangem_QuantoTentarProporUmLanceSemUsuarioCadastrado() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        adicionarLanceLeilaoClick();

        onView(allOf(withId(R.id.alertTitle), withText(NovoLanceDialog.USUARIOS_NAO_ENCONTRADOS)))
                .check(matches(isDisplayed()));

        onView(allOf(withId(android.R.id.message), withText(NovoLanceDialog.MENSAGEM_NAO_EXISTE_USUARIOS_CADASTRADOS)))
                .check(matches(isDisplayed()));

        onView(withText(NovoLanceDialog.CADASTRAR_USUARIO)).check(matches(isDisplayed()));
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuantoReceberUmLanceComValorMuitoAlto() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        adicionarUsuario(new Usuario(1, "Cristian"));

        mainActivity.launchActivity(new Intent());

        recyclerViewClickAtPostion(0);

        proporLance("2000000000", new Usuario(1, "Cristian"));

        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(2000000000.0)), isDisplayed())));

        onView(withId(R.id.lances_leilao_menor_lance))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(2000000000.0)), isDisplayed())));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(allOf(withText(formatadorDeMoeda.formata(2000000000.0) + " - (1) Cristian\n"), isDisplayed())));
    }

    private void recyclerViewClickAtPostion(int position) {

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));
    }

    private void adicionarLanceLeilaoClick() {

        onView(withId(R.id.lances_leilao_fab_adiciona)).check(matches(isDisplayed())).perform(click());
    }

    private void proporLance(String lance, Usuario usuario) {

        adicionarLanceLeilaoClick();

        onView(allOf(withId(R.id.alertTitle), withText(NovoLanceDialog.TITULO))).check(matches(isDisplayed()));

        onView(withId(R.id.form_lance_valor)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.form_lance_valor_edittext))
                .check(matches(isDisplayed()))
                .perform(typeText(lance), closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(is(usuario))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withText(NovoLanceDialog.DESCRICAO_BOTAO_POSITIVO))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    private void adicionarUsuario(Usuario... usuarios) {

        UsuarioDAO dao = new UsuarioDAO(InstrumentationRegistry.getTargetContext());

        for (Usuario usuario : usuarios) {

            Usuario usuarioSalvo = dao.salva(usuario);

            if (usuarioSalvo == null) {

                Assert.fail();
            }
        }
    }

    @After
    public void tearDown() throws IOException {

        limpaBancoDeDadosDaApi();
        limpaBancoDeDadosInterno();
    }

}