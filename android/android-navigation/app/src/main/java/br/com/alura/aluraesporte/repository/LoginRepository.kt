package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val CHAVE_USUARIO_LOGADO = "USUARIO_LOGADO"

/**
 * @author Cristian Urbainski
 * @since 1.0 (22/05/21)
 */
class LoginRepository(private val preferences: SharedPreferences) {

    fun logar() = edit(CHAVE_USUARIO_LOGADO, true)

    fun deslogar() = edit(CHAVE_USUARIO_LOGADO, false)

    fun isLogado(): Boolean = preferences.getBoolean(CHAVE_USUARIO_LOGADO, false)

    private fun edit(chave: String, value: Boolean) {

        preferences.edit {
            putBoolean(chave, value)
        }
    }

}