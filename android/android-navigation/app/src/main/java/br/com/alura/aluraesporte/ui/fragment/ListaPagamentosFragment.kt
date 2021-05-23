package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.aluraesporte.databinding.ListaPagamentosBinding
import br.com.alura.aluraesporte.ui.recyclerview.adapter.PagamentoAdapter
import br.com.alura.aluraesporte.ui.viewmodel.ComponentesVisuais
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @author Cristian Urbainski
 * @since 1.0 (23/05/21)
 */
class ListaPagamentosFragment : UsuarioLogadoFragment() {

    private lateinit var binding: ListaPagamentosBinding
    private val adapter: PagamentoAdapter by inject()
    private val pagamentoViewModel: PagamentoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ListaPagamentosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        estadoAppViewModel.hasComponentesVisuais = ComponentesVisuais(
            hasAppBar = true,
            hasBottomNavigation = true
        )

        pagamentoViewModel.findAllPagamentos().observe(this, Observer {
            it?.let {
                adapter.add(it)

                when (it.size) {
                    0 -> {
                        binding.listaPagamentosTextNenhumPagamentoEncontrado.visibility = VISIBLE
                        binding.listaPagamentosRecyclerview.visibility = GONE
                    }
                    else -> {
                        binding.listaPagamentosTextNenhumPagamentoEncontrado.visibility = GONE
                        binding.listaPagamentosRecyclerview.visibility = VISIBLE
                    }
                }
            }
        })

        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {

        val recyclerView = binding.listaPagamentosRecyclerview

        val divider = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(divider)
    }

}