package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.PersistableBundle;
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

public class StepDetailActivity extends AppCompatActivity {

    private static final String TAG = "StepDetailActivity";

    @BindView(R.id.btn_previousStep) Button previousStepButton;
    @BindView(R.id.btn_nextStep) Button nextStepButton;
    private static int newIndex;
    private boolean fromActivity;

    FragmentManager fragmentManager;

    VideoDisplayFragment videoDisplayFragment;
    StepInstructionFragment stepInstructionFragment;

    int itemClicked;
    List<Step> stepsList;
    int stepsSize;

    private static final String INSTRUCTION_FRAGMENT="instruction";
    private static final String VIDEO_FRAGMENT ="video";
    private static final String STEPDETAIL_LIST = "stepslist";
    private static final String ITEM_INDEX = "itemIndex";
    private static final String NEW_INDEX = "newIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
       // fromActivity = true;
        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null){
            videoDisplayFragment = new VideoDisplayFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.videoContainer, videoDisplayFragment)
                    .commit();

            stepInstructionFragment = new StepInstructionFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.instructionContainer, stepInstructionFragment)
                    .commit();



            Intent intent = getIntent();

            itemClicked = intent.getIntExtra("step_index", -1);

            stepsList = intent.getParcelableArrayListExtra("step_list");


            if(itemClicked == -1){
                Toast.makeText(this, "itemClicked is -1", Toast.LENGTH_SHORT).show();
            }

            //newIndex = itemClicked;
        } else {
           videoDisplayFragment = (VideoDisplayFragment)fragmentManager.getFragment(savedInstanceState, VIDEO_FRAGMENT);
           stepInstructionFragment = (StepInstructionFragment)fragmentManager.getFragment(savedInstanceState, INSTRUCTION_FRAGMENT);
           stepsList = savedInstanceState.getParcelableArrayList(STEPDETAIL_LIST);
           itemClicked = savedInstanceState.getInt(ITEM_INDEX);
        }

//
//        videoDisplayFragment = new VideoDisplayFragment();
//
//        fragmentManager.beginTransaction()
//                .add(R.id.videoContainer, videoDisplayFragment)
//                .commit();
//
//        stepInstructionFragment = new StepInstructionFragment();
//
//        fragmentManager.beginTransaction()
//                .add(R.id.instructionContainer, stepInstructionFragment)
//                .commit();
//
//
//
//        Intent intent = getIntent();
//
//        itemClicked = intent.getIntExtra("step_index", -1);
//
//        stepsList = intent.getParcelableArrayListExtra("step_list");
//
//        stepsSize = stepsList.size();
//
//        if(itemClicked == -1){
//            Toast.makeText(this, "itemClicked is -1", Toast.LENGTH_SHORT).show();
//        }
//
        newIndex = itemClicked;

        stepsSize = stepsList.size();


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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManager.putFragment(outState, VIDEO_FRAGMENT, videoDisplayFragment );
        fragmentManager.putFragment(outState, INSTRUCTION_FRAGMENT, stepInstructionFragment);
        outState.putParcelableArrayList(STEPDETAIL_LIST, (ArrayList)stepsList);
        outState.putInt(ITEM_INDEX, itemClicked);
        //outState.putInt(NEW_INDEX, newIndex);

    }
}
