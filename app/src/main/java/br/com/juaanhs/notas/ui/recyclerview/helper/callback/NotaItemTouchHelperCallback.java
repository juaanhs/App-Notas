package br.com.juaanhs.notas.ui.recyclerview.helper.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.ui.recyclerview.adapter.ListaNotasAdapterRecyclerView;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapterRecyclerView adapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapterRecyclerView adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT
                | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.UP
                | ItemTouchHelper.DOWN
                | ItemTouchHelper.RIGHT
                | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar,marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = viewHolder1.getAdapterPosition();
        new NotaDAO().troca(posicaoInicial, posicaoFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int posicaoNota = viewHolder.getAdapterPosition();
        new NotaDAO().remove(posicaoNota);
        adapter.remove(posicaoNota);
    }
}
