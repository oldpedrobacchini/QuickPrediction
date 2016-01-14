package com.example.pedrobacchini.quickprediction.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedrobacchini.quickprediction.JSONWeatherParser;
import com.example.pedrobacchini.quickprediction.R;
import com.example.pedrobacchini.quickprediction.Utils;
import com.example.pedrobacchini.quickprediction.WeatherHttpClient;
import com.example.pedrobacchini.quickprediction.model.Weather;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Classe principal da aplicacao contra a tela inicial, responsavel por mostrar a lista de previsoes temporais para uma respectiva cidade
 */
public class OverviewActivity extends AppCompatActivity {

    //Chave para os dados de configuracoes da aplicacao
    public static final String PREFS_NAME = "MyPrefsFile";

    //Cidade padrao da aplicacao
    public static final String DEFAULT_CITY = "London";

    //Variavel responsavel por controlar se a aplicacao deve ter uma ou duas telas
    private boolean mTwoPane;

    //Adaptador para mostrar os objetos Weather
    private WeatherItemRecyclerViewAdapter WeatherAdapter = new WeatherItemRecyclerViewAdapter();

    //Lista de condicoes temporais
    private ArrayList<Weather> weatherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Toolbar principal da aplicacao com nome e botao de configuracao
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Objeto que armazena dados de configuracao
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //Recuperando a cidade configurada da aplicacao
        String currentCity = settings.getString("currentCity",DEFAULT_CITY);
        //Recuperando o Campo de texto (editTextCurrentCity) da Tela de Overview
        TextView cityEdt = (TextView) findViewById(R.id.editTextCurrentCity);
        //Colocando a cidade configurada no campo
        cityEdt.setText(currentCity);

        //Recupera recyclerView responsavel por mostrar os dados temporais
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
        //Adiciona o WeatherAdapter ao recyclerView
        recyclerView.setAdapter(WeatherAdapter);

        //Verifica se aplicacao esta em landscape
        if (findViewById(R.id.item_detail_container) != null) {

            //Recupera gerente de janela
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            //Recupera largura da tela
            int width = windowManager.getDefaultDisplay().getWidth();
            //Redimenciona a largura do recyclerView
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(width/2, RecyclerView.LayoutParams.MATCH_PARENT));
            //seta a variavel (duas telas)
            mTwoPane = true;
        }

        //Aplicacao esta conectada a internet?
        if(isOnline()) {
            //Cria rotina paralela para recuperar dos dados temporais para a cidade configurada
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(currentCity);
        } else {
            // Mostra popup quando nao estiver conectado a internet
            Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();
        }
    }

    //Cria o menu na barra de ferramentas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Infla o menu com o arquivo de configuracao de menu
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    // Define aos opcoes da barra de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Recupera o id da opcao selecionada
        int id = item.getItemId();

        //Verifica se o id e uma acao de configuracao
        if (id == R.id.action_settings) {
            //Cria a intencao de mudar para a activity SettingsActivity
            Intent it = new Intent(this,SettingsActivity.class);
            //Inicia a nova activity
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Verifica se o dispositivo esta conectado a internet
    private boolean isOnline() {
        //Recupera gerente de conectividade
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Recupera informacoe de conectividade
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //verifica se esta conectado
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Classe para a corrotina responsavel por recuperar os dados temporais com conexao a API openweathermap
     */
    private class JSONWeatherTask extends AsyncTask<String, Void, ArrayList<Weather>> {

        //Executa durante a corrotina
        @Override
        protected ArrayList<Weather> doInBackground(String... params) {

            //Recuperando arquivo JSON com os dados temporais
            String data = ((new WeatherHttpClient().getWeatherData(params[0])));

            try {
                //Limpando dados temporais
                weatherList.clear();
                //Adicionando novos dados temporais convertidos apartir do arquivo JSON
                weatherList.addAll(JSONWeatherParser.getWeather(data));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weatherList;
        }

        //Executa depois da corrotina
        @Override
        protected void onPostExecute(ArrayList<Weather> weatherList) {
            super.onPostExecute(weatherList);

            //Desativa icon de loading da janela de overview
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            //Troca os dados do WeatherAdapter da recyclerView
            WeatherAdapter.swap(weatherList);
        }
    }

    /**
     * Adaptador para a RecyclerView
     * Responsavel por Controlar cada dado temporal (Weather) para a conteudo do arquivo XML
     * que descreve o layout de cada elemento da lista (fragment)
     */
    public class WeatherItemRecyclerViewAdapter extends RecyclerView.Adapter<WeatherItemRecyclerViewAdapter.WeatherViewHolder> {

        private ArrayList<Weather> weathers = new ArrayList<>();

        //Troca o conteudo do adptador
        public void swap(ArrayList<Weather> weatherList) {
            //Limpa a lista de dados temporais
            weathers.clear();
            //Adiciona os novos dados
            weathers.addAll(weatherList);
            //Notifica o RecyclerView a atualizar
            notifyDataSetChanged();
        }

        //Infla o View Holder com o layout
        @Override
        public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new WeatherViewHolder(view);
        }

        //Mapeia as variaveis do Weather aos campos do XML utilizando o WeatherViewHolder
        @Override
        public void onBindViewHolder(final WeatherViewHolder holder, int position) {

            Weather weather = weathers.get(position);

            holder.mItem = weather;
            holder.mDayOfTheWeek.setText(weather.getDayOfTheWeek());
            holder.mIdView.setText(weather.getTemperature().getDayTemp() + "ºC");
            holder.mContentView.setText(weather.getCurrentCondition().getCondition() + " (" + weather.getCurrentCondition().getDescription() + ")");
            holder.mImageView.setImageResource(Utils.getDrawbleWeather(weather.getCurrentCondition().getIcon()));

            //Adiciona evento Click aos itens da lista
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Caso tenho dois paineis
                    if (mTwoPane) {
                        //Cria um novo fragmento de item detalhado
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        //Cria pacote de argumentos
                        Bundle arguments = new Bundle();
                        //adiciona o Weather corrente ao pacote de argumentos
                        arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, holder.mItem);
                        //adiciona argumentos ao fragmento
                        fragment.setArguments(arguments);
                        //Inicia transacao de dados entre arquivo xml e fragmento
                        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
                    }
                    //Caso apenas um painel
                    else {
                        //Recupera contexto
                        Context context = v.getContext();
                        //Cria a intencao de mudar para a activity ItemDetailActivity
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        //adiciona o Weather corrente a intencao
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,holder.mItem);
                        //Inicia a nova activity
                        context.startActivity(intent);
                    }
                }
            });
        }

        //Responsavel por retornar o numero de elementos no adaptador
        @Override
        public int getItemCount() {
            return weathers.size();
        }

        //Classe responsavel por representar cada item_list_content (XML)
        public class WeatherViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mDayOfTheWeek;
            public final TextView mIdView;
            public final TextView mContentView;
            public final ImageView mImageView;
            public Weather mItem;

            public WeatherViewHolder(View view) {
                super(view);
                mView = view;
                mDayOfTheWeek = (TextView) view.findViewById(R.id.textViewdayOfTheWeek);
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mImageView = (ImageView) view.findViewById(R.id.mImageView);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
