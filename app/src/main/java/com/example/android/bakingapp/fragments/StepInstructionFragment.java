package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 10/10/2017.
 */

public class StepInstructionFragment extends Fragment {

    private static final String TAG = "StepInstructionFragment";

    private static List<Step> steps;

    @BindView(R.id.tv_stepsInstructions) TextView stepsInstructions;

    public StepInstructionFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_instruction, container, false);
        ButterKnife.bind(this, rootView);



        Bundle bundle = getArguments();
        if(bundle != null){
            int itemIndex = bundle.getInt("STEP_INDEX_ACTIVITY");
            List<Step> steps = bundle.getParcelableArrayList("STEP_LIST_ACTIVITY");
            Step temp = steps.get(itemIndex);
            stepsInstructions.setText(temp.getStep_desc());

        } else {
            Intent intent = getActivity().getIntent();
            int itemClicked = intent.getIntExtra("step_index", 0);
            List<Step> steps = intent.getParcelableArrayListExtra("step_list");
            stepsInstructions.setText(steps.get(itemClicked).getStep_desc());

        }




        return rootView;
    }
}
