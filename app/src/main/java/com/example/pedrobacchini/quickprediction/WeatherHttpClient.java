package com.example.pedrobacchini.quickprediction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe responsavel pela conexao com a API de dados temporais OpenWeatherMap e recuperacao do JSON
 */
public class WeatherHttpClient {

    //URL base para conexao com o OpenWeatherMap
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    //URL de parametros adicionais para o OpenWeatherMap
    private static String PARAMETES_URL = "&APPID=52f540d3fd613ccd5e11feb6ed0405a2&cnt=7&units=metric";

    //Faz a conexao com o OpenWeatherMap passando a cidade que se deseja os dados temporais, retorna uma string (JSON) com os dados de resposta
    public String getWeatherData(String location) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            //Cria objeto de conexao passando o endereco para tal
            connection = (HttpURLConnection) (new URL(BASE_URL + location.replaceAll(" ","") + PARAMETES_URL)).openConnection();
            //Define o tipo de conexao
            connection.setRequestMethod("GET");
            //Conecta ao OpenWeatherMap
            connection.connect();

            //Cria canal de entrada de dados
            inputStream = connection.getInputStream();
            //Cria buffer de leitura apartir do canal de entrada
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Variavel responsavel por ler linha por linha
            String line;
            //Buffer de armazenamento de String
            StringBuffer buffer = new StringBuffer();

            //Leitura do buffer de leitura ate o final adicionando ao buffer de armazenamento
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\r\n");
            }

            //Fecha canal de leitura
            inputStream.close();
            //Fecha Conexao
            connection.disconnect();

            //Retorna dados lidos
            return buffer.toString();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Throwable t) {
            }
            try {
                connection.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;
    }
}
