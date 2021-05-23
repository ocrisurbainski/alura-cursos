package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.databinding.ListaProdutosBinding
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListaProdutosFragment : UsuarioLogadoFragment() {

    private val produtosViewModel: ProdutosViewModel by viewModel()
    private val adapter: ProdutosAdapter by inject()
    private lateinit var binding : ListaProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        buscaProdutos()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListaProdutosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuraRecyclerView()

        estadoAppViewModel.hasComponentesVisuais = ComponentesVisuais(
            hasAppBar = true,
            hasBottomNavigation = true
        )
    }

    private fun buscaProdutos() {
        produtosViewModel.buscaTodos().observe(this, Observer { produtosEncontrados ->
            produtosEncontrados?.let {
                adapter.atualiza(it)
            }
        })
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        binding.listaProdutosRecyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = { produtoSelecionado ->

            val direction =
                ListaProdutosFragmentDirections.actionListaProdutosFragmentToDetalhesProdutoFragment(
                    produtoSelecionado.id
                )

            navController.navigate(direction)
        }
        binding.listaProdutosRecyclerview.adapter = adapter
    }

}