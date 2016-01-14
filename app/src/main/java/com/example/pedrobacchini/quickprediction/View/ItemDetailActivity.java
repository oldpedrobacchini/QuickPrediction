package com.example.pedrobacchini.quickprediction.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.pedrobacchini.quickprediction.R;

/**
 * Classe que contra a tela de Detalhe dos dados temporais
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        //Recupera a barra de ferramentos da janela
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        //Ativa o suporte a acoes na barra de ferramentas
        setSupportActionBar(toolbar);

        //Recupera barra de acao
        ActionBar actionBar = getSupportActionBar();
        //Se existir
        if (actionBar != null) {
            //Seta na barra de acao a opcao de voltar a tela anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Quando o savedInstanceState for diferente de nulo o fragmento vai ser recuperado automaticamente
        if(savedInstanceState == null) {

            //Cria pacote de argumentos
            Bundle arguments = new Bundle();
            //adiciona o Weather corrente ao pacote de argumentos
            arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, getIntent().getParcelableExtra(ItemDetailFragment.ARG_ITEM_ID));

            //Cria um novo fragmento de item detalhado
            ItemDetailFragment fragment = new ItemDetailFragment();
            //adiciona argumentos ao fragmento
            fragment.setArguments(arguments);
            //Inicia transacao de dados entre arquivo xml e fragmento
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container,fragment).commit();

        }
    }

    // Define aos opcoes da barra de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Recupera o id da opcao selecionada
        int id = item.getItemId();

        //Verifica se o id e uma acao de voltar
        if (id == android.R.id.home) {
            //Cria a intencao de mudar para a activity OverviewActivity
            Intent it = new Intent(this,OverviewActivity.class);
            //Volta a activity anterior
            navigateUpTo(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
