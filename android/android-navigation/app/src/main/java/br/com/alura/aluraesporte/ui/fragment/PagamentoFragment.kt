package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.databinding.PagamentoBinding
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Pagamento
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val COMPRA_REALIZADA = "Compra realizada"
private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"

class PagamentoFragment : UsuarioLogadoFragment() {

    private val pagamentoFragmentArgs by navArgs<PagamentoFragmentArgs>()
    private val produtoId by lazy {
        pagamentoFragmentArgs.produtoId
    }
    private val viewModel: PagamentoViewModel by viewModel()
    private lateinit var produtoEscolhido: Produto
    private lateinit var binding: PagamentoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PagamentoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraBotaoConfirmaPagamento()
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.buscaProdutoPorId(produtoId).observe(this, Observer {
            it?.let { produtoEncontrado ->
                produtoEscolhido = produtoEncontrado
                binding.pagamentoPreco.text = produtoEncontrado.preco.formatParaMoedaBrasileira()
            }
        })
    }

    private fun configuraBotaoConfirmaPagamento() {
        binding.pagamentoBotaoConfirmaPagamento.setOnClickListener {
            criaPagamento()?.let(this::salva) ?: Toast.makeText(
                context,
                FALHA_AO_CRIAR_PAGAMENTO,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun salva(pagamento: Pagamento) {
        if (::produtoEscolhido.isInitialized) {
            viewModel.salva(pagamento)
                .observe(this, Observer {
                    it?.dado?.let {
                        Toast.makeText(context, COMPRA_REALIZADA, Toast.LENGTH_SHORT).show()

                        val direction =
                            PagamentoFragmentDirections.actionPagamentoFragmentToListaProdutosFragment()

                        navController.navigate(direction)
                    }
                })
        }
    }

    private fun criaPagamento(): Pagamento? {
        val numeroCartao = binding.pagamentoNumeroCartao.editText?.text.toString()

        val dataValidade = binding.pagamentoDataValidade.editText?.text.toString()

        val cvc = binding.pagamentoCvc.editText?.text.toString()

        return geraPagamento(numeroCartao, dataValidade, cvc)
    }

    private fun geraPagamento(
        numeroCartao: String,
        dataValidade: String,
        cvc: String
    ): Pagamento? = try {
        Pagamento(
            numeroCartao = numeroCartao.toInt(),
            dataValidade = dataValidade,
            cvc = cvc.toInt(),
            produtoId = produtoId,
            preco = produtoEscolhido.preco
        )
    } catch (e: NumberFormatException) {
        null
    }

}