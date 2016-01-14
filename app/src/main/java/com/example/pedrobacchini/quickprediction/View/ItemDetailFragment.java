package com.example.pedrobacchini.quickprediction.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pedrobacchini.quickprediction.R;
import com.example.pedrobacchini.quickprediction.Utils;
import com.example.pedrobacchini.quickprediction.model.Weather;

/**
 * Classe que define o fragmento de cada detalhe de item
 */
public class ItemDetailFragment extends Fragment {

    //Id para Weather ser recuperado entre as activitys
    public static final String ARG_ITEM_ID = "item_id";

    //Weather principal do fragmento
    private Weather weather;

    //Construtor padrao para o fragmento
    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Verifica se o fargmento tem como argumento um Weather
        if(getArguments().containsKey(ARG_ITEM_ID)) {
            //Recupera o Weather dos argumentos
            weather = (Weather) getArguments().getParcelable(ARG_ITEM_ID);

            //Recupera a acitivity
            Activity activity = this.getActivity();
            //Recupera a barra de titulo da acitivity
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //Define o titulo da barra
                appBarLayout.setTitle(weather.getDayOfTheWeek());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        //Verifica se existe um Weather
        if(weather != null) {

            //Define o Campo de Detalhes da condicao do weather
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(
                    weather.getCurrentCondition().getCondition()+" (" +
                            weather.getCurrentCondition().getDescription()+")");

            //Define o campo temperatura do dia
            ((TextView) rootView.findViewById(R.id.dayTemp)).setText(weather.getTemperature().getDayTemp() + "ºC");

            //Define a imagem do weather
            ((ImageView) rootView.findViewById(R.id.mImageView)).setImageResource(
                    Utils.getDrawbleWeather(weather.getCurrentCondition().getIcon()));

            //Define  o campo da temperatura minima
            ((TextView) rootView.findViewById(R.id.minTemp)).setText(weather.getTemperature().getMinTemp() + "ºC");

            //Define o campo da temperatura maxima
            ((TextView) rootView.findViewById(R.id.maxTemp)).setText(weather.getTemperature().getMaxTemp() + "ºC");

            //Define o campo da tempeturatura noturna
            ((TextView) rootView.findViewById(R.id.nightTemp)).setText(weather.getTemperature().getNightTemp() + "ºC");

            //Define o campo da temperatura atarde
            ((TextView) rootView.findViewById(R.id.eveTemp)).setText(weather.getTemperature().getEveTemp() + "ºC");

            //Define o campo da temperatura de manha
            ((TextView) rootView.findViewById(R.id.mornTemp)).setText(weather.getTemperature().getMornTemp() + "ºC");

            //Define o campo da humidade
            ((TextView) rootView.findViewById(R.id.hum)).setText(weather.getCurrentCondition().getHumidity()+ "%");

            //Define o campo da pressao
            ((TextView) rootView.findViewById(R.id.pressure)).setText(weather.getCurrentCondition().getPressure()+"hPa");

            //Define o campo da velocidade do vento
            ((TextView) rootView.findViewById(R.id.windSpeed)).setText(weather.getWind().getSpeed()+"m/s");

            //Define o campo do angulo do vento
            ((TextView) rootView.findViewById(R.id.windDag)).setText(weather.getWind().getDeg()+"º");

            //Define o campo da quantidade de nuvem
            ((TextView) rootView.findViewById(R.id.cloudPerc)).setText(Integer.toString(weather.getCloudPerc()));

            //Define o campo da quantidade de neve
            ((TextView) rootView.findViewById(R.id.snow)).setText( Float.toString(weather.getSnow()));

        }

        return rootView;
    }
}