package com.example.pedrobacchini.quickprediction.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe de dados que representa um conjunto de temperaturas
 * Implementacao de Parcelable para transferir o Temperature entre activitys
 */
public class Temperature implements Parcelable {

    //Temperatura do dia
    private final float dayTemp;
    //Temperatura minima
    private final float minTemp;
    //Temperatura maxima
    private final float maxTemp;
    //Temperatura anoite
    private final float nightTemp;
    //Temperatura de tarde
    private final float eveTemp;
    //Temperatura de manha
    private final float mornTemp;

    //Construtor da classe temperature
    public Temperature(float dayTemp, float minTemp, float maxTemp, float nightTemp, float eveTemp, float mornTemp) {
        this.dayTemp = dayTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.nightTemp = nightTemp;
        this.eveTemp = eveTemp;
        this.mornTemp = mornTemp;
    }

    public float getDayTemp() {
        return dayTemp;
    }

    public float getNightTemp() {
        return nightTemp;
    }

    public float getEveTemp() {
        return eveTemp;
    }

    public float getMornTemp() {
        return mornTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    /*
    Daqui para baixo funcoes criadas para implementacao do Parcelable
     */
    protected Temperature(Parcel in) {
        dayTemp = in.readFloat();
        minTemp = in.readFloat();
        maxTemp = in.readFloat();
        nightTemp = in.readFloat();
        eveTemp = in.readFloat();
        mornTemp = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(dayTemp);
        dest.writeFloat(minTemp);
        dest.writeFloat(maxTemp);
        dest.writeFloat(nightTemp);
        dest.writeFloat(eveTemp);
        dest.writeFloat(mornTemp);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };
}