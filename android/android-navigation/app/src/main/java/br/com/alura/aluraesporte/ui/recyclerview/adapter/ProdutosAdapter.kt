package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.ItemProdutoBinding
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Produto

class ProdutosAdapter(
    private val context: Context,
    private val produtos: MutableList<Produto> = mutableListOf(),
    var onItemClickListener: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ProdutosAdapter.ViewHolder>() {

    private val inflater: LayoutInflater by lazy {

        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding = ItemProdutoBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(produtos[position])
    }

    fun atualiza(produtosNovos: List<Produto>) {
        notifyItemRangeRemoved(0, produtos.size)
        produtos.clear()
        produtos.addAll(produtosNovos)
        notifyItemRangeInserted(0, produtos.size)
    }

    inner class ViewHolder(binding: ItemProdutoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto
        private val campoNome by lazy { binding.itemProdutoNome }
        private val campoPreco by lazy { binding.itemProdutoPreco }

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    onItemClickListener(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            campoNome.text = produto.nome
            campoPreco.text = produto.preco.formatParaMoedaBrasileira()
        }

    }

}
