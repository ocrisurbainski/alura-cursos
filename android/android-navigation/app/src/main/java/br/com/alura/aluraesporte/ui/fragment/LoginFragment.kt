package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.databinding.LoginBinding
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @author Cristian Urbainski
 * @since 1.0 (18/05/21)
 */
class LoginFragment : Fragment() {

    private val navController by lazy {
        findNavController()
    }
    private lateinit var binding: LoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        estadoAppViewModel.hasComponentesVisuais = ComponentesVisuais()

        binding.loginBotaoLogar.setOnClickListener {

            loginViewModel.logar()

            openListaProdutoFragment()
        }

        binding.loginBotaoCadastrarUsuario.setOnClickListener {

            val direction = LoginFragmentDirections.actionLoginFragmentToCadastroUsuarioFragment()

            navController.navigate(direction)
        }
    }

    private fun openListaProdutoFragment() {

        val direction = LoginFragmentDirections.actionLoginFragmentToListaProdutosFragment()

        navController.navigate(direction)
    }

}