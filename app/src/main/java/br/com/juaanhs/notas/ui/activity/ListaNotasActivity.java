package br.com.juaanhs.notas.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.model.Nota;
import br.com.juaanhs.notas.ui.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        ListView ListaNotas = findViewById(R.id.cardView);

        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("Primeiro Título","Primeira descrição"));
        final List<Nota> todasNotas = dao.todos();

        ListaNotas.setAdapter(new ListaNotasAdapter(getApplicationContext(),todasNotas));
    }
}
