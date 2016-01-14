package com.example.pedrobacchini.quickprediction;

import com.example.pedrobacchini.quickprediction.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe responsavel por converter dados de arquivo JSON e array list de Weather
 */
public class JSONWeatherParser {

    //Metodo principal para convercao do arquivo JSON em ArrayList de Weather
    public static ArrayList<Weather> getWeather(String data) throws JSONException {

        //Cria o ArrayList de Weather
        ArrayList<Weather> weatherList = new ArrayList<Weather>();

        //Verifica se o arquivo JSON nao e nulo
        if(data!=null) {

            //Cria o objeto JSON com todos os dados
            JSONObject jsonObject = new JSONObject(data);

            //Recupera o Array de Weather do JSON
            JSONArray jArr = jsonObject.getJSONArray("list");

            //Cria JSONObjects
            JSONObject JSONWeather, JSONTemp, JSONCondition;

            //Cria valores de ponto flutuante
            float dayTemp, minTemp, maxTemp, nightTemp, eveTemp, mornTemp, pressure, humidity, speed, deg, snow;
            //Cria valores inteiros
            int cloudPerc;
            //Cria strings
            String condition, description, icon;

            //Cria Vetor de nome da semana
            String[] namesOfDays =  {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            //Recupera indice do dia da semana atual
            int indexdayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

            //Nome do dia da semana
            String dayOfTheWeek;

            //Percorre por todos os Weathers JSON
            for (int i = 0; i < jArr.length(); i++) {

                //Recupera o Weather atual
                JSONWeather = jArr.getJSONObject(i);

                //Recupera as Temperaturas do Weather
                JSONTemp = JSONWeather.getJSONObject("temp");

                //Recupera a temperatuda do dia
                dayTemp = getFloat("day", JSONTemp);
                //Recupera a temperatura minima
                minTemp = getFloat("min", JSONTemp);
                //Recupera a temperatura maxima
                maxTemp = getFloat("max", JSONTemp);
                //Recupera a temperatura noturna
                nightTemp = getFloat("night", JSONTemp);
                //Recupera a temperatura de tarde
                eveTemp = getFloat("eve", JSONTemp);
                //Recupera a temperatura de manha
                mornTemp = getFloat("morn", JSONTemp);

                //Recupera as condicoes do Weather
                JSONCondition = JSONWeather.getJSONArray("weather").getJSONObject(0);

                //Recupera a condicao principal
                condition = getString("main", JSONCondition);
                //Recupera a descricao da condicao
                description = getString("description", JSONCondition);
                //Recupera o icone da condicao
                icon = getString("icon", JSONCondition);

                //Recupera a pressao
                pressure = getFloat("pressure", JSONWeather);
                //Recupera a humidade
                humidity = getFloat("humidity", JSONWeather);
                //Recupera a velocidade dos ventos
                speed = getFloat("speed", JSONWeather);
                //Recupera o angulo dos ventos
                deg = getFloat("deg", JSONWeather);
                //Recupera a quantidade de nuvens
                cloudPerc = getInt("clouds", JSONWeather);
                //Recupera a quantidade neve
                snow = getFloat("snow", JSONWeather);

                //Verifica se a interacao atual e a primeira
                if(i == 0)
                {
                    dayOfTheWeek = "Today";
                }
                //Verifica se a interacao atual e a segunda
                else if(i==1)
                {
                    dayOfTheWeek = "Tomorrow";
                }
                //Para todas as outras define o dia da semana apartir do vetor de dias da semana
                else {
                    dayOfTheWeek = namesOfDays[indexdayOfTheWeek-1];
                }

                //Cria o objeto Weather passando ao construtor todos os dados recuperados
                weatherList.add(new Weather(dayTemp, minTemp, maxTemp, nightTemp, eveTemp, mornTemp, condition, description, icon, pressure, humidity, speed, deg, cloudPerc, snow,dayOfTheWeek));

                //Incrementa o indice de dia da semana
                indexdayOfTheWeek++;
                //Verifica se o indice exedeu o vetor
                if(indexdayOfTheWeek>=7)
                {
                    //Se sim reinicia o indice
                    indexdayOfTheWeek=1;
                }
            }
        }

        return weatherList;
    }

    //Recupera de um Objeto JSON um valor FLOAT
    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        if (!jObj.isNull(tagName)) {
            return (float) jObj.getDouble(tagName);
        } else {
            return 0;
        }
    }

    //Recupera de um Objeto JSON um valor INT
    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        if(!jObj.isNull(tagName)) {
            return jObj.getInt(tagName);
        } else {
            return 0;
        }
    }

    //Recupera de um Objeto JSON uma String
    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        if(!jObj.isNull(tagName)) {
            return jObj.getString(tagName);
        } else {
            return "";
        }
    }
}
