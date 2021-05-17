package br.com.alura.technews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.asynctask.BaseAsyncTask
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient

class NoticiaRepository(
    private val dao: NoticiaDAO,
    private val webclient: NoticiaWebClient
) {

    private val noticiasMediator = MediatorLiveData<Resource<List<Noticia>?>>()

    fun buscaTodos(): LiveData<Resource<List<Noticia>?>> {

        noticiasMediator.addSource(buscaInterno()) { noticias ->
            noticiasMediator.value = Resource(dado = noticias)
        }

        val falhasDaWebApiLiveData = MutableLiveData<Resource<List<Noticia>?>>()

        noticiasMediator.addSource(falhasDaWebApiLiveData) { resourceDeFalha ->

            val resourceAtual = noticiasMediator.value
            val resourceNovo: Resource<List<Noticia>?> = if (resourceAtual != null) {
                Resource(dado = resourceAtual.dado, erro = resourceDeFalha.erro)
            } else {
                resourceDeFalha
            }

            noticiasMediator.value = resourceNovo
        }

        buscaNaApi(quandoFalha = { erro ->
            falhasDaWebApiLiveData.value = Resource(dado = null, erro = erro)
        })

        return noticiasMediator
    }

    fun salva(
        noticia: Noticia
    ): LiveData<Resource<Noticia?>> {

        var liveData = MutableLiveData<Resource<Noticia?>>()

        salvaNaApi(noticia, quandoSucesso = {

            liveData.value = Resource(dado = null)
        }, quandoFalha = { erro ->

            liveData.value = Resource(dado = null, erro = erro)
        })

        return liveData
    }

    fun remove(
        noticia: Noticia
    ): LiveData<Resource<Void?>> {

        var liveData = MutableLiveData<Resource<Void?>>()

        removeNaApi(noticia, quandoSucesso = {

            liveData.value = Resource(dado = null)
        }, quandoFalha = { erro ->

            liveData.value = Resource(dado = null, erro = erro)
        })

        return liveData
    }

    fun edita(
        noticia: Noticia
    ): LiveData<Resource<Noticia?>> {

        var liveData = MutableLiveData<Resource<Noticia?>>()

        editaNaApi(noticia, quandoSucesso = {

            liveData.value = Resource(dado = null)
        }, quandoFalha = { erro ->

            liveData.value = Resource(dado = null, erro = erro)
        })

        return liveData
    }

    fun buscaPorId(noticiaId: Long) = dao.buscaPorId(noticiaId)

    private fun buscaNaApi(
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.buscaTodas(
            quandoSucesso = { noticiasNovas ->
                noticiasNovas?.let {
                    salvaInterno(noticiasNovas)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno(): LiveData<List<Noticia>> {
        return dao.buscaTodos()
    }

    private fun salvaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.salva(
            noticia,
            quandoSucesso = {
                it?.let { noticiaSalva ->
                    salvaInterno(noticiaSalva, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun salvaInterno(
        noticias: List<Noticia>
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticias)
        }).execute()
    }

    private fun salvaInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun removeNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.remove(
            noticia.id,
            quandoSucesso = {
                removeInterno(noticia, quandoSucesso)
            },
            quandoFalha = quandoFalha
        )
    }

    private fun removeInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun editaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.edita(
            noticia.id, noticia,
            quandoSucesso = { noticiaEditada ->
                noticiaEditada?.let {
                    salvaInterno(noticiaEditada, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

}
