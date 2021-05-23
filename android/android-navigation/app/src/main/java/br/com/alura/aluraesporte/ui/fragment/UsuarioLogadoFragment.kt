package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.NavGraphDirections
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @author Cristian Urbainski
 * @since 1.0 (22/05/21)
 */
abstract class UsuarioLogadoFragment : Fragment() {

    protected val navController by lazy { findNavController() }
    private val loginViewModel: LoginViewModel by viewModel()
    protected val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (!loginViewModel.isLogado()) {

            openLoginFragment()

            return
        }

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        estadoAppViewModel.hasComponentesVisuais = ComponentesVisuais(hasAppBar = true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_usuario_logado_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_usuario_logado_sair_app -> {
                deslogar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deslogar() {

        loginViewModel.deslogar()

        openLoginFragment()
    }

    private fun openLoginFragment() {

        val direction = NavGraphDirections.actionGlobalLoginFragment()

        navController.navigate(direction)
    }

}