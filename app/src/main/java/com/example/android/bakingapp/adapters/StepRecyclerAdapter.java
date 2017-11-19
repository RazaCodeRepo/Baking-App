package com.example.android.bakingapp.adapters;

/**
 * Created by Kontrol on 9/28/2017.
 */

import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.bake.Step;

import android.text.TextUtils;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kontrol on 9/28/2017.
 */

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.StepViewHolder> {

    private List<Step> stepList;

    private final StepListItemClickListener mOnClickListener;

    public interface StepListItemClickListener{
        void onListItemClickListener(int clickedItemIndex);
    }

    public StepRecyclerAdapter(List<Step> steps, StepListItemClickListener itemClick){
       stepList = steps;
        mOnClickListener = itemClick;
    }

    @Override
    public StepRecyclerAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);
        return new StepViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(StepRecyclerAdapter.StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_id) TextView stepID;
        @BindView(R.id.tv_step_desc) TextView stepShortDesc;
        @BindView(R.id.play_button) ImageView stepPlayImage;


        public StepViewHolder(View itemView){
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            stepID.setText(String.valueOf(stepList.get(listIndex).getStepID()));
            stepShortDesc.setText(stepList.get(listIndex).getStep_shortDescription());
            String temp = stepList.get(listIndex).getStep_videoURL();
            if(TextUtils.isEmpty(temp)){
                stepPlayImage.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClickListener(clickedPosition);
        }
    }

}

