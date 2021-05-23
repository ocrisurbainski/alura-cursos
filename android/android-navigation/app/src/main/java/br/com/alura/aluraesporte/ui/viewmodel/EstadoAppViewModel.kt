package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Cristian Urbainski
 * @since 1.0 (23/05/21)
 */
class EstadoAppViewModel : ViewModel() {

    private var _componentesVisuais: MutableLiveData<ComponentesVisuais> =
        MutableLiveData<ComponentesVisuais>().also {
            it.value = hasComponentesVisuais
        }

    val componentesVisuais: LiveData<ComponentesVisuais> get() = _componentesVisuais

    var hasComponentesVisuais: ComponentesVisuais = ComponentesVisuais()
        set(value) {
            field = value
            _componentesVisuais.value = value
        }

}

class ComponentesVisuais(
    val hasAppBar: Boolean = false,
    val hasBottomNavigation: Boolean = false
)