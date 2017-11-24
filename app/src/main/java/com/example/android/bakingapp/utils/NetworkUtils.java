package com.example.android.bakingapp.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.bakingapp.bake.Ingredient;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.bake.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kontrol on 9/20/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getName();
    //any changes

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static List<Recipe> extractRecipes() {

        Log.v(TAG, "Fetching Recipe");

        List<Recipe> recipes = new ArrayList<Recipe>();

        URL url = buildURL(RECIPE_URL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        recipes = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}

        return recipes;
    }

    public static URL buildURL(String requestUrl){

        URL url = null;
        try{
            url = new URL(requestUrl);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        //Log.v(TAG, "Built Uri " + url);

        return url;

    }

    private static String makeHttpRequest(URL url)throws IOException {
        Log.v(TAG, "In makeHttpREquest");
        String jsonResponse = "";

        if(url == null){
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the recipe JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.v(TAG, jsonResponse);


        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.v(TAG, "In readFromStream");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static List<Recipe> extractFeatureFromJson(String recipeJSON) {
        Log.v(TAG, "In extractFeatureFromJSON");
        // If the JSON string is empty or null, then return early.
        ArrayList<Recipe> recipes = new ArrayList<>();
        if (TextUtils.isEmpty(recipeJSON)) {
            return null;
        }

        try {

            JSONArray resultsArray = new JSONArray(recipeJSON);
            if(resultsArray == null || resultsArray.length() == 0){
                Log.i(TAG, "Results Array Empty");
                return null;
            }

            for(int i=0;i<resultsArray.length();i++){

                JSONObject recipeData = resultsArray.getJSONObject(i);
                int id = recipeData.getInt("id");
                String name = recipeData.getString("name");

                JSONArray ingredientsArray = recipeData.getJSONArray("ingredients");
                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                for(int j=0;j<ingredientsArray.length();j++){
                    JSONObject ingredientData = ingredientsArray.getJSONObject(j);
                    int quantity = ingredientData.getInt("quantity");
                    String metric = ingredientData.getString("measure");
                    String ingredientNme = ingredientData.getString("ingredient");

                    ingredients.add(new Ingredient(ingredientNme, metric, quantity));
                }

                JSONArray stepsArray = recipeData.getJSONArray("steps");
                List<Step> steps = new ArrayList<Step>();
                for(int k=0;k<stepsArray.length();k++){
                    JSONObject stepsData = stepsArray.getJSONObject(k);
                    int stepId = stepsData.getInt("id");
                    String short_desc = stepsData.getString("shortDescription");
                    String desc = stepsData.getString("description");
                    String videoURL = stepsData.getString("videoURL");
                    String thumbnail = stepsData.getString("thumbnailURL");

                    steps.add(new Step(stepId, short_desc, desc, videoURL, thumbnail));
                }
                int servings = recipeData.getInt("servings");
                String image = recipeData.getString("image");

                recipes.add(new Recipe(id, name, ingredients, steps, servings, image));
            }
            return recipes;
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the movies JSON results", e);
        }
        return null;
    }
}
