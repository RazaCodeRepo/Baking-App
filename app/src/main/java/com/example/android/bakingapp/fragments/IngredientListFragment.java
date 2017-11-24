package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.adapters.IngredientRecyclerAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Ingredient;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.widget.BakingWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/28/2017.
 */

public class IngredientListFragment extends Fragment {

    public static final String TAG = "IngredientListFragment";
    @BindView(R.id.rv_ingredients_detail) RecyclerView recyclerView;
    private Recipe recipe;
    private List<Ingredient> temp;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null){
            Bundle bundle = getArguments();

            if(bundle != null){
                Recipe recipe = bundle.getParcelable("RECIPE_TABLET");
                temp = recipe.getRecipeIngredients();
            } else {
                Intent intent = getActivity().getIntent();
                recipe = intent.getParcelableExtra("RECIPELISTFRAGMENT_RECIPE");
                temp = recipe.getRecipeIngredients();
            }

            Gson gson = new Gson();
            String json = gson.toJson(temp);

            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_INGREDIENT", json).apply();

            BakingWidgetProvider.sendRefreshBroadcast(getContext());

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            IngredientRecyclerAdapter mAdapter = new IngredientRecyclerAdapter(temp);
            recyclerView.setAdapter(mAdapter);

        } else{
            temp = savedInstanceState.getParcelableArrayList("SAVEDINSTANCE_INGREDIENTS");
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            IngredientRecyclerAdapter mAdapter = new IngredientRecyclerAdapter(temp);
            recyclerView.setAdapter(mAdapter);
        }



        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("SAVEDINSTANCE_INGREDIENTS", (ArrayList)temp);

    }
}
