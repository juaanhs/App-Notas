package br.com.juaanhs.notas.ui.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.model.Nota;

public class ListaNotasAdapterRecyclerView extends RecyclerView.Adapter<ListaNotasAdapterRecyclerView.NotaViewHolder> {


    private final List<Nota> notas;
    private final Context context;

    public ListaNotasAdapterRecyclerView(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    @Override
    public ListaNotasAdapterRecyclerView.NotaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, viewGroup, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaNotasAdapterRecyclerView.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }




    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView descricao;
        private final TextView titulo;

        public NotaViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
        }

        public void vincula(Nota nota) {
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    public void adiciona(Nota nota) {
        notas.add(nota);
        notifyDataSetChanged();
    }
}
