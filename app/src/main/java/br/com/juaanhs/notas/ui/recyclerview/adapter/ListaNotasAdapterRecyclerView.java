package br.com.juaanhs.notas.ui.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.model.Nota;

public class ListaNotasAdapterRecyclerView extends RecyclerView.Adapter {


    private final List<Nota> notas;
    private Context context;

    public ListaNotasAdapterRecyclerView(Context context ,List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, viewGroup, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Nota nota = notas.get(position);
        TextView titulo = viewHolder.itemView.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());
        TextView descricao = viewHolder.itemView.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
