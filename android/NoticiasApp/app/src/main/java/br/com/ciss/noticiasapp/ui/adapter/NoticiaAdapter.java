package br.com.ciss.noticiasapp.ui.adapter;

import br.com.ciss.noticiasapp.model.Noticia;
import br.com.ciss.noticiasapp.ui.adapter.NoticiaAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Cristian Urbainski
 * @since 1.0 (29/09/20)
 */
public class NoticiaAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final LayoutInflater layoutInflater;

    public NoticiaAdapter(Context context) {

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }

    }

}