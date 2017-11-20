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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 10/10/2017.
 */

public class StepInstructionFragment extends Fragment {

    private static final String TAG = "StepInstructionFragment";

    private List<Step> steps;

    @BindView(R.id.tv_stepsInstructions) TextView stepsInstructions;

    private static final String INSTRUCTION_FRAGMENT_STEPS_INSTANCE_KEY ="instructionlist";
    private static final String INSTRUCTION_FRAGMENT_INDEX_INSTANCE_KEY ="itemindex";

    private int itemIndex;


    public StepInstructionFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_instruction, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            if(bundle != null){
                itemIndex = bundle.getInt("STEP_INDEX_ACTIVITY");
                steps = bundle.getParcelableArrayList("STEP_LIST_ACTIVITY");
                Step temp = steps.get(itemIndex);
                stepsInstructions.setText(temp.getStep_desc());

            } else {
                Intent intent = getActivity().getIntent();
                itemIndex = intent.getIntExtra("step_index", 0);
                steps = intent.getParcelableArrayListExtra("step_list");
                stepsInstructions.setText(steps.get(itemIndex).getStep_desc());

            }
        } else{
            steps = savedInstanceState.getParcelableArrayList(INSTRUCTION_FRAGMENT_STEPS_INSTANCE_KEY);
            itemIndex = savedInstanceState.getInt(INSTRUCTION_FRAGMENT_INDEX_INSTANCE_KEY);
            Step temp = steps.get(itemIndex);
            stepsInstructions.setText(temp.getStep_desc());
        }


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INSTRUCTION_FRAGMENT_STEPS_INSTANCE_KEY, (ArrayList)steps);
        outState.putInt(INSTRUCTION_FRAGMENT_INDEX_INSTANCE_KEY, itemIndex );
    }
}
