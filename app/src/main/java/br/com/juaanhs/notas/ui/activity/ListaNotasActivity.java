package br.com.juaanhs.notas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.dao.NotaDAO;
import br.com.juaanhs.notas.model.Nota;
import br.com.juaanhs.notas.ui.recyclerview.adapter.ListaNotasAdapterRecyclerView;
import br.com.juaanhs.notas.ui.recyclerview.adapter.listener.OnItemClickListener;

import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.KEY_NOTA;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.KEY_POSITION;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.MSG_DE_ERRO;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.POSITION_INVALID;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.REQUEST_CODE_ALTERA_NOTA;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.REQUEST_CODE_INSERE_NOTA;

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
                vaiParaFormularioNotaActivityInsere();
            }
        });
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent intentFormularioNota =
                new Intent(getApplicationContext(),
                        FormularioNotaActivity.class);
        startActivityForResult(intentFormularioNota,
                REQUEST_CODE_INSERE_NOTA);
    }

    private List<Nota> getTodasNotas() {
        NotaDAO dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(new Nota("Titulo " + (i+1),
                    "descricao " + (i+1)));
        }
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ehResultadoInsereNota(requestCode, data)){
            if(resultCode == RESULT_OK) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(KEY_NOTA);
                adicionaNota(notaRecebida);
            }
        }

        if(ehResultadoAlteraNota(requestCode, data)) {
            if(resultCode == RESULT_OK) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(KEY_NOTA);
                int posicaoRecebida = data.getIntExtra(KEY_POSITION, POSITION_INVALID);
                if (ehPosicaoValida(posicaoRecebida)) {
                    alteraNota(notaRecebida, posicaoRecebida);
                } else {
                    Toast.makeText(getApplicationContext(), MSG_DE_ERRO, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void alteraNota(Nota nota, int posicao) {
        new NotaDAO().altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSITION_INVALID;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return requestCode == REQUEST_CODE_ALTERA_NOTA &&
                data.hasExtra(KEY_NOTA);
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return requestCode == REQUEST_CODE_INSERE_NOTA &&
                data.hasExtra(KEY_NOTA);
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView ListaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, ListaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapterRecyclerView(getApplicationContext(), todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int position) {
                vaiParaFormularioNotaActivityAltera(nota, position);
            }
        });
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int position) {
        Intent abreFormularioComNota = new Intent(getApplicationContext(),
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(KEY_NOTA, nota);
        abreFormularioComNota.putExtra(KEY_POSITION, position);
        startActivityForResult(abreFormularioComNota, REQUEST_CODE_ALTERA_NOTA);
    }
}
