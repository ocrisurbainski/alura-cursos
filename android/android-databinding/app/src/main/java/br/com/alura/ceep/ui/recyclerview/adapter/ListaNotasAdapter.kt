package br.com.alura.ceep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.databinding.ItemNotaBinding
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.databinding.NotaData

class ListaNotasAdapter(
    private val context: Context,
    var onItemClickListener: (notaSelecionada: Nota) -> Unit = {}
) : ListAdapter<Nota, ListaNotasAdapter.ViewHolder>(DiffCallback) {

    private val inflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { nota ->
            holder.vincula(nota)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {

        super.onViewAttachedToWindow(holder)

        holder.markStateStarted()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {

        super.onViewDetachedFromWindow(holder)

        holder.markStateDestroyed()
    }

    inner class ViewHolder(private val binding: ItemNotaBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, LifecycleOwner {

        private val registry = LifecycleRegistry(this)
        private lateinit var nota: Nota

        init {
            binding.listener = this
            binding.lifecycleOwner = this
            registry.currentState = Lifecycle.State.INITIALIZED
        }

        fun markStateStarted() {
            registry.currentState = Lifecycle.State.STARTED
        }

        fun markStateDestroyed() {
            registry.currentState = Lifecycle.State.DESTROYED
        }

        override fun onClick(view: View?) {
            if (::nota.isInitialized) {
                onItemClickListener(nota)
            }
        }

        fun vincula(nota: Nota) {

            this.nota = nota
            binding.nota = NotaData(nota)
        }

        override fun getLifecycle(): Lifecycle = registry

    }

}

object DiffCallback : DiffUtil.ItemCallback<Nota>() {
    override fun areItemsTheSame(
        oldItem: Nota,
        newItem: Nota
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Nota, newItem: Nota) = oldItem == newItem
}