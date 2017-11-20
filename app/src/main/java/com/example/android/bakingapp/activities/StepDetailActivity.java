package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Step;
import com.example.android.bakingapp.fragments.StepInstructionFragment;
import com.example.android.bakingapp.fragments.StepsListFragment;
import com.example.android.bakingapp.fragments.VideoDisplayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements StepsListFragment.OnStepItemClickListener {

    private static final String TAG = "StepDetailActivity";

    @BindView(R.id.btn_previousStep) Button previousStepButton;
    @BindView(R.id.btn_nextStep) Button nextStepButton;
    private static int newIndex;
    private boolean fromActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        fromActivity = true;


        final FragmentManager fragmentManager = getSupportFragmentManager();

        final VideoDisplayFragment videoDisplayFragment = new VideoDisplayFragment();

        fragmentManager.beginTransaction()
                .add(R.id.videoContainer, videoDisplayFragment)
                .commit();

        StepInstructionFragment stepInstructionFragment = new StepInstructionFragment();

        fragmentManager.beginTransaction()
                .add(R.id.instructionContainer, stepInstructionFragment)
                .commit();



        Intent intent = getIntent();

        int itemClicked = intent.getIntExtra("step_index", -1);

        final List<Step>stepsList = intent.getParcelableArrayListExtra("step_list");

        final int stepsSize = stepsList.size();

        if(itemClicked == -1){
            Toast.makeText(this, "itemClicked is -1", Toast.LENGTH_SHORT).show();
        }

        newIndex = itemClicked;

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                newIndex = newIndex + 1;
                if(newIndex < 0){
                    Toast.makeText(StepDetailActivity.this, "Item already at 0", Toast.LENGTH_SHORT).show();
                } else if(newIndex > stepsSize -1){
                    Toast.makeText(StepDetailActivity.this, "No more Steps", Toast.LENGTH_SHORT).show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putInt("STEP_INDEX_ACTIVITY", newIndex);
                    bundle.putParcelableArrayList("STEP_LIST_ACTIVITY", (ArrayList)stepsList);


                    VideoDisplayFragment replaceVideoDisplayFragment = new VideoDisplayFragment();
                    replaceVideoDisplayFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.videoContainer, replaceVideoDisplayFragment)
                            .commit();

                    StepInstructionFragment replaceStepInstructionFragment = new StepInstructionFragment();
                    replaceStepInstructionFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.instructionContainer, replaceStepInstructionFragment)
                            .commit();
                }

            }
        });

        previousStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newIndex =  newIndex - 1;
                if(newIndex < 0){
                    Toast.makeText(StepDetailActivity.this, "Item already at 0", Toast.LENGTH_SHORT).show();
                } else if(newIndex > stepsSize){
                    Toast.makeText(StepDetailActivity.this, "No more Steps", Toast.LENGTH_SHORT).show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putInt("STEP_INDEX_ACTIVITY", newIndex);
                    bundle.putParcelableArrayList("STEP_LIST_ACTIVITY", (ArrayList)stepsList);
                    VideoDisplayFragment replaceVideoDisplayFragment = new VideoDisplayFragment();
                    replaceVideoDisplayFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.videoContainer, replaceVideoDisplayFragment)
                            .commit();

                    StepInstructionFragment replaceStepInstructionFragment = new StepInstructionFragment();
                    replaceStepInstructionFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.instructionContainer, replaceStepInstructionFragment)
                            .commit();
                }
            }
        });
    }



    @Override
    public void onStepSelected(int itemIndex, List<Step> stepsList) {
        if(TextUtils.isEmpty(stepsList.get(itemIndex).getStep_videoURL())){
            FrameLayout videoContainer = (FrameLayout)findViewById(R.id.videoContainer);
            videoContainer.setVisibility(View.INVISIBLE);
        }
    }
}
