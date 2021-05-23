package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.LoginRepository

/**
 * @author Cristian Urbainski
 * @since 1.0 (22/05/21)
 */
class LoginViewModel(private val repository: LoginRepository): ViewModel() {

    fun logar() = repository.logar()

    fun deslogar() = repository.deslogar()

    fun isLogado() = repository.isLogado()

}