package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.databinding.CadastroUsuarioBinding
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.EstadoAppViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * @author Cristian Urbainski
 * @since 1.0 (22/05/21)
 */
class CadastroUsuarioFragment : Fragment() {

    private val navController by lazy {
        findNavController()
    }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private lateinit var binding: CadastroUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CadastroUsuarioBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        estadoAppViewModel.hasComponentesVisuais = ComponentesVisuais(
            hasAppBar = true
        )

        binding.cadastroUsuarioBotaoCadastrar.setOnClickListener {

            navController.popBackStack()
        }
    }

}