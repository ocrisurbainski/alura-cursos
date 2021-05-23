package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.databinding.DetalhesProdutoBinding
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesProdutoFragment : UsuarioLogadoFragment() {

    private val detalhesProdutoFragmentArgs by navArgs<DetalhesProdutoFragmentArgs>()
    private val produtoId by lazy {
        detalhesProdutoFragmentArgs.produtoId
    }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(produtoId) }
    private lateinit var binding: DetalhesProdutoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetalhesProdutoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscaProduto()
        configuraBotaoComprar()
    }

    private fun configuraBotaoComprar() {
        binding.detalhesProdutoBotaoComprar.setOnClickListener {

            var direction =
                DetalhesProdutoFragmentDirections.actionDetalhesProdutoFragmentToPagamentoFragment(
                    produtoId
                )

            navController.navigate(direction)
        }
    }

    private fun buscaProduto() {
        viewModel.produtoEncontrado.observe(this, Observer {
            it?.let { produto ->
                binding.detalhesProdutoNome.text = produto.nome
                binding.detalhesProdutoPreco.text = produto.preco.formatParaMoedaBrasileira()
            }
        })
    }

}