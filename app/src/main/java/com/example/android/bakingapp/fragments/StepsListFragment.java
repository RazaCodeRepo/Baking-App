package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepRecyclerAdapter;
import com.example.android.bakingapp.activities.StepDetailActivity;
import com.example.android.bakingapp.bake.Recipe;
import com.example.android.bakingapp.bake.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/28/2017.
 */

public class StepsListFragment extends Fragment implements StepRecyclerAdapter.StepListItemClickListener {

    public static final String TAG = "StepsListFragment";
    private List<Step> steps;
    @BindView(R.id.rv_steps_list) RecyclerView recyclerView;
    private String recipeName;

    OnStepItemClickListener mCallback;

    public interface OnStepItemClickListener{
        void onStepSelected(int itemIndex, List<Step> stepsList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepItemClickListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null){
            Intent intent = getActivity().getIntent();
            Recipe recipe = intent.getParcelableExtra("RECIPELISTFRAGMENT_RECIPE");
            steps = recipe.getRecipeSteps();
            recipeName = recipe.getRecipeName();
            Log.v(TAG, recipe.getRecipeName());


            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            StepRecyclerAdapter mAdapter = new StepRecyclerAdapter(steps, StepsListFragment.this);
            recyclerView.setAdapter(mAdapter);
        } else{
            steps = savedInstanceState.getParcelableArrayList("SAVEDINSTANCE_STEPS");
        }


        return rootView;
    }

    @Override
    public void onListItemClickListener(int clickedItemIndex) {
        mCallback.onStepSelected(clickedItemIndex, steps);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("SAVEDINSTANCE_STEPS", (ArrayList)steps);
    }
}
