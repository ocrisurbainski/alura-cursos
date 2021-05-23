package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.databinding.ItemPagamentoBinding
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Pagamento

/**
 * @author Cristian Urbainski
 * @since 1.0 (23/05/21)
 */
class PagamentoAdapter(
    private val context: Context,
    pagamentos: List<Pagamento> = listOf()
): RecyclerView.Adapter<PagamentoAdapter.ViewHolder>() {

    private val pagamentos = pagamentos.toMutableList()
    private val inflater: LayoutInflater by lazy {

        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var binding: ItemPagamentoBinding = ItemPagamentoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pagamento = pagamentos[position]
        holder.bind(pagamento)
    }

    override fun getItemCount(): Int = pagamentos.size

    fun add(pagamentos: List<Pagamento>) {

        notifyItemRangeRemoved(0, this.pagamentos.size)

        this.pagamentos.clear()
        this.pagamentos.addAll(pagamentos)

        notifyItemRangeInserted(0, this.pagamentos.size)
    }

    class ViewHolder(private val binding: ItemPagamentoBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(pagamento: Pagamento) {

            binding.itemPagamentoId.text = pagamento.id.toString()
            binding.itemPagamentoPreco.text = pagamento.preco.formatParaMoedaBrasileira()
        }

    }
}