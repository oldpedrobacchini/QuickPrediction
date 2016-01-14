package com.example.pedrobacchini.quickprediction.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobacchini.quickprediction.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que contra a tela de configuracao, responsavel pela busca e mudanca da ciadade corrente da aplicacao
 */
public class SettingsActivity extends AppCompatActivity {

    //Vetor de nomes de cidade
    private String[] city_names = {};
    //ListView que mostra os nomes de cidade
    private ListView listView;
    //Cidade corrente selecionada
    private String currentCity;
    //Dados de configuracao da aplicacao
    private SharedPreferences settings;
    //Loading Panel para mostrar ao usuario quando esta sendo feita uma busca
    private RelativeLayout layoutloadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Recupera a barra de ferramentos da janela
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Ativa o suporte a acoes na barra de ferramentas
        setSupportActionBar(toolbar);

        //Recupera barra de acao
        ActionBar actionBar = getSupportActionBar();
        //Se existir
        if (actionBar != null) {
            //Seta na barra de acao a opcao de voltar a tela anterior
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Recupera Loading Panel
        layoutloadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
        //Desativa Loading Panel
        layoutloadingPanel.setVisibility(View.GONE);

        //Recupera campo de texto, usado para a busca de cidade
        final EditText edt = (EditText) findViewById(R.id.cityEdtText);

        //Define o comportamento ao buscar uma cidade
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Verifica se a acao foi uma busca
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //Recupera o padrao a ser buscado
                    String pattern = edt.getText().toString();
                    //Verifica se esse padrao tem tamanho maior que 3
                    if (pattern.length() >= 3) {
                        //Ativa o Loading Panel
                        layoutloadingPanel.setVisibility(View.VISIBLE);
                        //Esconde o teclado do andorid
                        ((InputMethodManager) getBaseContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                edt.getWindowToken(), 0);
                        //Corrotina para busca do padrao
                        JSONSearchTask task = new JSONSearchTask();
                        task.execute(new String[]{pattern});
                    }else {
                        // Mostra janela alertando quando o padrao buscado tem tamanho menor que 3
                        Toast.makeText(getApplicationContext(),
                                "3 or more characters to search", Toast.LENGTH_LONG)
                                .show();
                    }
                    return true;
                }

                return false;
            }
        });

        //Recupera dados de configuracao do usuario
        settings = getSharedPreferences(OverviewActivity.PREFS_NAME, 0);
        //Recupera cidade corrente configurado pelo usuario
        currentCity = settings.getString("currentCity",OverviewActivity.DEFAULT_CITY);
        //Recupera campo de texto da cidade corrente
        TextView cityEdt = (TextView) findViewById(R.id.editTextCurrentCity);
        //Seta a cidade corrente selecionado pelo usuario ao compo de texto correspondente
        cityEdt.setText(currentCity);

        //Recupera a lista de cidade
        listView = (ListView) findViewById(R.id.cityList);

        //Define o comportamento quando um dos objetos na lista e selecionado
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Indice para o objeto clicado na lista
                int itemPosition = position;

                // Valor do objeto cliacado
                currentCity = (String) listView.getItemAtPosition(position);
                // Recupera o campo de texto com a cidade corrente
                TextView cityEdt = (TextView) findViewById(R.id.editTextCurrentCity);
                // Define o novo texto para o campo de texto da cidade corrente
                cityEdt.setText(currentCity);
                // Recupera o editor do arquivo de configuracao do usuario
                SharedPreferences.Editor editor = settings.edit();
                // Define a nome cidade corrente do usuario
                editor.putString("currentCity",currentCity);
                // Aplica as modificacoes no editor
                editor.commit();
            }

        });
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

    /**
     * Classe para a corrotina responsavel por recuperar as cidades de acordo com o padrao passado
     */
    private class JSONSearchTask extends AsyncTask<String, Void, String[]> {

        //Executa durante a corrotina
        @Override
        protected String[] doInBackground(String... params) {
            try {
                // Recupera o vetor com as cidades de acordo com o padrao
                city_names = LoadFile("city_list.txt", params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return city_names;
        }

        //Executa depois da corrotina
        @Override
        protected void onPostExecute(String[] city_names) {
            super.onPostExecute(city_names);

            //Desativa o Loading Panel
            layoutloadingPanel.setVisibility(View.GONE);

            //Verifica se nao encontrou nada
            if(city_names.length == 0) {
                //Se nao encontrou mostrar janela ao usaurio, nenhuma cidade encontrada
                Toast.makeText(getApplicationContext(),
                        "No city found", Toast.LENGTH_LONG)
                        .show();
            } else {
                //Se alguma cidade for encontrada cria um novo adaptador que define as cidades na lista
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, city_names);
                //Define o novo adpatador a lista de cidades
                listView.setAdapter(adapter);
            }
        }
    }

    //Carrega um vetor de strings de acordo com um padrao de comeco de texto, apartir de um arquivo de texto em disco
    public String[] LoadFile(String fileName, String pattern) throws IOException
    {
        //Define a estrutura que armazenara as Strings encontradas
        List<String> city_names = new ArrayList<>();

        //Recupera os recursos
        Resources resources = getResources();
        //Cria o Leior de dados de entrada
        InputStream iS;

        //Recupera o arquivo do disco
        iS = resources.getAssets().open(fileName);

        //Cria um buffer de leitura aprtir do leitor de dados
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iS));

        //String de leitura linha por linha
        String line;

        //Ler o buffer linha por linha verificando se cada linha comeca com o padrao procurado ate o fim do arquivo
        //Caso encontre adiciona na lista de cidade
        while ((line = bufferedReader.readLine()) != null) {
            if(line.toLowerCase().startsWith(pattern.toLowerCase().trim())) {
                city_names.add(line);
            }
        }

        //fecha leitor de dados
        iS.close();

        //Retorna a lista de cidade convertendo ela em vetor
        return city_names.toArray(new String[city_names.size()]);
    }
}
