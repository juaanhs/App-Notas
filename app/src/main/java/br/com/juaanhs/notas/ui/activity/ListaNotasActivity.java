package br.com.juaanhs.notas.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.model.Nota;
import br.com.juaanhs.notas.ui.recyclerview.adapter.ListaNotasAdapterRecyclerView;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        final List<Nota> todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);
    }

    private List<Nota> notasDeExemplo() {
        NotaDAO dao = new NotaDAO();
            dao.insere(new Nota("Primeira nota ",
                    "Primeira descrição pequena "), new Nota("Segunda nota ", "Segunda descrição enormeeeeeee muito grandeeee, giganteeee"));
        return dao.todos();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView ListaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, ListaNotas);
        configuraLayoutManager(ListaNotas);
    }

    private void configuraLayoutManager(RecyclerView listaNotas) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaNotas.setLayoutManager(layoutManager);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        listaNotas.setAdapter(new ListaNotasAdapterRecyclerView(getApplicationContext(), todasNotas));
    }
}
