package com.example.android.bakingapp.bake;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kontrol on 9/20/2017.
 */

public class  Recipe implements Parcelable {
    private  int recipeID;
    private  String recipeName;
    private  List<Ingredient> recipeIngredients;
    private  List<Step> recipeSteps;
    private  int recipeServings;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings){
        recipeID = id;
        recipeName = name;
        recipeIngredients = ingredients;
        recipeSteps = steps;
        recipeServings = servings;
    }



    public  int getRecipeID(){
        return recipeID;
    }

    public  String getRecipeName(){
        return recipeName;
    }

    public  List<Ingredient> getRecipeIngredients(){
        return recipeIngredients;
    }

    public  List<Step> getRecipeSteps(){
        return recipeSteps;
    }

    public  int getRecipeServings(){
        return recipeServings;
    }



    protected Recipe(Parcel in) {
        recipeID = in.readInt();
        recipeName = in.readString();
        if (in.readByte() == 0x01) {
            recipeIngredients = new ArrayList<Ingredient>();
            in.readList(recipeIngredients, Ingredient.class.getClassLoader());
        } else {
            recipeIngredients = null;
        }
        if (in.readByte() == 0x01) {
            recipeSteps = new ArrayList<Step>();
            in.readList(recipeSteps, Step.class.getClassLoader());
        } else {
            recipeSteps = null;
        }
        recipeServings = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeID);
        dest.writeString(recipeName);
        if (recipeIngredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(recipeIngredients);
        }
        if (recipeSteps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(recipeSteps);
        }
        dest.writeInt(recipeServings);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
