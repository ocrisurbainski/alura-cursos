package br.com.alura.technews.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment
import kotlinx.android.synthetic.main.activity_noticias.*

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) {
            abreListaNoticias()
        } else {
            supportFragmentManager.findFragmentByTag(VisualizaNoticiaFragment::class.java.simpleName)?.let { fragment ->

                fragment.arguments?.getLong(NOTICIA_ID_CHAVE).let { idNoticia ->

                    removeFragment(fragment)

                    if (idNoticia != null) {

                        val noticia = Noticia(id = idNoticia)

                        abreVisualizadorNoticia(noticia)
                    }
                }
            }
        }
    }

    private fun abreListaNoticias() {
        transacaoFragment {
            replace(R.id.activity_noticias_primario, ListaNoticiasFragment())
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when(fragment){
            is ListaNoticiasFragment -> {
                configuraListaNoticias(fragment)
            }
            is VisualizaNoticiaFragment -> {
                configuraVisualizaNoticia(fragment)
            }
        }
    }

    private fun configuraVisualizaNoticia(fragment: VisualizaNoticiaFragment) {
        fragment.quandoFinalizaTela = {
            supportFragmentManager.findFragmentByTag(VisualizaNoticiaFragment::class.java.simpleName)?.let { fragment ->

                removeFragment(fragment)
            }
        }
        fragment.quandoSelecionaMenuEdicao = this::abreFormularioEdicao
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.quandoNoticiaSeleciona = this::abreVisualizadorNoticia
        fragment.quandoFabSalvaNoticiaClicado = this::abreFormularioModoCriacao
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)

        val fragment = VisualizaNoticiaFragment()
        fragment.arguments = dados

        transacaoFragment {

            val container = if (activity_noticias_secundario != null) {
                R.id.activity_noticias_secundario
            } else {
                addToBackStack(null)
                R.id.activity_noticias_primario
            }

            replace(container, fragment, VisualizaNoticiaFragment::class.java.simpleName)
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

    private fun removeFragment(fragment: Fragment) {

        transacaoFragment {

            remove(fragment)
        }

        supportFragmentManager.popBackStack()
    }

}
