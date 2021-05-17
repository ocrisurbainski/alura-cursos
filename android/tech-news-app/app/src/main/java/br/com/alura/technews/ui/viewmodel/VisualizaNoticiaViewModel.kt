package br.com.alura.technews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

/**
 * @author Cristian Urbainski
 * @since 1.0 (28/09/20)
 */
class VisualizaNoticiaViewModel(
    noticiaId: Long,
    private val repository: NoticiaRepository
) : ViewModel() {

    val noticia = repository.buscaPorId(noticiaId)

    fun remove(): LiveData<Resource<Void?>> {

        return noticia.value?.run {
            repository.remove(this)
        } ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(dado = null, erro = "Notícia não encontrada")
        }
    }

}