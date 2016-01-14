package com.example.pedrobacchini.quickprediction;

/**
 * Classe com os metodos uteis a aplicacao
 */
public class Utils {

    //Recuepra o indice dos icones que representam a condicao climatica de acordo com o codigo do icone
    public static int getDrawbleWeather(String iconCode) {

        switch (iconCode) {
            case "01d":
                return R.drawable.a01d;
            case "01n":
                return R.drawable.a01n;

            case "02d":
                return R.drawable.a02d;
            case "02n":
                return R.drawable.a02n;

            case "03d":
            case "03n":
                return R.drawable.a03d;

            case "04d":
            case "04n":
                return R.drawable.a04d;

            case "09d":
            case "09n":
                return R.drawable.a09d;

            case "10d":
                return R.drawable.a10d;
            case "10n":
                return R.drawable.a10n;

            case "11d":
            case "11n":
                return R.drawable.a11d;

            case "13d":
            case "13n":
                return R.drawable.a13d;

            case "50d":
            case "50n":
                return R.drawable.a50d;

            default:
                return R.drawable.placeholder;
        }
    }
}
