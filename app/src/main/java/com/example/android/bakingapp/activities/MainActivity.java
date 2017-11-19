package com.example.android.bakingapp.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.fragments.IngredientListFragment;
import com.example.android.bakingapp.fragments.RecipeListFragment;
import com.example.android.bakingapp.fragments.StepInstructionFragment;
import com.example.android.bakingapp.fragments.StepsListFragment;
import com.example.android.bakingapp.fragments.VideoDisplayFragment;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeItemClickListener {

    public static final String TAG = "MainActivity";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.recipe_tag);


        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.main_baking_app_linear_layout) != null){
            mTwoPane = true;

        } else{
            mTwoPane = false;
        }

        RecipeListFragment recipeListFragment = new RecipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_tablet", mTwoPane);
        recipeListFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeListFragment)
                .commit();


    }

    @Override
    public void onRecipeSelected(int itemClicked, Recipe recipe) {

        Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        intent.putExtra("RECIPELISTFRAGMENT_RECIPE", recipe);
        intent.putExtra("IS_TABLET", mTwoPane);
        startActivity(intent);
    }
}
