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
class FormularioNoticiaViewModel(
    private val repository: NoticiaRepository
): ViewModel() {

    fun salva(noticia: Noticia): LiveData<Resource<Noticia?>> {

        if (isEdit(noticia)) {

            return repository.edita(noticia);
        }

        return repository.salva(noticia)
    }

    fun buscaPorId(noticiaId: Long) = repository.buscaPorId(noticiaId)

    private fun isEdit(
        noticia: Noticia
    ): Boolean = noticia.id > 0

}