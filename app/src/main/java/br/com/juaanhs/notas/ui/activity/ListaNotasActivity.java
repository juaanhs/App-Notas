package br.com.juaanhs.notas.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.model.Nota;
import br.com.juaanhs.notas.ui.recyclerview.adapter.ListaNotasAdapterRecyclerView;
import br.com.juaanhs.notas.ui.recyclerview.adapter.OnItemClickListener;

import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.KEY_NOTA;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.REQUEST_CODE_INSERE_NOTA;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTA_CRIADA;

public class ListaNotasActivity extends AppCompatActivity {


    private ListaNotasAdapterRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> todasNotas = getTodasNotas();

        configuraRecyclerView(todasNotas);

        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioNotaActivity();
            }
        });
    }

    private void vaiParaFormularioNotaActivity() {
        Intent intentFormularioNota =
                new Intent(getApplicationContext(),
                        FormularioNotaActivity.class);
        startActivityForResult(intentFormularioNota,
                REQUEST_CODE_INSERE_NOTA);
    }

    private List<Nota> getTodasNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(ehResultadoComNota(requestCode, resultCode, data)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(KEY_NOTA);
            adiciona(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, Intent data) {
        return requestCode == REQUEST_CODE_INSERE_NOTA &&
                resultCode == RESULT_CODE_NOTA_CRIADA &&
                data.hasExtra(KEY_NOTA);
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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(ListaNotasActivity.this, "Clicado na activity",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
