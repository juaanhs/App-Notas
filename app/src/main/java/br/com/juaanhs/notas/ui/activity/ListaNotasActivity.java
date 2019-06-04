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

        RecyclerView ListaNotas = findViewById(R.id.lista_notas_recyclerview);

        NotaDAO dao = new NotaDAO();
        for(int i = 0; i <= 10000; i++) {
            dao.insere(new Nota("Primeiro Título " + i,
                    "Primeira descrição " + i));
        }
        final List<Nota> todasNotas = dao.todos();

        ListaNotas.setAdapter(new ListaNotasAdapterRecyclerView(getApplicationContext(), todasNotas));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        ListaNotas.setLayoutManager(layoutManager);
    }
}
