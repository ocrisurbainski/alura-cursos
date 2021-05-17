package br.com.alura.leilao.ui;

import br.com.alura.leilao.asynctask.BaseAsyncTask;
import br.com.alura.leilao.database.repository.UsuarioRepository;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

public class AtualizadorDeUsuario {

    private final ListaUsuarioAdapter adapter;
    private final RecyclerView recyclerView;
    private UsuarioRepository usuarioRepository;

    public AtualizadorDeUsuario(UsuarioRepository usuarioRepository,
            ListaUsuarioAdapter adapter,
            RecyclerView recyclerView) {

        this.usuarioRepository = usuarioRepository;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    public void salva(Usuario usuario) {

        new BaseAsyncTask<>(() -> usuarioRepository.insert(usuario), this::atualizaNaLista).execute();
    }

    @VisibleForTesting
    public void atualizaNaLista(Usuario usuario) {

        adapter.adiciona(usuario);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

}
