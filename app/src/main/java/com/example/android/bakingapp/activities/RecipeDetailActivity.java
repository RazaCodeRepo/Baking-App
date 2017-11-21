package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Ingredient;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.bake.Step;
import com.example.android.bakingapp.fragments.IngredientListFragment;
import com.example.android.bakingapp.fragments.StepInstructionFragment;
import com.example.android.bakingapp.fragments.StepsListFragment;
import com.example.android.bakingapp.fragments.VideoDisplayFragment;
import com.example.android.bakingapp.widget.BakingWidgetProvider;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements StepsListFragment.OnStepItemClickListener {

    public static final String TAG = "RecipeDetailActivity";


    FragmentManager fragmentManager;
    boolean isTwoPane;
    private Recipe recipe;
    private List<Step> steps;

    IngredientListFragment ingredientListFragment;
    StepsListFragment stepsListFragment;
    VideoDisplayFragment videoDisplayFragment;
    StepInstructionFragment stepInstructionFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
//            Intent intent = getIntent();
//            recipe = intent.getParcelableExtra("RECIPELISTFRAGMENT_RECIPE");
//            steps = recipe.getRecipeSteps();
            getSupportActionBar().setTitle(recipe.getRecipeName());

            if(findViewById(R.id.detail_linear_layout) != null){
                isTwoPane = true;


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("STEP_LIST_ACTIVITY", (ArrayList)steps);
                bundle.putInt("STEP_INDEX_ACTIVITY", 0);

                ingredientListFragment = new IngredientListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_steps_container, ingredientListFragment)
                        .commit();

                stepsListFragment = new StepsListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_container, stepsListFragment)
                        .commit();

                videoDisplayFragment = new VideoDisplayFragment();
                videoDisplayFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .add(R.id.videoContainer, videoDisplayFragment)
                        .commit();

                stepInstructionFragment = new StepInstructionFragment();
                stepInstructionFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .add(R.id.instructionContainer, stepInstructionFragment)
                        .commit();


            } else{
                isTwoPane = false;

                ingredientListFragment = new IngredientListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_steps_container, ingredientListFragment)
                        .commit();

                stepsListFragment = new StepsListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_container, stepsListFragment)
                        .commit();
            }

        } else{
            recipe = savedInstanceState.getParcelable("SAVEDINSTANCE_RECIPE");
            steps = recipe.getRecipeSteps();
            getSupportActionBar().setTitle(recipe.getRecipeName());
        }

//        if(findViewById(R.id.detail_linear_layout) != null){
//            isTwoPane = true;
//
//
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("STEP_LIST_ACTIVITY", (ArrayList)steps);
//            bundle.putInt("STEP_INDEX_ACTIVITY", 0);
//
//            ingredientListFragment = new IngredientListFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.ingredient_steps_container, ingredientListFragment)
//                    .commit();
//
//            stepsListFragment = new StepsListFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.steps_container, stepsListFragment)
//                    .commit();
//
//            videoDisplayFragment = new VideoDisplayFragment();
//            videoDisplayFragment.setArguments(bundle);
//            fragmentManager.beginTransaction()
//                    .add(R.id.videoContainer, videoDisplayFragment)
//                    .commit();
//
//            stepInstructionFragment = new StepInstructionFragment();
//            stepInstructionFragment.setArguments(bundle);
//            fragmentManager.beginTransaction()
//                    .add(R.id.instructionContainer, stepInstructionFragment)
//                    .commit();
//
//
//        } else{
//            isTwoPane = false;
//
//            ingredientListFragment = new IngredientListFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.ingredient_steps_container, ingredientListFragment)
//                    .commit();
//
//            stepsListFragment = new StepsListFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.steps_container, stepsListFragment)
//                    .commit();
//        }

    }

    @Override
    public void onStepSelected(int itemIndex, List<Step> stepsList) {

        if(isTwoPane){

            Bundle bundle = new Bundle();
            bundle.putInt("STEP_INDEX_ACTIVITY", itemIndex);
            bundle.putBoolean("TABLETMODE",isTwoPane );
            bundle.putParcelableArrayList("STEP_LIST_ACTIVITY", (ArrayList)stepsList);

            videoDisplayFragment = new VideoDisplayFragment();
            videoDisplayFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.videoContainer, videoDisplayFragment)
                    .commit();

            stepInstructionFragment = new StepInstructionFragment();
            stepInstructionFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.instructionContainer, stepInstructionFragment)
                    .commit();

        } else{

            Intent intent = new Intent(RecipeDetailActivity.this, StepDetailActivity.class);
            intent.putExtra("step_index", itemIndex);
            intent.putParcelableArrayListExtra("step_list", (ArrayList)stepsList);
            startActivity(intent);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("SAVEDINSTANCE_RECIPE", recipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = savedInstanceState.getParcelable("SAVEDINSTANCE_RECIPE");
    }


}
