package com.example.pedrobacchini.quickprediction.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe de dados que representa a condicao dos ventos para um cidade
 * Implementacao de Parcelable para transferir o Weather entre activitys
 */
public class Wind implements Parcelable {

    //Velocidade dos ventos
    private final float speed;
    //Angulo dos ventos
    private final float deg;

    //Construtor da classe ventos
    public Wind(float speed, float deg) {
        if(speed<0)
            throw new IllegalArgumentException("Wind speed can not be negative");

        if(deg<0)
            throw new IllegalArgumentException("Wind deg can not be negative");

        this.speed = speed;
        this.deg = deg;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }

    /*
    Daqui para baixo funcoes criadas para implementacao do Parcelable
     */
    protected Wind(Parcel in) {
        speed = in.readFloat();
        deg = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(speed);
        dest.writeFloat(deg);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}