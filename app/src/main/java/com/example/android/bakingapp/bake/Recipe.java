package com.example.android.bakingapp.bake;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.bakingapp.bake.Ingredient;
import com.example.android.bakingapp.bake.Step;

import java.util.ArrayList;
import java.util.List;

public class  Recipe implements Parcelable {
    private  int recipeID;
    private  String recipeName;
    private  List<Ingredient> recipeIngredients;
    private  List<Step> recipeSteps;
    private  int recipeServings;
    private String imageURL;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image){
        recipeID = id;
        recipeName = name;
        recipeIngredients = ingredients;
        recipeSteps = steps;
        recipeServings = servings;
        imageURL = image;
    }



    public  int getRecipeID(){
        return recipeID;
    }

    public  String getRecipeName(){
        return recipeName;
    }

    public List<Ingredient> getRecipeIngredients(){
        return recipeIngredients;
    }

    public  List<Step> getRecipeSteps(){
        return recipeSteps;
    }

    public  int getRecipeServings(){
        return recipeServings;
    }

    public String getImageURL(){
        return imageURL;
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
        imageURL = in.readString();
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
        dest.writeString(imageURL);
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