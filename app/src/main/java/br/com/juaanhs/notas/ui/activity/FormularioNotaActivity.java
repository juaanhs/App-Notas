package br.com.juaanhs.notas.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.com.juaanhs.notas.R;
import br.com.juaanhs.notas.model.Nota;

import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.KEY_NOTA;
import static br.com.juaanhs.notas.ui.activity.NotaActivityConstantes.RESULT_CODE_NOTA_CRIADA;

public class FormularioNotaActivity extends AppCompatActivity {


    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra(KEY_NOTA) && dadosRecebidos.hasExtra("posicao")){
            Nota notaRecebida = (Nota) dadosRecebidos
                    .getSerializableExtra(KEY_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra("posicao", -1);
            TextView titulo = findViewById(R.id.formulario_nota_titulo);
            titulo.setText(notaRecebida.getTitulo());
            TextView descricao = findViewById(R.id.formulario_nota_descricao);
            descricao.setText(notaRecebida.getDescricao());


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(verificaMenuSalvaNota(item)){
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(KEY_NOTA, nota);
        resultadoInsercao.putExtra("posicao", posicaoRecebida);
        setResult(RESULT_CODE_NOTA_CRIADA, resultadoInsercao);
    }

    private Nota criaNota() {
        EditText titulo = findViewById(R.id.formulario_nota_titulo);
        EditText descricao = findViewById(R.id.formulario_nota_descricao);
        return new Nota(titulo.getText()
                .toString(),
                descricao.getText().toString());
    }

    private boolean verificaMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_ic_nota_salva;
    }
}
