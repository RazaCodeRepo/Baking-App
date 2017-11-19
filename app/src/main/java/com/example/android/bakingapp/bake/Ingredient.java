package com.example.android.bakingapp.bake;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kontrol on 9/20/2017.
 */

public class Ingredient implements Parcelable {
    private String name;
    private String metric;
    private int quantity;

    public Ingredient(String name, String metric, int quantity){
        this.name = name;
        this.metric = metric;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public String getMetric(){
        return metric;
    }

    public int getQuantity(){
        return quantity;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        metric = in.readString();
        quantity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(metric);
        dest.writeInt(quantity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
