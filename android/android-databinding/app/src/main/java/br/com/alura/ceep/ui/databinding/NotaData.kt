package br.com.alura.ceep.ui.databinding

import androidx.lifecycle.MutableLiveData
import br.com.alura.ceep.model.Nota

/**
 * @author Cristian Urbainski
 * @since 1.0 (26/05/21)
 */
class NotaData(
    private var nota: Nota = Nota(),
    val titulo: MutableLiveData<String> = MutableLiveData<String>().also { it.value = nota.titulo },
    val descricao: MutableLiveData<String> = MutableLiveData<String>().also { it.value = nota.descricao },
    val favorita: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also { it.value = nota.favorita },
    val imagemUrl: MutableLiveData<String> = MutableLiveData<String>().also { it.value = nota.imagemUrl }
) {
    fun atualiza(nota: Nota) {
        this.nota = nota
        this.titulo.postValue(nota.titulo)
        this.descricao.postValue(nota.descricao)
        this.favorita.postValue(nota.favorita)
        this.imagemUrl.postValue(nota.imagemUrl)
    }

    fun toNota(): Nota? {
        return this.nota.copy(
            titulo = titulo.value ?: return null,
            descricao = descricao.value ?: return null,
            favorita = favorita.value ?: return null,
            imagemUrl =  imagemUrl.value ?: return null
        )
    }

}