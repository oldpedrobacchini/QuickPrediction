package com.example.pedrobacchini.quickprediction.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe de dados que representa uma previsao de tempo para um cidade, com todos os dados necessarios a aplicacao
 * Implementacao de Parcelable para transferir o Weather entre activitys
 */
public class Weather implements Parcelable {

    //Dados de temperaturas
    private final Temperature temperature;
    //Dados da condicoes do tempo
    private final CurrentCondition currentCondition;
    //Dados do vento
    private final Wind wind;
    //Dado da quantidade de nuvens
    private final int cloudPerc;
    //Dados da quantidade de neve
    private final float snow;
    //Dia da semana que o dados temporais representam, apenas para mostrar na interface de forma mais agradavel
    private final String dayOfTheWeek;

    //Construtor da classe dos dados temporais
    public Weather(float dayTemp,
                   float minTemp,
                   float maxTemp,
                   float nightTemp,
                   float eveTemp,
                   float mornTemp,

                   String condition,
                   String description,
                   String icon,
                   float pressure,
                   float humidity,

                   float speed,
                   float deg,

                   int cloudPerc,
                   float snow,
                   String dayOfTheWeek) {

        if(cloudPerc<0)
            throw new IllegalArgumentException("Weather cloudPerc can not be negative");

        if(snow<0)
            throw new IllegalArgumentException("Weather snow can not be negative");

        temperature = new Temperature(dayTemp, minTemp, maxTemp, nightTemp, eveTemp, mornTemp);
        currentCondition = new CurrentCondition(condition, description, icon, pressure, humidity);
        wind = new Wind(speed, deg);
        this.cloudPerc = cloudPerc;
        this.snow = snow;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public CurrentCondition getCurrentCondition() {
        return currentCondition;
    }

    public Wind getWind() {
        return wind;
    }

    public int getCloudPerc() {
        return cloudPerc;
    }

    public float getSnow() {
        return snow;
    }

    public String getDayOfTheWeek() { return dayOfTheWeek; }

    /*
    Daqui para baixo funcoes criadas para implementacao do Parcelable
     */
    protected Weather(Parcel in) {
        temperature = (Temperature) in.readValue(Temperature.class.getClassLoader());
        currentCondition = (CurrentCondition) in.readValue(CurrentCondition.class.getClassLoader());
        wind = (Wind) in.readValue(Wind.class.getClassLoader());
        cloudPerc = in.readInt();
        snow = in.readFloat();
        dayOfTheWeek = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temperature);
        dest.writeValue(currentCondition);
        dest.writeValue(wind);
        dest.writeInt(cloudPerc);
        dest.writeFloat(snow);
        dest.writeString(dayOfTheWeek);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}