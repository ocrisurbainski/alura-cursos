package br.com.alura.ceep.ui.databinding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.alura.ceep.ui.extensions.carregaImagem

/**
 * @author Cristian Urbainski
 * @since 1.0 (23/05/21)
 */
@BindingAdapter("carregaImagem")
fun ImageView.carregaImagem(url: String?) {
    url?.let {
        carregaImagem(url)
    }
}