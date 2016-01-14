package com.example.pedrobacchini.quickprediction.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe de dados que representa uma condicao de tempo para um cidade
 * Implementacao de Parcelable para transferir o Weather entre activitys
 */
public class CurrentCondition implements Parcelable {

    //Condicao geral
    private final String condition;
    //Descricao da condicao
    private final String description;
    //Codigo do icone da condicao
    private final String icon;
    //Pressao atimosferica
    private final float pressure;
    //Humidade
    private final float humidity;

    //Construtor da classe de condicao de tempo
    public CurrentCondition(String condition, String description, String icon, float pressure, float humidity) {

        if(pressure<0)
            throw new IllegalArgumentException("CurrentCondition pressure can not be negative");

        if(humidity<0)
            throw new IllegalArgumentException("WiCurrentConditionnd humidity can not be negative");

        this.condition = condition;
        this.description = description;
        this.icon = icon;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    /*
    Daqui para baixo funcoes criadas para implementacao do Parcelable
     */
    protected CurrentCondition(Parcel in) {
        condition = in.readString();
        description = in.readString();
        icon = in.readString();
        pressure = in.readFloat();
        humidity = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(condition);
        dest.writeString(description);
        dest.writeString(icon);
        dest.writeFloat(pressure);
        dest.writeFloat(humidity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CurrentCondition> CREATOR = new Parcelable.Creator<CurrentCondition>() {
        @Override
        public CurrentCondition createFromParcel(Parcel in) {
            return new CurrentCondition(in);
        }

        @Override
        public CurrentCondition[] newArray(int size) {
            return new CurrentCondition[size];
        }
    };
}