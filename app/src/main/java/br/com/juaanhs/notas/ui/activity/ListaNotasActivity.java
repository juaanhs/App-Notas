package br.com.juaanhs.notas.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.model.Nota;
import br.com.juaanhs.notas.ui.recyclerview.adapter.ListaNotasAdapterRecyclerView;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapterRecyclerView adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        todasNotas = notasDeExemplo();
        configuraRecyclerView(todasNotas);

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intentFormularioNota = new Intent(getApplicationContext(), FormularioNotaActivity.class);
                startActivityForResult(intentFormularioNota, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 2 && data.hasExtra("nota")){
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            new NotaDAO().insere(notaRecebida);
            adapter.adiciona(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        adapter = new ListaNotasAdapterRecyclerView(getApplicationContext(), todasNotas);
        listaNotas.setAdapter(adapter);
    }
}
