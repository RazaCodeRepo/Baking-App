package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeDetailActivity;
import com.example.android.bakingapp.adapters.RecipeRecyclerAdapter;
import com.example.android.bakingapp.bake.Ingredient;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.widget.BakingWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/26/2017.
 */

public class RecipeListFragment extends Fragment implements RecipeRecyclerAdapter.ListItemClickListener {

    public static final String TAG = "RecipeListFragment";

   @BindView(R.id.rv_recipe_name) RecyclerView mRecyclerView;

   @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    private RecipeRecyclerAdapter mAdapter;

    public static final int NETWORK_LOADER = 111;

    private List<Recipe> recipeList;

    OnRecipeItemClickListener mCallback;

    public interface OnRecipeItemClickListener{
        void onRecipeSelected(int itemClicked, Recipe recipe);
    }

    public RecipeListFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnRecipeItemClickListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeItemClickListener");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NETWORK_LOADER, null, networkLoaderCallbacks).forceLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);


        recipeList = new ArrayList<Recipe>();



        Bundle  bundle = getArguments();
        boolean isTablet = bundle.getBoolean("is_tablet");
        if(!isTablet){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(layoutManager);
        }



        return rootView;

    }

    private LoaderManager.LoaderCallbacks<List<Recipe>> networkLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Recipe>>() {
        @Override
        public Loader<List<Recipe>> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<List<Recipe>>(getActivity()) {
                
                @Override
                public List<Recipe> loadInBackground() {
                    List<Recipe> recipesList = NetworkUtils.extractRecipes();
                    return recipesList;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
            if(data != null){
                progressBar.setVisibility(View.GONE);
                recipeList = data;
                List<Ingredient> tempIng = recipeList.get(0).getRecipeIngredients();
                Gson gson = new Gson();
                String json = gson.toJson(tempIng);
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_INGREDIENT", json).apply();
                BakingWidgetProvider.sendRefreshBroadcast(getContext());
                mAdapter = new RecipeRecyclerAdapter(data, RecipeListFragment.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<Recipe>> loader) {

        }
    };

    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        Recipe mRecipe = recipeList.get(clickedItemIndex);
        mCallback.onRecipeSelected(clickedItemIndex, mRecipe);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("SAVEDINSTANCE_RECIPELIST", (ArrayList)recipeList);
    }
}
