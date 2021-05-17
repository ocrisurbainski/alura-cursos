package br.com.alura.technews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

/**
 * @author Cristian Urbainski
 * @since 1.0 (27/09/20)
 */
class ListaNoticiasViewModel(
    private val repository: NoticiaRepository
) : ViewModel() {

    fun buscaTodos(): LiveData<Resource<List<Noticia>?>> {
        return repository.buscaTodos()
    }

}